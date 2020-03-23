package com.technicalinterest.group.api.controller;

import com.technicalinterest.group.api.param.QueryArticleAdminParam;
import com.technicalinterest.group.api.param.QueryFileParam;
import com.technicalinterest.group.api.param.QueryLogParam;
import com.technicalinterest.group.api.vo.*;
import com.technicalinterest.group.dao.Log;
import com.technicalinterest.group.dto.*;
import com.technicalinterest.group.service.AdminService;
import com.technicalinterest.group.service.dto.PageBean;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.util.ListBeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	//TODO 登录

	@ApiOperation(value = "用户列表", notes = "用户列表")
	@GetMapping(value = "/list/user")
	public ApiResult<List<UserInfoVO>> listUser() {
		ApiResult apiResult = new ApiResult();
		ReturnClass listUser = adminService.userAll();
		if (listUser.isSuccess()) {
			List list = ListBeanUtils.copyProperties(listUser.getData(), UserInfoVO.class);
			apiResult.success(list);

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