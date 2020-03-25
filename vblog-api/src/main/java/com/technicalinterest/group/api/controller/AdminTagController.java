package com.technicalinterest.group.api.controller;

import com.github.blackshadowwalker.spring.distributelock.annotation.DistributeLock;
import com.technicalinterest.group.api.param.EditCategoryParam;
import com.technicalinterest.group.api.param.EditTagParam;
import com.technicalinterest.group.api.param.NewCategoryParam;
import com.technicalinterest.group.api.param.NewTagParam;
import com.technicalinterest.group.api.vo.ApiResult;
import com.technicalinterest.group.api.vo.ArticleTitleVO;
import com.technicalinterest.group.api.vo.CategoryVO;
import com.technicalinterest.group.api.vo.TagVO;
import com.technicalinterest.group.dto.CategoryDTO;
import com.technicalinterest.group.dto.TagDTO;
import com.technicalinterest.group.service.CategoryService;
import com.technicalinterest.group.service.TagService;
import com.technicalinterest.group.service.annotation.BlogOperation;
import com.technicalinterest.group.service.dto.EditCategoryDTO;
import com.technicalinterest.group.service.dto.EditTagDTO;
import com.technicalinterest.group.service.dto.ReturnClass;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @package: com.technicalinterest.group.api.controller
 * @className: CategoryController
 * @description: 博客标签controller
 * @author: Shuyu.Wang
 * @date: 2019-08-15 17:15
 * @since: 0.1
 **/
@Api(tags = "博客标签")
@RestController
@RequestMapping("admin/tag")
public class AdminTagController {
	@Autowired
	private TagService tagService;


	/**
	 * @Description: 博客标签列表
	 * @author: shuyu.wang
	 * @date: 2019-08-15 17:39
	 * @param name
	 * @return null
	 */
	@ApiOperation(value = "博客标签列表", notes = "博客标签")
	@GetMapping(value = "/list")
	@BlogOperation(value = "博客标签列表")
	public ApiResult<List<TagVO>> listCategory(@RequestParam(value = "name",required = false) String name) {
		ApiResult apiResult = new ApiResult();
		ReturnClass listCategory = tagService.listTagByAdmin(name);
		if (listCategory.isSuccess()) {
			List<TagVO> list = new ArrayList<TagVO>();
			List<TagDTO> tagDTOS = (List<TagDTO>) listCategory.getData();
			for (TagDTO entity : tagDTOS) {
				TagVO tagVO = new TagVO();
				BeanUtils.copyProperties(entity, tagVO);
				list.add(tagVO);
			}
			apiResult.success(list);
		} else {
			apiResult.setMsg(listCategory.getMsg());
		}
		return apiResult;
	}

	/**
	 * @Description: 博客标签列表
	 * @author: shuyu.wang
	 * @date: 2019-08-15 17:39
	 * @return null
	 */
	@ApiOperation(value = "博客标签列表下拉选择")
	@GetMapping(value = "/list/dic")
	@BlogOperation(value = "博客标签列表")
	public ApiResult<List<TagVO>> listTagDics() {
		ApiResult apiResult = new ApiResult();
		ReturnClass listCategory = tagService.allTagListDic();
		if (listCategory.isSuccess()) {
			List<TagVO> list = new ArrayList<TagVO>();
			List<TagDTO> tagDTOS = (List<TagDTO>) listCategory.getData();
			for (TagDTO entity : tagDTOS) {
				TagVO tagVO = new TagVO();
				BeanUtils.copyProperties(entity, tagVO);
				list.add(tagVO);
			}
			apiResult.success(list);
		} else {
			apiResult.setMsg(listCategory.getMsg());
		}
		return apiResult;
	}

	/**
	 * @Description: 编辑博客标签
	 * @author: shuyu.wang
	 * @date: 2019-08-15 17:39
	 * @param editTagParam
	 * @return null
	 */
	@ApiOperation(value = "编辑博客标签", notes = "更新")
	@PostMapping(value = "/edit")
	@BlogOperation(value = "编辑博客标签")
	@DistributeLock( key = "#editTagParam.id", timeout = 2, expire = 1, errMsg = "00000")
	public ApiResult<String> editCategory(@Valid @RequestBody EditTagParam editTagParam) {
		ApiResult apiResult = new ApiResult();
		EditTagDTO editTagDTO = new EditTagDTO();
		BeanUtils.copyProperties(editTagParam, editTagDTO);
		ReturnClass update = tagService.update(editTagDTO);
		if (update.isSuccess()) {
			apiResult.success(update.getMsg(),null);
		} else {
			apiResult.setMsg(update.getMsg());
		}
		return apiResult;
	}

	/**
	 * @Description: 编辑博客标签
	 * @author: shuyu.wang
	 * @date: 2019-08-15 17:39
	 * @param newTagParam
	 * @return null
	 */
	@ApiOperation(value = "新增博客标签", notes = "新增")
	@PostMapping(value = "/new")
	@BlogOperation(value = "新增博客标签")
	public ApiResult<String> newCategory(@Valid @RequestBody NewTagParam newTagParam) {
		ApiResult apiResult = new ApiResult();
		EditTagDTO editTagDTO = new EditTagDTO();
		BeanUtils.copyProperties(newTagParam, editTagDTO);
		ReturnClass update = tagService.insertSelective(editTagDTO);
		if (update.isSuccess()) {
			apiResult.success(update.getMsg(),null);
		} else {
			apiResult.setMsg(update.getMsg());
		}
		return apiResult;
	}

	/**
	 * @Description: 标签删除
	 * @author: shuyu.wang
	 * @date: 2019-08-16 23:18
	 * @param id
	 * @return null
	 */
	@ApiOperation(value = "博客标签删除", notes = "删除")
	@GetMapping(value = "/del/{id}")
	@BlogOperation(value = "博客标签删除")
	public ApiResult<String> delTag(@PathVariable("id") Long id) {
		ApiResult apiResult = new ApiResult();
		ReturnClass delTag = tagService.delTag(id);
		if (delTag.isSuccess()) {
			apiResult.success(delTag.getMsg(),null);
		} else {
			apiResult.fail(delTag.getMsg());
		}
		return apiResult;
	}
}
