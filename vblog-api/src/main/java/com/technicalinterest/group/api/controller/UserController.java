package com.technicalinterest.group.api.controller;

import com.technicalinterest.group.api.constant.ResultMessage;
import com.technicalinterest.group.api.vo.ApiResult;
import com.technicalinterest.group.service.dto.EditUserDTO;
import com.technicalinterest.group.service.dto.NewUserDTO;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.dto.UserDTO;
import com.technicalinterest.group.service.user.UserService;
import com.technicalinterest.group.service.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @package: com.shuyu.blog.controller
 * @className: UserController
 * @description: 用户登录
 * @author: Shuyu.Wang
 * @date: 2019-07-14 15:30
 * @since: 0.1
 **/
@Api(tags = "用户管理")
@RestController
@RequestMapping("admin")
public class UserController {
	@Autowired
	private UserService userService;

	/**
	 * 登录接口
	 * @return null
	 * @author: shuyu.wang
	 * @date: 2019-07-14 19:24
	 */
	@ApiOperation(value = "登录", notes = "用户登录")
	@PostMapping(value = "/user/login")
	public ApiResult<UserVO> login(UserDTO userDTO) {
		ApiResult apiResult = new ApiResult();
		if (Objects.isNull(userDTO.getUserName()) || Objects.isNull(userDTO.getPassWord())) {
			apiResult.fail(ResultMessage.PARAM_ERROR);
			return apiResult;
		}
		ReturnClass<UserVO> login = userService.login(userDTO);
		if (login.isSuccess()) {
			apiResult.success(login.getData());
		} else {
			apiResult.setMsg(login.getMsg());
		}
		return apiResult;
	}

	/**
	 * 注册新用户
	 * @author: shuyu.wang
	 * @date: 2019-07-21 21:50
	 * @param userDTO
	 * @return com.technicalinterest.group.api.vo.ApiResult<com.technicalinterest.group.service.vo.UserVO>
	 */
	@ApiOperation(value = "注册", notes = "新用户注册")
	@PostMapping(value = "/user/new")
	public ApiResult<String> saveUser(NewUserDTO userDTO) {
		ApiResult apiResult = new ApiResult();
		if (Objects.isNull(userDTO.getUserName()) || Objects.isNull(userDTO.getPassWord()) || Objects.isNull(userDTO.getEmail())) {
			apiResult.fail(ResultMessage.PARAM_ERROR);
			return apiResult;
		}
		ReturnClass<String> addUser = userService.addUser(userDTO);
		if (addUser.isSuccess()) {
			apiResult.success(addUser.getData());
		} else {
			apiResult.setMsg(addUser.getMsg());
		}
		return apiResult;
	}

	//修改信息设置
	/**
	 * 修改信息设置
	 * @author: shuyu.wang
	 * @date: 2019-07-21 21:50
	 * @param userDTO
	 * @return com.technicalinterest.group.api.vo.ApiResult<com.technicalinterest.group.service.vo.UserVO>
	 */
	@ApiOperation(value = "注册", notes = "新用户注册")
	@PostMapping(value = "/user/edit")
	public ApiResult<String> editUser(EditUserDTO userDTO) {
		ApiResult apiResult = new ApiResult();
		ReturnClass<String> addUser = userService.updateUser(userDTO);
		if (addUser.isSuccess()) {
			apiResult.success(addUser.getData());
		} else {
			apiResult.setMsg(addUser.getMsg());
		}
		return apiResult;
	}

	//退出
	
	/**
	 * 注销登录
	 * @author: shuyu.wang
	 * @date: 2019-07-21 22:27
	 * @param null
	 * @return null
	*/
	@ApiOperation(value = "注销登录", notes = "退出")
	@GetMapping(value = "/user/logout")
	public ApiResult<String> logout() {
		ApiResult apiResult = new ApiResult();
		ReturnClass<Boolean> addUser = userService.logout("");
		if (addUser.isSuccess()) {
			apiResult.success(addUser.getData());
		} else {
			apiResult.setMsg(addUser.getMsg());
		}
		return apiResult;
	}

}
