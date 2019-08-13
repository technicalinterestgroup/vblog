package com.technicalinterest.group.api.controller;

import com.technicalinterest.group.api.param.EditUserParam;
import com.technicalinterest.group.api.param.NewUserParam;
import com.technicalinterest.group.api.param.UserParam;
import com.technicalinterest.group.api.vo.ApiResult;
import com.technicalinterest.group.api.vo.UserVO;
import com.technicalinterest.group.service.constant.ResultEnum;
import com.technicalinterest.group.service.dto.EditUserDTO;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.UserService;
import com.technicalinterest.group.service.exception.VLogException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("user")
public class UserController {
	@Autowired
	private UserService userService;

	private static final Boolean authCheck=true;


	/**
	 * 修改信息设置
	 * @author: shuyu.wang
	 * @date: 2019-07-21 21:50
	 * @param editUserParam
	 * @return com.technicalinterest.group.api.vo.ApiResult<com.technicalinterest.group.service.vo.UserVO>
	 */
	@ApiOperation(value = "修改信息", notes = "用户模块")
	@PostMapping(value = "/edit")
	public ApiResult<String> editUser(@Valid @RequestBody EditUserParam editUserParam) {
		ApiResult apiResult = new ApiResult();
		EditUserDTO editUserDTO = new EditUserDTO();
		BeanUtils.copyProperties(editUserParam, editUserDTO);
		ReturnClass addUser = userService.updateUser(authCheck,editUserDTO);
		if (addUser.isSuccess()) {
			apiResult.success(addUser.getData());
		} else {
			apiResult.fail(addUser.getMsg());
		}
		return apiResult;
	}

	/**
	 * 用户信息接口
	 * @return null
	 * @author: shuyu.wang
	 * @date: 2019-07-14 19:24
	 */
	@ApiOperation(value = "用户信息", notes = "用户信息")
	@GetMapping(value = "/detail/{userName}")
	public ApiResult<UserVO> detail(@PathVariable("userName") String userName) {
		ApiResult apiResult = new ApiResult();
		ReturnClass getUserByuserName = userService.getUserByuserName(authCheck,userName);
		if (getUserByuserName.isSuccess()) {
			UserVO userVO = new UserVO();
			BeanUtils.copyProperties(getUserByuserName.getData(), userVO);
			apiResult.success(userVO);

		} else {
			throw new VLogException(ResultEnum.NO_URL);
		}
		return apiResult;
	}

	/**
	 * 注销登录
	 * @author: shuyu.wang
	 * @date: 2019-07-21 22:27
	 * @return null
	 */
	@ApiOperation(value = "注销登录", notes = "退出")
	@GetMapping(value = "/logout")
	public ApiResult<String> logout() {
		ApiResult apiResult = new ApiResult();
		ReturnClass addUser = userService.logout("");
		if (addUser.isSuccess()) {
			apiResult.success(addUser.getData());
		} else {
			apiResult.fail(addUser.getMsg());
		}
		return apiResult;
	}


}
