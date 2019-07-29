package com.technicalinterest.group.api.controller;

import com.technicalinterest.group.api.param.EditUserParam;
import com.technicalinterest.group.api.param.NewUserParam;
import com.technicalinterest.group.api.param.UserParam;
import com.technicalinterest.group.api.vo.ApiResult;
import com.technicalinterest.group.api.vo.UserVO;
import com.technicalinterest.group.service.context.RequestHeaderContext;
import com.technicalinterest.group.service.dto.EditUserDTO;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.dto.UserDTO;
import com.technicalinterest.group.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
	@GetMapping(value = "/user/login")
	public ApiResult<UserVO> login(@Valid UserParam userParam, BindingResult results) {
		ApiResult apiResult = new ApiResult();
		if (results.hasErrors()){
			apiResult.fail(results.getFieldError().getDefaultMessage());
			return apiResult;
		}
		EditUserDTO userDTO=new EditUserDTO();
		BeanUtils.copyProperties(userParam,userDTO);
		ReturnClass<UserDTO> login=userService.login(userDTO);
		if (login.isSuccess()) {
			UserVO userVO=new UserVO();
			BeanUtils.copyProperties(login.getData(),userVO);
			apiResult.success(userVO);
		} else {
			apiResult.setMsg(login.getMsg());
		}
		return apiResult;
	}

	/**
	 * 注册新用户
	 * @author: shuyu.wang
	 * @date: 2019-07-21 21:50
	 * @param newUserParam
	 * @return com.technicalinterest.group.api.vo.ApiResult<com.technicalinterest.group.service.vo.UserVO>
	 */
	@ApiOperation(value = "注册", notes = "新用户注册")
	@PostMapping(value = "/user/new")
	public ApiResult<String> saveUser(@Valid NewUserParam newUserParam, BindingResult results) {
		ApiResult apiResult = new ApiResult();
		if (results.hasErrors()){
			apiResult.fail(results.getFieldError().getDefaultMessage());
			return apiResult;
		}
		EditUserDTO newUserDTO=new EditUserDTO();
		BeanUtils.copyProperties(newUserParam,newUserDTO);
		ReturnClass<String> addUser = userService.addUser(newUserDTO);
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
	 * @param editUserParam
	 * @return com.technicalinterest.group.api.vo.ApiResult<com.technicalinterest.group.service.vo.UserVO>
	 */
	@ApiOperation(value = "修改信息", notes = "用户模块")
	@PostMapping(value = "/user/edit")
	public ApiResult<String> editUser(@Valid EditUserParam editUserParam, BindingResult results) {
		ApiResult apiResult = new ApiResult();
		if (results.hasErrors()){
			apiResult.fail(results.getFieldError().getDefaultMessage());
			return apiResult;
		}
		EditUserDTO editUserDTO=new EditUserDTO();
		BeanUtils.copyProperties(editUserParam,editUserDTO);
		ReturnClass<String> addUser = userService.updateUser(editUserDTO);
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
