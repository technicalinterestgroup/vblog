package com.technicalinterest.group.service.impl;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.technicalinterest.group.dao.User;
import com.technicalinterest.group.dao.UserRole;
import com.technicalinterest.group.dto.BlogUserDTO;
import com.technicalinterest.group.dto.RoleAuthDTO;
import com.technicalinterest.group.dto.UserRoleDTO;
import com.technicalinterest.group.mapper.RoleAuthMapper;
import com.technicalinterest.group.mapper.UserMapper;
import com.technicalinterest.group.mapper.UserRoleMapper;
import com.technicalinterest.group.service.MailService;
import com.technicalinterest.group.service.VSystemService;
import com.technicalinterest.group.service.constant.ResultEnum;
import com.technicalinterest.group.service.constant.UserConstant;
import com.technicalinterest.group.service.context.RequestHeaderContext;
import com.technicalinterest.group.service.dto.*;
import com.technicalinterest.group.service.UserService;
import com.technicalinterest.group.service.exception.VLogException;
import com.technicalinterest.group.service.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @package: com.shuyu.blog.service.impl
 * @className: UserServiceImpl
 * @description:
 * @author: Shuyu.Wang
 * @date: 2019-07-14 18:37
 * @since: 0.1
 **/
@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleAuthMapper roleAuthMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private MailService mailService;
	@Autowired
	private VSystemService systemService;
	@Autowired
	private DefaultKaptcha producer;

	@Value("${server}")
	private String SERVER;

	@Value("${activation_web_url}")
	private String ACTIVATION_WEB_URL;

	@Value("${reset_web_url}")
	private String REST_WEB_URL;

	private static final long activation_time = 60 * 60 * 24;

	private static final long login_time = 60 * 30;
	//验证码过期时间
	private static final long img_time = 60 * 5;

	/**
	 * 登录
	 * @param userDTO
	 * @return ReturnClass<UserDTO>
	 * @author: shuyu.wang
	 * @date: 2019-07-14 18:48
	 */
	@Override
	public ReturnClass login(EditUserDTO userDTO) {
		//验证码校验
		ReturnClass returnClass = validImg(userDTO.getToken(), userDTO.getImg());
		if (!returnClass.isSuccess()){
			return returnClass;
		}
		User user = new User();
		user.setUserName(userDTO.getUserName());
		//用户名判断
		UserRoleDTO userRoleDTO1 = userMapper.queryUserRoleDTO(user);
		if (Objects.isNull(userRoleDTO1)) {
			return ReturnClass.fail(UserConstant.NO_USER);
		}
		if (userRoleDTO1.getState() == 0) {
			return ReturnClass.fail(UserConstant.NO_ACTIVATION);
		}
		if (userRoleDTO1.getIsDel() == 1) {
			return ReturnClass.fail(UserConstant.USER_DISABLE);
		}
		//密码判断
		user.setPassWord(user.getPassWord());
		UserRoleDTO userRoleDTO = userMapper.queryUserRoleDTO(user);
		if (Objects.isNull(userRoleDTO)) {
			return ReturnClass.fail(UserConstant.PASSWORD_ERROR);
		}
		//获取权限列表
		List<RoleAuthDTO> roleAuthDTOS = roleAuthMapper.queryAuthByRole(userRoleDTO.getRoleId(), (short) 1);
		if (userRoleDTO.getRoleType() == 1) {
			if (!redisUtil.hasKey(UserConstant.ADMIN_AUTH_URL)) {
				List<RoleAuthDTO> roleAuth = roleAuthMapper.queryAuthByRole(userRoleDTO.getRoleId(), (short) 2);
				StringBuffer stringBuffer = new StringBuffer();
				for (RoleAuthDTO entity : roleAuth) {
					stringBuffer.append(entity.getUrl());
					stringBuffer.append(",");
				}
				redisUtil.set(UserConstant.ADMIN_AUTH_URL, stringBuffer.toString());
			}
		}
		//生成token
		UserDTO userVO = new UserDTO();
		userVO.setUserToken(setToken(userRoleDTO.getUserName()));
		userVO.setUserName(userRoleDTO.getUserName());
		userVO.setNickName(userRoleDTO.getNickName());
		userVO.setRoleType(userRoleDTO.getRoleType());
		userVO.setAuthList(roleAuthDTOS);
		return ReturnClass.success(UserConstant.LOGIN_SUCCESS, userVO);
	}

	private String setToken(String userName) {
		String token = UUID.randomUUID().toString().replaceAll("-", "");
		if (redisUtil.hasKey(userName)) {
			String o = (String) redisUtil.get(userName);
			redisUtil.del(o);
		}
		redisUtil.set(userName, token, login_time);
		redisUtil.set(token, userName, login_time);
		return token;
	}

	/**
	 * 注册新用户
	 * @author: shuyu.wang
	 * @date: 2019-07-21 21:32
	 * @param newUserDTO
	 * @return null
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ReturnClass addUser(EditUserDTO newUserDTO) {
		//验证码校验
		ReturnClass returnClass = validImg(newUserDTO.getToken(), newUserDTO.getImg());
		if (!returnClass.isSuccess()){
			return returnClass;
		}
		User user = User.builder().userName(newUserDTO.getUserName()).build();
		User userByUser = userMapper.getUserByUser(user);
		if (!Objects.isNull(userByUser)) {
			return ReturnClass.fail(UserConstant.DUPLICATE_USER_NAME);
		}
		User email = User.builder().email(newUserDTO.getEmail()).build();
		User emailResult = userMapper.getUserByUser(email);
		if (!Objects.isNull(emailResult)) {
			return ReturnClass.fail(UserConstant.DUPLICATE_USER_EMAIL);
		}
		BeanUtils.copyProperties(newUserDTO, user);
		user.setNickName("小小程序员");
		int i = userMapper.insertSelective(user);
		if (i != 1) {
			return ReturnClass.fail(UserConstant.ADD_USER_ERROR);
		} else {
			UserRole userRole = new UserRole();
			userRole.setUserId(user.getId());
			userRole.setRoleId((long) 2);
			int i1 = userRoleMapper.insertSelective(userRole);
			if (i1 < 1) {
				throw new VLogException(UserConstant.ADD_USER_ERROR);
			}
			VSystemDTO vSystemDTO = VSystemDTO.builder().userName(newUserDTO.getUserName()).vStart(new Date()).build();
			systemService.insertSelective(vSystemDTO);
			String key = newUserDTO.getUserName() + "_" + UUID.randomUUID().toString();
			redisUtil.set(key, String.valueOf(user.getId()), activation_time);
			//发送邮件
			//点击验证邮箱：<a href=\""+domain+"\">"+domain+"</a>"
			mailService.sendHtmlMail(newUserDTO.getEmail(), UserConstant.MAIL_TITLE,
					"<p>欢迎!&nbsp" + newUserDTO.getUserName() + "<br>" + "感谢您在技术兴趣博客网站的注册，请点击这里激活您的账号:<br></p>" + "<a href=\"" + ACTIVATION_WEB_URL + UserConstant.ACTIVATION_URL
							+ key + "\">" + SERVER + UserConstant.ACTIVATION_URL + key + "</a><br>" + "<p>祝您使用愉快，使用过程中您有任何问题请及时联系我们。</p>");

			return ReturnClass.success(UserConstant.ADD_EMAIL_SEND);
		}
	}

	/**
	 * 更新用户信息
	 * @author: shuyu.wang
	 * @date: 2019-07-21 22:11
	 * @param editUserDTO
	 * @return null
	 */
	@Override
	public ReturnClass updateUser(Boolean authCheck, EditUserDTO editUserDTO) {
		User user = new User();
		ReturnClass userByToken = getUserByToken();
		if (!userByToken.isSuccess()) {
			throw new VLogException(ResultEnum.USERINFO_ERROR);
		}
		UserDTO userDTO = (UserDTO) userByToken.getData();
		BeanUtils.copyProperties(editUserDTO, user);
		user.setId(userDTO.getId());
		int update = userMapper.update(user);
		if (update != 1) {
			return ReturnClass.fail(UserConstant.EDIT_USER_ERROR);
		} else {
			return ReturnClass.success(UserConstant.EDIT_USER_SUS);
		}
	}

	/**
	 * @Description: 退出登录
	 * @author: shuyu.wang
	 * @date: 2019-07-21 22:22
	 * @param token
	 * @return null
	 */
	@Override
	public ReturnClass logout(String token) {
		Object o = redisUtil.get(token);
		if (!Objects.isNull(o)) {
			redisUtil.del(token, o.toString());
		}
		return ReturnClass.success();
	}

	/**
	 * @Description: 账号激活
	 * @author: shuyu.wang
	 * @date: 2019/8/5 21:24
	 * @param key
	 * @return null
	 */
	@Override
	public ReturnClass activationUser(String key) {
		String id = (String) redisUtil.get(key);
		if (!StringUtils.isEmpty(id)) {
			User user = new User();
			user.setId(Long.parseLong(id));
			user.setState((short) 1);
			int update = userMapper.update(user);
			if (update < 1) {
				throw new VLogException(UserConstant.ACTIVATION_FAIL);
			}
			redisUtil.del(key);
			return ReturnClass.success(UserConstant.ACTIVATION_SUC);
		} else {
			return ReturnClass.fail(UserConstant.MAIL_OUTTIME);
		}
	}

	/**
	 * @Description: 根据toke获取用户信息
	 * @author: shuyu.wang
	 * @date: 2019-07-28 19:43
	 * @return null
	 */
	@Override
	public ReturnClass getUserByToken() {
		String accessToken = RequestHeaderContext.getInstance().getAccessToken();
		if (!redisUtil.hasKey(accessToken)) {
			throw new VLogException(ResultEnum.TIME_OUT);
		}
		String userName = (String) redisUtil.get(accessToken);
		User user = User.builder().userName(userName).build();
		User userByUser = userMapper.getUserByUser(user);
		if (Objects.nonNull(userByUser)) {
			UserDTO userDTO = new UserDTO();
			BeanUtils.copyProperties(userByUser, userDTO);
			return ReturnClass.success(userDTO);
		}
		return ReturnClass.fail();
	}

	/**
	 * @Description:根据用户名查询用户信息
	 * @author: shuyu.wang
	 * @date: 2019-08-08 13:08
	 * @param userName
	 * @return null
	 */
	@Override
	public ReturnClass getUserByuserName(Boolean authCheck, String userName) {
		//获取数据是否是当前用户校验
		if (authCheck) {
			ReturnClass returnClass = userNameIsLoginUser(userName);
			if (!returnClass.isSuccess()) {
				throw new VLogException(ResultEnum.NO_AUTH);
			}
		}
		User user = User.builder().userName(userName).build();
		UserRoleDTO userRoleDTO = userMapper.queryUserRoleDTO(user);
		if (Objects.nonNull(userRoleDTO)) {
			UserDTO userDTO = new UserDTO();
			BeanUtils.copyProperties(userRoleDTO, userDTO);
			return ReturnClass.success(userDTO);
		}
		return ReturnClass.fail();
	}

	/**
	 * @Description: 判断用户名是否是当前操作登录的用户
	 * @author: shuyu.wang
	 * @date: 2019-08-08 13:12
	 * @param userName
	 * @return null
	 */
	@Override
	public ReturnClass userNameIsLoginUser(String userName) {
		String accessToken = RequestHeaderContext.getInstance().getAccessToken();
		if (!redisUtil.hasKey(accessToken)) {
			throw new VLogException(ResultEnum.TIME_OUT);
		}
		String userNameLogin = (String) redisUtil.get(accessToken);
		if (StringUtils.equals(userName, userNameLogin)) {
			return ReturnClass.success();
		}
		return ReturnClass.fail();
	}

	/**
	 * @Description:获取博客用户信息
	 * @author: shuyu.wang
	 * @date: 2019-08-19 12:56
	 * @param userName
	 * @return null
	 */
	@Override
	public ReturnClass getBlogUserInfo(String userName) {
		List<BlogUserDTO> blogUserDTOS = userMapper.queryUserBlog(userName);
		if (blogUserDTOS.isEmpty()) {
			return ReturnClass.fail(UserConstant.NO_USER_INFO);
		}
		return ReturnClass.success(blogUserDTOS);
	}

	/**
	 * @Description: 发送忘记密码邮件
	 * @author: shuyu.wang
	 * @date: 2019-10-08 17:04
	 * @param userName
	 * @return null
	 */
	@Override
	public ReturnClass sendForgetPassMail(String userName,String email,String img,String token) {
		//验证码校验
		ReturnClass returnClass = validImg(token, img);
		if (!returnClass.isSuccess()){
			return returnClass;
		}
		User user = new User();
		user.setUserName(userName);
		//用户名判断
		User userByUser = userMapper.getUserByUser(user);
		if (Objects.isNull(userByUser)) {
			return ReturnClass.fail(UserConstant.NO_USER);
		}
		if (userByUser.getState() == 0) {
			return ReturnClass.fail(UserConstant.NO_ACTIVATION);
		}
		if (!StringUtils.equals(email,userByUser.getEmail())){
			return ReturnClass.fail(UserConstant.MAIL_FAIL);
		}
		String key = userName + "_" + UUID.randomUUID().toString();
		redisUtil.set(key, String.valueOf(userByUser.getId()), activation_time);
		//发送邮件
		//点击验证邮箱：<a href=\""+domain+"\">"+domain+"</a>"
		mailService.sendHtmlMail(userByUser.getEmail(), UserConstant.FORGET_PASS_MAIL_TITLE,
				"<p>您好!&nbsp" + userName + "<br>" + "如果您确认忘记登录密码，请点击这里重置您的登录密码:<br></p>" + "<a href=\"" + REST_WEB_URL + UserConstant.FORGET_PASS_URL + key + "\">" + SERVER
						+ UserConstant.FORGET_PASS_URL + key + "</a><br>" + "<p>如果操作过程中遇到其他问题请及时联系我们。</p>");

		return ReturnClass.success(UserConstant.FORGET_PASS_MAIL_SEND);
	}

	/**
	 * @Description: 修改密码
	 * @author: shuyu.wang
	 * @date: 2019-10-08 17:23
	 * @param editUserDTO
	 * @return null
	 */
	@Override
	public ReturnClass resetPassWord(String key, EditUserDTO editUserDTO) {

		String id = (String) redisUtil.get(key);
		if (!StringUtils.isEmpty(id)) {
			User user = new User();
			user.setUserName(editUserDTO.getUserName());
			//用户名判断
			User userByUser = userMapper.getUserByUser(user);
			if (Objects.isNull(userByUser)) {
				return ReturnClass.fail(UserConstant.NO_USER);
			}
			if (Long.parseLong(id) != userByUser.getId()) {
				return ReturnClass.fail(UserConstant.KEY_ERROR);
			}
			user.setId(Long.parseLong(id));
			user.setPassWord(editUserDTO.getPassWord());
			int update = userMapper.update(user);
			if (update < 1) {
				throw new VLogException(UserConstant.CHANGE_PASS_FAIL);
			}
			redisUtil.del(key);
			return ReturnClass.success(UserConstant.CHANGE_PASS_SUC);
		} else {
			return ReturnClass.fail(UserConstant.MAIL_OUTTIME);
		}
	}

	@Override
	public ReturnClass createImage() {
		/**
		 * 前后端分离 登录验证码 方案
		 * 后端生成图片 验证码字符串 uuid
		 * uuid为key  验证码字符串为value
		 * 传递bs64图片 和uuid给前端
		 * 前端在登录的时候 传递 账号 密码 验证码 uuid
		 * 通过uuid获取 验证码 验证
		 */
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		//获取验证码
		String text = producer.createText();
		try {
			BufferedImage image = producer.createImage(text);
			ImageIO.write(image, "png", out);
			String base64bytes = Base64.encode(out.toByteArray());
			//该字符串传输至前端放入src即可显示图片，安卓可以去掉data:image/png;base64,
			String src = "data:image/png;base64," + base64bytes;
			String token = UUID.randomUUID().toString();
			redisUtil.set(token, text, img_time);
			ImagVerifi imagVerifi = ImagVerifi.builder().img(src.replaceAll("[\\s*\t\n\r]", "")).token(token).build();
			return ReturnClass.success(imagVerifi);
		} catch (IOException e) {
			throw new VLogException(UserConstant.CREAT_IMG_FAIL);
		}
	}


	/**
	 * @Description: 校验验证码
	 * @author: shuyu.wang
	 * @date: 2019-10-10 17:07
	 * @param token
	 * @return null
	*/
	private ReturnClass validImg(String token, String img) {
		if (redisUtil.hasKey(token)) {
			String result = (String) redisUtil.get(token);
			if (StringUtils.equals(result, img)) {
				redisUtil.del(token);
				return ReturnClass.success(UserConstant.IMG_SUC);
			}
			return ReturnClass.fail(UserConstant.IMG_FAIL);
		}
		return ReturnClass.fail(UserConstant.IMG_TIME_OUT);
	}

}
