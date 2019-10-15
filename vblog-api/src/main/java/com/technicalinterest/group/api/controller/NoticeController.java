package com.technicalinterest.group.api.controller;

import com.github.blackshadowwalker.spring.distributelock.annotation.DistributeLock;
import com.technicalinterest.group.api.param.PageBaseParam;
import com.technicalinterest.group.api.param.QueryArticleParam;
import com.technicalinterest.group.api.vo.ApiResult;
import com.technicalinterest.group.api.vo.ArticlesVO;
import com.technicalinterest.group.api.vo.CommentNoticeVO;
import com.technicalinterest.group.api.vo.LikeNoticeVO;
import com.technicalinterest.group.dao.PageBase;
import com.technicalinterest.group.dto.ArticlesDTO;
import com.technicalinterest.group.dto.CommentNoticeDTO;
import com.technicalinterest.group.dto.LikeNoticeDTO;
import com.technicalinterest.group.dto.QueryArticleDTO;
import com.technicalinterest.group.service.NoticeService;
import com.technicalinterest.group.service.annotation.BlogOperation;
import com.technicalinterest.group.service.dto.PageBean;
import com.technicalinterest.group.service.dto.ReturnClass;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @package: com.technicalinterest.group.api.controller
 * @className: NoticeController
 * @description: 消息通知控制层
 * @author: Shuyu.Wang
 * @date: 2019-08-20 12:36
 * @since: 0.1
 **/
@Api(tags = "消息通知模块")
@RestController
@RequestMapping("notice")
public class NoticeController {
	@Autowired
	private NoticeService noticeService;

	@ApiOperation(value = "评论通知列表", notes = "通知列表")
	@GetMapping(value = "/comment/list/{userName}")
	@BlogOperation(value = "评论通知列表")
	public ApiResult<PageBean<CommentNoticeVO>> listCommentNotic(@PathVariable("userName") String userName, @Valid PageBaseParam pageBaseParam) {
		ApiResult apiResult = new ApiResult();

		PageBase pageBase = new PageBase();
		BeanUtils.copyProperties(pageBaseParam, pageBase);
		ReturnClass listArticle = noticeService.queryCommentNotice(userName, pageBase);
		if (listArticle.isSuccess()) {
			PageBean<CommentNoticeDTO> pageBean = (PageBean<CommentNoticeDTO>) listArticle.getData();
			List<CommentNoticeVO> list = new ArrayList<>();
			for (CommentNoticeDTO entity : pageBean.getPageData()) {
				CommentNoticeVO commentNoticeVO = new CommentNoticeVO();
				BeanUtils.copyProperties(entity, commentNoticeVO);
				list.add(commentNoticeVO);
			}
			PageBean<CommentNoticeVO> pageInfo = new PageBean<CommentNoticeVO>();
			BeanUtils.copyProperties(listArticle.getData(), pageInfo);
			pageInfo.setPageData(list);
			apiResult.success(pageInfo);
		} else {
			apiResult.setMsg(listArticle.getMsg());
		}
		return apiResult;
	}

	@ApiOperation(value = "查看评论通知", notes = "查看评论通知")
	@GetMapping(value = "/comment/view/{id}")
	@BlogOperation(value = "查看评论通知")
	public ApiResult<PageBean<CommentNoticeVO>> viewCommentNotic(@PathVariable("id") Long id) {
		ApiResult apiResult = new ApiResult();
		ReturnClass returnClass = noticeService.viewComment(id);
		if (returnClass.isSuccess()) {
			apiResult.success(returnClass.getMsg(),null);
		} else {
			apiResult.setMsg(returnClass.getMsg());
		}
		return apiResult;
	}

	@ApiOperation(value = "点赞通知列表", notes = "通知列表")
	@GetMapping(value = "/like/list/{userName}")
	@BlogOperation(value = "点赞通知列表")
	public ApiResult<PageBean<LikeNoticeVO>> listLikeNotic(@PathVariable("userName") String userName, @Valid PageBaseParam pageBaseParam) {
		ApiResult apiResult = new ApiResult();

		PageBase pageBase = new PageBase();
		BeanUtils.copyProperties(pageBaseParam, pageBase);
		ReturnClass listArticle = noticeService.queryLikeNotice(userName, pageBase);
		if (listArticle.isSuccess()) {
			PageBean<LikeNoticeDTO> pageBean = (PageBean<LikeNoticeDTO>) listArticle.getData();
			List<LikeNoticeVO> list = new ArrayList<>();
			for (LikeNoticeDTO entity : pageBean.getPageData()) {
				LikeNoticeVO likeNoticeVO = new LikeNoticeVO();
				BeanUtils.copyProperties(entity, likeNoticeVO);
				list.add(likeNoticeVO);
			}
			PageBean<LikeNoticeVO> pageInfo = new PageBean<LikeNoticeVO>();
			BeanUtils.copyProperties(listArticle.getData(), pageInfo);
			pageInfo.setPageData(list);
			apiResult.success(pageInfo);
		} else {
			apiResult.setMsg(listArticle.getMsg());
		}
		return apiResult;
	}

	@ApiOperation(value = "查看评论通知", notes = "查看评论通知")
	@GetMapping(value = "/like/view/{id}")
	@BlogOperation(value = "查看评论通知")
	@DistributeLock( key = "#id", timeout = 1, expire = 1, errMsg = "00000")
	public ApiResult<PageBean<CommentNoticeVO>> viewLike(@PathVariable("id") Long id) {
		ApiResult apiResult = new ApiResult();
		ReturnClass returnClass = noticeService.viewLike(id);
		if (returnClass.isSuccess()) {
			apiResult.success();
		} else {
			apiResult.setMsg(returnClass.getMsg());
		}
		return apiResult;
	}

}