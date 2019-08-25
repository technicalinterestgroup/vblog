package com.technicalinterest.group.service.impl;

import com.technicalinterest.group.dao.User;
import com.technicalinterest.group.dto.BlogUserDTO;
import com.technicalinterest.group.mapper.UserMapper;
import com.technicalinterest.group.service.MailService;
import com.technicalinterest.group.service.VSystemService;
import com.technicalinterest.group.service.constant.ResultEnum;
import com.technicalinterest.group.service.constant.UserConstant;
import com.technicalinterest.group.service.context.RequestHeaderContext;
import com.technicalinterest.group.service.dto.EditUserDTO;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.dto.UserDTO;
import com.technicalinterest.group.service.UserService;
import com.technicalinterest.group.service.dto.VSystemDTO;
import com.technicalinterest.group.service.exception.VLogException;
import com.technicalinterest.group.service.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private MailService mailService;
	@Autowired
	private VSystemService systemService;

	private static final long activation_time = 60 * 60 * 24;

	/**
	 * 登录
	 * @param userDTO
	 * @return ReturnClass<UserDTO>
	 * @author: shuyu.wang
	 * @date: 2019-07-14 18:48
	 */
	@Override
	public ReturnClass login(EditUserDTO userDTO) {
		User user = new User();
		user.setUserName(userDTO.getUserName());
		//用户名判断
		User user1 = userMapper.getUserByUser(user);
		if (Objects.isNull(user1)) {
			return ReturnClass.fail(UserConstant.NO_USER);
		}
		//密码判断
		user.setPassWord(user.getPassWord());
		user1 = userMapper.getUserByUser(user);
		if (Objects.isNull(user1)) {
			return ReturnClass.fail(UserConstant.PASSWORD_ERROR);
		}
		if (user1.getState() == 0) {
			return ReturnClass.fail(UserConstant.NO_ACTIVATION);
		}
		//生成token
		UserDTO userVO = new UserDTO();
		userVO.setUserToken(setToken(user1.getUserName()));
		userVO.setUserName(user1.getUserName());
		userVO.setNickName(user1.getNickName());
		return ReturnClass.success(userVO);
	}

	private String setToken(String userName) {
		String token = UUID.randomUUID().toString().replaceAll("-", "");
		if (redisUtil.hasKey(userName)) {
			String o = (String) redisUtil.get(userName);
			redisUtil.del(o);
		}
		redisUtil.set(userName, token, 1800);
		redisUtil.set(token, userName, 1800);
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
	public ReturnClass addUser(EditUserDTO newUserDTO) {
		User user = User.builder().userName(newUserDTO.getUserName()).build();
		User userByUser = userMapper.getUserByUser(user);
		if (!Objects.isNull(userByUser)) {
			return ReturnClass.fail(UserConstant.DUPLICATE_USER_NAME);
		}
		User user2 = User.builder().userName(newUserDTO.getUserName()).build();
		User userByUser2 = userMapper.getUserByUser(user2);
		if (!Objects.isNull(userByUser2)) {
			return ReturnClass.fail(UserConstant.DUPLICATE_USER_EMAIL);
		}
		BeanUtils.copyProperties(newUserDTO, user);
		int i = userMapper.insertSelective(user);
		if (i != 1) {
			return ReturnClass.fail(UserConstant.ADD_USER_ERROR);
		} else {
			VSystemDTO vSystemDTO = VSystemDTO.builder().userName(newUserDTO.getUserName()).vStart(new Date()).build();
			systemService.insertSelective(vSystemDTO);
			String key = newUserDTO.getUserName() + "_" + UUID.randomUUID().toString();
			redisUtil.set(key, String.valueOf(user.getId()), activation_time);
			//发送邮件
			//点击验证邮箱：<a href=\""+domain+"\">"+domain+"</a>"
			mailService.sendHtmlMail(newUserDTO.getEmail(), UserConstant.MAIL_TITLE,
					"<a href=\"" + UserConstant.ACTIVATION_URL + key + "\">" + UserConstant.ACTIVATION_URL + key + "</a>");

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
				throw new VLogException(ResultEnum.USERINFO_ERROR);
			}
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
		if (!redisUtil.hasKey(accessToken)){
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
		User userByUser = userMapper.getUserByUser(user);
		if (Objects.nonNull(userByUser)) {
			UserDTO userDTO = new UserDTO();
			BeanUtils.copyProperties(userByUser, userDTO);
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
		if (!redisUtil.hasKey(accessToken)){
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
}
