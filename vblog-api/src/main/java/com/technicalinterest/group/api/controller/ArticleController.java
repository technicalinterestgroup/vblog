package com.technicalinterest.group.api.controller;

import com.technicalinterest.group.api.param.ArticleContentParam;
import com.technicalinterest.group.api.param.EditArticleContentParam;
import com.technicalinterest.group.api.param.NewUserParam;
import com.technicalinterest.group.api.vo.ApiResult;
import com.technicalinterest.group.service.ArticleService;
import com.technicalinterest.group.service.dto.ArticleContentDTO;
import com.technicalinterest.group.service.dto.EditUserDTO;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.exception.VLogException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @package: com.technicalinterest.group.api.controller
 * @className: ArticleController
 * @description: 文章管理
 * @author: Shuyu.Wang
 * @date: 2019-08-04 15:27
 * @since: 0.1
 **/
@Api(tags = "文章管理")
@RestController
@RequestMapping("article")
public class ArticleController {
	@Autowired
	private ArticleService articleService;

	@ApiOperation(value = "文章发布", notes = "文章发布")
	@PostMapping(value = "/new")
	public ApiResult<String> saveArticle(@Valid @RequestBody ArticleContentParam articleContentParam) {
		ApiResult apiResult = new ApiResult();
		ArticleContentDTO articleContentDTO = new ArticleContentDTO();
		BeanUtils.copyProperties(articleContentParam, articleContentDTO);
		ReturnClass addUser = articleService.saveArticle(articleContentDTO);
		if (addUser.isSuccess()) {
			apiResult.success(addUser.getMsg());
		} else {
			apiResult.setMsg(addUser.getMsg());
		}
		return apiResult;
	}

	@ApiOperation(value = "文章编辑", notes = "文章编辑")
	@PostMapping(value = "/edit")
	public ApiResult<String> editArticle(@Valid @RequestBody EditArticleContentParam editArticleContentParam) {
		ApiResult apiResult = new ApiResult();
		ArticleContentDTO articleContentDTO = new ArticleContentDTO();
		BeanUtils.copyProperties(editArticleContentParam, articleContentDTO);
		ReturnClass addUser = articleService.saveArticle(articleContentDTO);
		if (addUser.isSuccess()) {
			apiResult.success(addUser.getMsg());
		} else {
			apiResult.setMsg(addUser.getMsg());
		}
		return apiResult;
	}
}
