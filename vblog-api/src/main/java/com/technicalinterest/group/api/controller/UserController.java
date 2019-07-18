package com.technicalinterest.group.api.controller;

import com.technicalinterest.group.api.vo.ApiResult;
import com.technicalinterest.group.api.vo.UserVO;
import com.technicalinterest.group.service.dto.UserDTO;
import com.technicalinterest.group.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	 * 登录页面
	 *
	 * @return null
	 * @author: shuyu.wang
	 * @date: 2019-07-14 19:24
	 */
	@ApiOperation(value = "登录接口")
	@PostMapping(value = "/user/login")
	public UserDTO login(UserVO userVO) {
		//        UserDTO login = userService.login(userVO);
		return new UserDTO();
	}

	@ApiOperation(value = "测试接口")
	@PostMapping(value = "/test")
	public ApiResult test(UserVO userVO) {
		int a = 1 / 0;
		return new ApiResult();
	}

}
