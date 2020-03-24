package com.technicalinterest.group.api.controller;

import com.technicalinterest.group.api.param.*;
import com.technicalinterest.group.api.vo.*;
import com.technicalinterest.group.dao.Log;
import com.technicalinterest.group.dto.*;
import com.technicalinterest.group.service.AdminService;
import com.technicalinterest.group.service.UserService;
import com.technicalinterest.group.service.annotation.BlogOperation;
import com.technicalinterest.group.service.dto.EditUserDTO;
import com.technicalinterest.group.service.dto.PageBean;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.dto.UserJWTDTO;
import com.technicalinterest.group.service.util.ListBeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @package: com.technicalinterest.group.api.controller
 * @className: AdminController
 * @description: 管理员控制层
 * @author: Shuyu.Wang
 * @date: 2019-09-01 15:11
 * @since: 0.1
 **/
@Api(tags = "管理员模块")
@RestController
@RequestMapping("admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private UserService userService;


	/**
	 * 登录接口
	 * @return null
	 * @author: shuyu.wang
	 * @date: 2019-07-14 19:24
	 */
	@ApiOperation(value = "登录", notes = "用户登录")
	@GetMapping(value = "/login")
	@BlogOperation(value = "登录")
	public ApiResult<UserVO> login(@Valid UserParam userParam) {
		ApiResult apiResult = new ApiResult();
		EditUserDTO userDTO = new EditUserDTO();
		BeanUtils.copyProperties(userParam, userDTO);
		ReturnClass login = userService.login(userDTO,(short)1);
		if (login.isSuccess()) {
			UserVO userVO = new UserVO();
			UserJWTDTO resultUser = (UserJWTDTO) login.getData();
			BeanUtils.copyProperties(resultUser, userVO);
			List list = ListBeanUtils.copyProperties(resultUser.getAuthList(), RoleAuthVO.class);
			userVO.setAuthList(list);
			apiResult.success(userVO);
			apiResult.setMsg(login.getMsg());
		} else {
			apiResult.fail(login.getMsg());
		}
		return apiResult;
	}

	@ApiOperation(value = "用户列表", notes = "用户列表")
	@GetMapping(value = "/list/user")
	public ApiResult<PageBean<UserInfoVO>> listUser(QueryUserRoleParam queryUserRoleParam) {
		ApiResult apiResult = new ApiResult();
		UserRoleDTO user=new UserRoleDTO();
		BeanUtils.copyProperties(queryUserRoleParam,user);
		ReturnClass listUser = adminService.userAll(user);
		if (listUser.isSuccess()) {
			PageBean<UserRoleDTO> pageBean = (PageBean<UserRoleDTO>) listUser.getData();
			List<UserInfoVO> list = new ArrayList<>();
			for (UserRoleDTO entity : pageBean.getPageData()) {
				UserInfoVO userInfoVO = new UserInfoVO();
				BeanUtils.copyProperties(entity, userInfoVO);
				list.add(userInfoVO);
			}
			PageBean<UserInfoVO> pageInfo = new PageBean<UserInfoVO>();
			BeanUtils.copyProperties(listUser.getData(), pageInfo);
			pageInfo.setPageData(list);
			apiResult.success(pageInfo);

		} else {
			apiResult.setMsg(listUser.getMsg());
		}
		return apiResult;
	}
	//用户禁用
	//角色管理
	//菜单管理

	@ApiOperation(value = "博客列表", notes = "博客列表")
	@GetMapping(value = "/list/blog")
	public ApiResult<PageBean<ArticlesAdminVO>> listBlog(QueryArticleAdminParam queryArticleAdminParam) {
		ApiResult apiResult = new ApiResult();
		QueryArticleDTO queryArticleDTO = new QueryArticleDTO();
		BeanUtils.copyProperties(queryArticleAdminParam, queryArticleDTO);
		ReturnClass listUser = adminService.articleAll(queryArticleDTO);
		if (listUser.isSuccess()) {
			PageBean<ArticlesAdminVO> result = new PageBean<ArticlesAdminVO>();
			PageBean<ArticlesDTO> pageBean = (PageBean<ArticlesDTO>) listUser.getData();
			List list = ListBeanUtils.copyProperties(pageBean.getPageData(), ArticlesAdminVO.class);
			BeanUtils.copyProperties(pageBean, result);
			result.setPageData(list);
			apiResult.success(result);
		} else {
			apiResult.setMsg(listUser.getMsg());
		}
		return apiResult;

	}

	@ApiOperation(value = "文件列表", notes = "文件列表")
	@GetMapping(value = "/list/file")
	public ApiResult<PageBean<FileInfoVO>> listFile(QueryFileParam queryFileParam) {
		ApiResult apiResult = new ApiResult();
		QueryFileDTO queryFileDTO = new QueryFileDTO();
		BeanUtils.copyProperties(queryFileParam, queryFileDTO);
		ReturnClass listArticle = adminService.fileAll(queryFileDTO);
		if (listArticle.isSuccess()) {
			PageBean<FileDTO> pageBean = (PageBean<FileDTO>) listArticle.getData();
			List<FileInfoVO> list = new ArrayList<>();
			for (FileDTO entity : pageBean.getPageData()) {
				FileInfoVO fileVO = new FileInfoVO();
				BeanUtils.copyProperties(entity, fileVO);
				list.add(fileVO);
			}
			PageBean<FileInfoVO> pageInfo = new PageBean<FileInfoVO>();
			BeanUtils.copyProperties(listArticle.getData(), pageInfo);
			pageInfo.setPageData(list);
			apiResult.success(pageInfo);
		} else {
			apiResult.setMsg(listArticle.getMsg());
		}
		return apiResult;
	}

	@ApiOperation(value = "日志列表", notes = "日志列表")
	@GetMapping(value = "/list/log")
	public ApiResult<PageBean<LogVO>> listLog(QueryLogParam queryLogParam) {
		ApiResult apiResult = new ApiResult();
		QueryLogDTO queryLogDTO = new QueryLogDTO();
		BeanUtils.copyProperties(queryLogParam, queryLogDTO);
		ReturnClass listArticle = adminService.logAll(queryLogDTO);
		if (listArticle.isSuccess()) {
			PageBean<Log> pageBean = (PageBean<Log>) listArticle.getData();
			List list = ListBeanUtils.copyProperties(pageBean.getPageData(), LogVO.class);
			PageBean<LogVO> pageInfo = new PageBean<LogVO>();
			BeanUtils.copyProperties(listArticle.getData(), pageInfo);
			pageInfo.setPageData(list);
			apiResult.success(pageInfo);
		} else {
			apiResult.setMsg(listArticle.getMsg());
		}
		return apiResult;
	}


}