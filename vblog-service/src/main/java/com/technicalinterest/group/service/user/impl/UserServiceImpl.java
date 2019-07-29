package com.technicalinterest.group.service.user.impl;

import com.technicalinterest.group.dao.User;
import com.technicalinterest.group.mapper.UserMapper;
import com.technicalinterest.group.service.constant.UserConstant;
import com.technicalinterest.group.service.context.RequestHeaderContext;
import com.technicalinterest.group.service.dto.EditUserDTO;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.dto.UserDTO;
import com.technicalinterest.group.service.user.UserService;
import com.technicalinterest.group.service.util.RedisUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

	private static final long activation_time = 60 * 60 * 24;

	/**
	 * 登录
	 * @param userDTO
	 * @return ReturnClass<UserDTO>
	 * @author: shuyu.wang
	 * @date: 2019-07-14 18:48
	 */
	@Override
	public ReturnClass<UserDTO> login(EditUserDTO userDTO) {
		ReturnClass<UserDTO> returnClass = new ReturnClass<>();
		User user = new User();
		user.setUserName(userDTO.getUserName());
		//用户名判断
		User user1 = userMapper.getUserByUser(user);
		if (Objects.isNull(user1)) {
			returnClass.setMsg(UserConstant.NO_USER);
			return returnClass;
		}
		//密码判断
		user.setPassWord(user.getPassWord());
		user1 = userMapper.getUserByUser(user);
		if (Objects.isNull(user1)) {
			returnClass.setMsg(UserConstant.PASSWORD_ERROR);
			return returnClass;
		}
		//生成token
		UserDTO userVO = new UserDTO();
		userVO.setUserToken(setToken(userDTO.getUserName()));
		userVO.setUserName(userDTO.getUserName());
		returnClass.success(userVO);
		return returnClass;
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
	public ReturnClass<String> addUser(EditUserDTO newUserDTO) {
		ReturnClass<String> returnClass = new ReturnClass<String>();
		User user = new User();
		user.setUserName(newUserDTO.getUserName());
		User userByUser = userMapper.getUserByUser(user);
		if (!Objects.isNull(userByUser)) {
			returnClass.setMsg(UserConstant.DUPLICATE_USER_NAME);
			return returnClass;
		}
		User user2 = new User();
		user2.setUserName(newUserDTO.getUserName());
		User userByUser2 = userMapper.getUserByUser(user2);
		if (!Objects.isNull(userByUser2)) {
			returnClass.setMsg(UserConstant.DUPLICATE_USER_EMAIL);
			return returnClass;
		}
		BeanUtils.copyProperties(newUserDTO, user);
		user.setState((short) 1);
		user.setCreateTime(new Date());
		int i = userMapper.insertSelective(user);
		if (i != 1) {
			returnClass.setMsg(UserConstant.ADD_USER_ERROR);
		} else {
			String key = newUserDTO.getUserName() + "_" + UUID.randomUUID().toString();
			redisUtil.set(key, user.getId(), activation_time);
			//发送邮件
			returnClass.setMsg(UserConstant.ADD_EMAIL_SEND);
			returnClass.success();
		}
		return returnClass;
	}

	/**
	 * 更新用户信息
	 * @author: shuyu.wang
	 * @date: 2019-07-21 22:11
	 * @param editUserDTO
	 * @return null
	 */
	@Override
	public ReturnClass<String> updateUser(EditUserDTO editUserDTO) {
		ReturnClass<String> returnClass = new ReturnClass<String>();
		User user = new User();

		ReturnClass<UserDTO> userByToken = getUserByToken();
		if (!userByToken.isSuccess()){
			returnClass.setMsg(UserConstant.FAILD_GET_USER_INFO);
			return returnClass;
		}
		user.setId(userByToken.getData().getId());
		BeanUtils.copyProperties(editUserDTO, user);
		int update = userMapper.update(user);
		if (update != 1) {
			returnClass.setMsg(UserConstant.EDIT_USER_ERROR);
		} else {
			returnClass.success();
		}
		return returnClass;
	}

	/**
	 * @Description: 退出登录
	 * @author: shuyu.wang
	 * @date: 2019-07-21 22:22
	 * @param token
	 * @return null
	 */
	@Override
	public ReturnClass<Boolean> logout(String token) {
		ReturnClass<Boolean> returnClass = new ReturnClass<Boolean>();
		Object o = redisUtil.get(token);
		if (!Objects.isNull(o)) {
			redisUtil.del(token, o.toString());
		}
		returnClass.success();
		return returnClass;
	}

	/**
	 * @Description: 根据toke获取用户信息
	 * @author: shuyu.wang
	 * @date: 2019-07-28 19:43
	 * @return null
	 */
	@Override
	public ReturnClass<UserDTO> getUserByToken() {
		ReturnClass<UserDTO> returnClass=new ReturnClass<>();
		String accessToken = RequestHeaderContext.getInstance().getAccessToken();
		String userName=(String)redisUtil.get(accessToken);
		User user=new User();
		user.setUserName(userName);
		User userByUser = userMapper.getUserByUser(user);
		if (!Objects.isNull(userByUser)){
			UserDTO userDTO=new UserDTO();
			BeanUtils.copyProperties(userByUser, userDTO);
			returnClass.success(userDTO);
		}
		return returnClass;
	}
}
