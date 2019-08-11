package com.technicalinterest.group.api.controller;

import com.technicalinterest.group.api.param.ArticleContentParam;
import com.technicalinterest.group.api.param.EditArticleContentParam;
import com.technicalinterest.group.api.param.QueryArticleParam;
import com.technicalinterest.group.api.vo.ApiResult;
import com.technicalinterest.group.api.vo.ArticleContentVO;
import com.technicalinterest.group.api.vo.ArticlesVO;
import com.technicalinterest.group.dao.QueryArticleDTO;
import com.technicalinterest.group.service.ArticleService;
import com.technicalinterest.group.service.UserService;
import com.technicalinterest.group.service.constant.ResultEnum;
import com.technicalinterest.group.service.dto.ArticleContentDTO;
import com.technicalinterest.group.service.dto.PageBean;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.exception.VLogException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
	@Autowired
	private UserService userService;

	@ApiOperation(value = "文章发布", notes = "文章发布")
	@PostMapping(value = "/new")
	public ApiResult<String> saveArticle(@Valid @RequestBody ArticleContentParam articleContentParam) {
		ApiResult apiResult = new ApiResult();
		ArticleContentDTO articleContentDTO = new ArticleContentDTO();
		BeanUtils.copyProperties(articleContentParam, articleContentDTO);
		ReturnClass saveArticle = articleService.saveArticle(articleContentDTO);
		if (saveArticle.isSuccess()) {
			apiResult.success(null, saveArticle.getMsg());
		} else {
			apiResult.fail(saveArticle.getMsg());
		}
		return apiResult;
	}

	@ApiOperation(value = "文章编辑", notes = "文章编辑")
	@PostMapping(value = "/edit")
	public ApiResult<String> editArticle(@Valid @RequestBody EditArticleContentParam editArticleContentParam) {
		ApiResult apiResult = new ApiResult();
		ArticleContentDTO articleContentDTO = new ArticleContentDTO();
		BeanUtils.copyProperties(editArticleContentParam, articleContentDTO);
		ReturnClass editArticle = articleService.editArticle(articleContentDTO);
		if (editArticle.isSuccess()) {
			apiResult.success(null, editArticle.getMsg());
		} else {
			apiResult.fail(editArticle.getMsg());
		}
		return apiResult;
	}

	@ApiOperation(value = "文章列表", notes = "文章列表")
	@GetMapping(value = "/list/{userName}")
	public ApiResult<PageBean<ArticlesVO>> listArticle(@PathVariable("userName") String userName, @Valid QueryArticleParam queryArticleParam) {
		ApiResult apiResult = new ApiResult();
		ReturnClass returnClass = userService.getUserByuserName(userName);
		if (!returnClass.isSuccess()) {
			throw new VLogException(ResultEnum.NO_URL);
		}
		QueryArticleDTO queryArticleDTO = new QueryArticleDTO();
		BeanUtils.copyProperties(queryArticleParam, queryArticleDTO);
		queryArticleDTO.setUserName(userName);
		ReturnClass listArticle = articleService.listArticle(userName, queryArticleDTO);
		if (listArticle.isSuccess()) {
			PageBean<ArticlesVO> pageInfo = new PageBean<ArticlesVO>();
			BeanUtils.copyProperties(listArticle.getData(), pageInfo);
			apiResult.success(pageInfo);

		} else {
			apiResult.setMsg(listArticle.getMsg());
		}
		return apiResult;
	}

	@ApiOperation(value = "文章详情", notes = "文章详情")
	@GetMapping(value = "/detail/{id}")
	public ApiResult<ArticleContentVO> articleDetail(@PathVariable("id") Long id) {
		ApiResult apiResult = new ApiResult();
		ReturnClass articleDetail = articleService.articleDetail(id);

		ArticleContentVO articleContentVO = new ArticleContentVO();
		if (articleDetail.isSuccess()) {
			PageBean<ArticlesVO> pageInfo = new PageBean<ArticlesVO>();
			BeanUtils.copyProperties(articleDetail.getData(), articleContentVO);
			apiResult.success(articleContentVO);

		} else {
			apiResult.setMsg(articleDetail.getMsg());
		}
		return apiResult;
	}

}
