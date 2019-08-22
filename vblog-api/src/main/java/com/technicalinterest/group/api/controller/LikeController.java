package com.technicalinterest.group.api.controller;

import com.technicalinterest.group.api.vo.ApiResult;
import com.technicalinterest.group.service.LikeService;
import com.technicalinterest.group.service.annotation.BlogOperation;
import com.technicalinterest.group.service.dto.LikeDTO;
import com.technicalinterest.group.service.dto.ReturnClass;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @package: com.technicalinterest.group.api.controller
 * @className: LikeController
 * @description: 点赞控制层
 * @author: Shuyu.Wang
 * @date: 2019-08-22 23:04
 * @since: 0.1
 **/
@Api(tags = "点赞模块")
@RestController
@RequestMapping("like")
public class LikeController {
	@Autowired
	private LikeService likeService;

	/**
	 * @Description: 添加收藏
	 * @author: shuyu.wang
	 * @date: 2019-08-15 17:39
	 * @param articleId
	 * @return null
	 */
	@ApiOperation(value = "博客点赞", notes = "博客点赞")
	@GetMapping(value = "/new/{articleId}")
	@BlogOperation(value = "博客点赞")
	public ApiResult<String> addCollection(@PathVariable("articleId") Long articleId) {
		ApiResult apiResult = new ApiResult();
		LikeDTO pojo= LikeDTO.builder().sourceId(articleId).type((short)1).build();
		ReturnClass insert = likeService.insert(pojo);
		if (insert.isSuccess()) {
			apiResult.success(insert.getMsg(), null);
		} else {
			apiResult.setMsg(insert.getMsg());
		}
		return apiResult;
	}

	/**
	 * @Description: 取消收藏
	 * @author: shuyu.wang
	 * @date: 2019-08-15 17:39
	 * @param articleId
	 * @return null
	 */
	@ApiOperation(value = "博客取消收藏", notes = "取消收藏")
	@GetMapping(value = "/del/{articleId}")
	@BlogOperation(value = "取消收藏")
	public ApiResult<String> delCollection(@PathVariable("articleId") Long articleId) {
		ApiResult apiResult = new ApiResult();
		LikeDTO pojo= LikeDTO.builder().sourceId(articleId).type((short)1).build();
		ReturnClass insert = likeService.del(pojo);
		if (insert.isSuccess()) {
			apiResult.success(insert.getMsg(), null);
		} else {
			apiResult.setMsg(insert.getMsg());
		}
		return apiResult;
	}
}
