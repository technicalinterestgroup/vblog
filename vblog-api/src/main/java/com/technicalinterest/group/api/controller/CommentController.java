package com.technicalinterest.group.api.controller;

import com.github.blackshadowwalker.spring.distributelock.annotation.DistributeLock;
import com.technicalinterest.group.api.param.NewCommentParam;
import com.technicalinterest.group.api.vo.ApiResult;
import com.technicalinterest.group.service.CommentService;
import com.technicalinterest.group.service.annotation.BlogOperation;
import com.technicalinterest.group.service.dto.EditCommentDTO;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.util.IpAdrressUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @package: com.technicalinterest.group.api.controller
 * @className: CommentController
 * @description: 评论控制层
 * @author: Shuyu.Wang
 * @date: 2019-08-18 21:09
 * @since: 0.1
 **/
@Api(tags = "博客评论模块")
@RestController
@RequestMapping("comment")
public class CommentController {
	@Autowired
	private CommentService commentService;

	@ApiOperation(value = "发表评论", notes = "发表评论")
	@PostMapping(value = "/new")
	@BlogOperation(value = "发表评论")
	@DistributeLock( key = "#newCommentParam.articleId", timeout = 2, expire = 1, errMsg = "00000")
	public ApiResult<String> saveArticle(@Valid @RequestBody NewCommentParam newCommentParam) {
		ApiResult apiResult = new ApiResult();
		EditCommentDTO editCommentDTO = new EditCommentDTO();
		BeanUtils.copyProperties(newCommentParam, editCommentDTO);
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();
		editCommentDTO.setIpAddress(IpAdrressUtil.getIpAdrress(request));
		ReturnClass returnClass = commentService.insert(editCommentDTO);
		if (returnClass.isSuccess()) {
			apiResult.success(returnClass.getMsg(), null);
		} else {
			apiResult.fail(returnClass.getMsg());
		}
		return apiResult;
	}
}
