package com.technicalinterest.group.service.impl;

import com.technicalinterest.group.dao.User;
import com.technicalinterest.group.mapper.UserMapper;
import com.technicalinterest.group.service.MailService;
import com.technicalinterest.group.service.constant.UserConstant;
import com.technicalinterest.group.service.context.RequestHeaderContext;
import com.technicalinterest.group.service.dto.EditUserDTO;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.dto.UserDTO;
import com.technicalinterest.group.service.UserService;
import com.technicalinterest.group.service.exception.VLogException;
import com.technicalinterest.group.service.util.RedisUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
		if (user.getState()==0){
			return ReturnClass.fail(UserConstant.NO_ACTIVATION);
		}
		//生成token
		UserDTO userVO = new UserDTO();
		userVO.setUserToken(setToken(userDTO.getUserName()));
		userVO.setUserName(userDTO.getUserName());
		return ReturnClass.success(userVO);
	}

	private String setToken(String userName) {
		String token = UUID.randomUUID().toString().replaceAll("-", "");
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
//		user.setState((short) 1);
		int i = userMapper.insertSelective(user);
		if (i != 1) {
			return ReturnClass.fail(UserConstant.ADD_USER_ERROR);
		} else {
			String key = newUserDTO.getUserName() + "_" + UUID.randomUUID().toString();
			redisUtil.set(key, String.valueOf(user.getId()), activation_time);
			//发送邮件
			//点击验证邮箱：<a href=\""+domain+"\">"+domain+"</a>"
			mailService.sendHtmlMail(newUserDTO.getEmail(),UserConstant.MAIL_TITLE,"<a href=\""+UserConstant.ACTIVATION_URL+key+"\">"+UserConstant.ACTIVATION_URL+key+"</a>");
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
	public ReturnClass updateUser(EditUserDTO editUserDTO) {
		User user = new User();
		ReturnClass userByToken = getUserByToken();
		if (!userByToken.isSuccess()) {
			throw new VLogException(UserConstant.FAILD_GET_USER_INFO);
		}
		UserDTO userDTO = (UserDTO) userByToken.getData();
		user.setId(userDTO.getId());
		BeanUtils.copyProperties(editUserDTO, user);
		int update = userMapper.update(user);
		if (update != 1) {
			return ReturnClass.fail(UserConstant.EDIT_USER_ERROR);
		} else {
			return ReturnClass.success();
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
	 * @Description: 根据toke获取用户信息
	 * @author: shuyu.wang
	 * @date: 2019-07-28 19:43
	 * @return null
	 */
	@Override
	public ReturnClass getUserByToken() {
		String accessToken = RequestHeaderContext.getInstance().getAccessToken();
		String userName = (String) redisUtil.get(accessToken);
		if(Objects.isNull(userName)){
			throw new VLogException(UserConstant.LOG_OUT_INFO);
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
	 * @Description: 账号激活
	 * @author: shuyu.wang
	 * @date: 2019/8/5 21:24
	 * @param key
	 * @return null
	 */
	@Override
	public ReturnClass activationUser(String key) {
		String id=(String)redisUtil.get(key);
		if (!StringUtils.isEmpty(id)){
			User user =new User();
			user.setId(Long.parseLong(id));
			user.setState((short)1);
			int update = userMapper.update(user);
			if (update<1){
                  throw new VLogException(UserConstant.FAILD_GET_USER_INFO);
			}
			return ReturnClass.success(UserConstant.ACTIVATION_SUC);
		}else {
			return  ReturnClass.fail(UserConstant.MAIL_OUTTIME);
		}
	}
}
