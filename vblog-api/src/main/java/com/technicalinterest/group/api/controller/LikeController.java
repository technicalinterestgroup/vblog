package com.technicalinterest.group.api.controller;

import com.github.blackshadowwalker.spring.distributelock.annotation.DistributeLock;
import com.technicalinterest.group.api.vo.ApiResult;
import com.technicalinterest.group.service.LikeService;
import com.technicalinterest.group.service.annotation.BlogOperation;
import com.technicalinterest.group.service.dto.LikeDTO;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.util.IpAdrressUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

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
	 * @Description: 点赞
	 * @author: shuyu.wang
	 * @date: 2019-08-15 17:39
	 * @param sourceId
	 * @return null
	 */
	@ApiOperation(value = "点赞")
	@GetMapping(value = "/new/{sourceId}")
	@BlogOperation(value = "博客点赞")
	public ApiResult<String> addCollection(@PathVariable("sourceId") Long sourceId,@RequestParam(value = "type")Short type) {
		ApiResult apiResult = new ApiResult();
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();
		LikeDTO pojo= LikeDTO.builder().sourceId(sourceId).ipAddress(IpAdrressUtil.getIpAdrress(request)).type(type).build();
		ReturnClass insert = likeService.insert(pojo);
		if (insert.isSuccess()) {
			apiResult.success(insert.getMsg(), null);
		} else {
			apiResult.setMsg(insert.getMsg());
		}
		return apiResult;
	}

	/**
	 * @Description: 取消点赞
	 * @author: shuyu.wang
	 * @date: 2019-08-15 17:39
	 * @param sourceId
	 * @return null
	 */
	@ApiOperation(value = "取消点赞")
	@GetMapping(value = "/del/{sourceId}")
	@BlogOperation(value = "取消点赞")
	public ApiResult<String> delCollection(@PathVariable("sourceId") Long sourceId,@RequestParam(value = "type")Short type) {
		ApiResult apiResult = new ApiResult();
		LikeDTO pojo= LikeDTO.builder().sourceId(sourceId).type(type).build();
		ReturnClass insert = likeService.del(pojo);
		if (insert.isSuccess()) {
			apiResult.success(insert.getMsg(), null);
		} else {
			apiResult.setMsg(insert.getMsg());
		}
		return apiResult;
	}
}
