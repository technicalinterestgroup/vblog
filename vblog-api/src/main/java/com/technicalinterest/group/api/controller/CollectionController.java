package com.technicalinterest.group.api.controller;

import com.technicalinterest.group.api.param.PageBaseParam;
import com.technicalinterest.group.api.vo.ApiResult;
import com.technicalinterest.group.api.vo.ArticlesVO;
import com.technicalinterest.group.api.vo.CategoryVO;
import com.technicalinterest.group.api.vo.CollectionVO;
import com.technicalinterest.group.dao.PageBase;
import com.technicalinterest.group.dto.ArticlesDTO;
import com.technicalinterest.group.dto.CategoryDTO;
import com.technicalinterest.group.dto.CollectionDTO;
import com.technicalinterest.group.service.CollectionService;
import com.technicalinterest.group.service.annotation.BlogOperation;
import com.technicalinterest.group.service.dto.PageBean;
import com.technicalinterest.group.service.dto.ReturnClass;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @package: com.technicalinterest.group.api.controller
 * @className: CollectionController
 * @description: 收藏控制层
 * @author: Shuyu.Wang
 * @date: 2019-08-22 12:50
 * @since: 0.1
 **/
@Api(tags = "博客收藏")
@RestController
@RequestMapping("collection")
public class CollectionController {
	@Autowired
	private CollectionService collectionService;

	/**
	 * @Description: 博客收藏列表
	 * @author: shuyu.wang
	 * @date: 2019-08-15 17:39
	 * @param userName
	 * @return null
	 */
	@ApiOperation(value = "博客收藏列表", notes = "博客收藏列表")
	@GetMapping(value = "/list/{userName}")
	@BlogOperation(value = "博客收藏列表")
	public ApiResult<PageBean<CollectionVO>> listCategory(@PathVariable("userName") String userName, @Validated PageBaseParam pageBaseParam) {
		ApiResult apiResult = new ApiResult();
		PageBase pageBase = new PageBase();
		BeanUtils.copyProperties(pageBaseParam, pageBase);
		ReturnClass listCollection = collectionService.queryListCollection(userName, pageBase);
		if (listCollection.isSuccess()) {
			PageBean<CollectionDTO> pageBean = (PageBean<CollectionDTO>) listCollection.getData();
			List<CollectionVO> list = new ArrayList<>();
			for (CollectionDTO entity : pageBean.getPageData()) {
				CollectionVO collectionVO = new CollectionVO();
				BeanUtils.copyProperties(entity, collectionVO);
				list.add(collectionVO);
			}
			PageBean<CollectionVO> pageInfo = new PageBean<CollectionVO>();
			BeanUtils.copyProperties(listCollection.getData(), pageInfo);
			pageInfo.setPageData(list);
			apiResult.success(pageInfo);

		} else {
			apiResult.setMsg(listCollection.getMsg());
		}
		return apiResult;
	}

	/**
	 * @Description: 添加收藏
	 * @author: shuyu.wang
	 * @date: 2019-08-15 17:39
	 * @param articleId
	 * @return null
	 */
	@ApiOperation(value = "添加收藏", notes = "添加收藏")
	@GetMapping(value = "/new/{articleId}")
	@BlogOperation(value = "添加收藏")
	public ApiResult<String> addCollection(@PathVariable("articleId") Long articleId) {
		ApiResult apiResult = new ApiResult();
		ReturnClass insert = collectionService.insert(articleId);
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
	 * @param id
	 * @return null
	 */
	@ApiOperation(value = "取消收藏", notes = "取消收藏")
	@GetMapping(value = "/del/{id}")
	@BlogOperation(value = "取消收藏")
	public ApiResult<String> delCollection(@PathVariable("id") Long id) {
		ApiResult apiResult = new ApiResult();
		ReturnClass insert = collectionService.del(id);
		if (insert.isSuccess()) {
			apiResult.success(insert.getMsg(), null);
		} else {
			apiResult.setMsg(insert.getMsg());
		}
		return apiResult;
	}
}
