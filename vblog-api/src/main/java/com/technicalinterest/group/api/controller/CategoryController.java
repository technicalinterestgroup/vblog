package com.technicalinterest.group.api.controller;

import com.technicalinterest.group.api.param.EditCategoryParam;
import com.technicalinterest.group.api.param.NewCategoryParam;
import com.technicalinterest.group.api.vo.ApiResult;
import com.technicalinterest.group.api.vo.ArticleTitleVO;
import com.technicalinterest.group.api.vo.CategoryVO;
import com.technicalinterest.group.dto.CategoryDTO;
import com.technicalinterest.group.service.CategoryService;
import com.technicalinterest.group.service.dto.EditCategoryDTO;
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
 * @description: 文章分类controller
 * @author: Shuyu.Wang
 * @date: 2019-08-15 17:15
 * @since: 0.1
 **/
@Api(tags = "文章分类")
@RestController
@RequestMapping("category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	private static final Boolean authCheck = true;

	/**
	 * @Description: 文章分类列表
	 * @author: shuyu.wang
	 * @date: 2019-08-15 17:39
	 * @param userName
	 * @return null
	 */
	@ApiOperation(value = "文章分类", notes = "文章分类")
	@GetMapping(value = "/list/{userName}")
	public ApiResult<List<CategoryVO>> listCategory(@PathVariable("userName") String userName) {
		ApiResult apiResult = new ApiResult();
		ReturnClass listCategory = categoryService.listCategoryByUser(authCheck, userName);
		if (listCategory.isSuccess()) {
			List<CategoryVO> list = new ArrayList<CategoryVO>();
			List<CategoryDTO> categoryDTOList = (List<CategoryDTO>) listCategory.getData();
			for (CategoryDTO entity : categoryDTOList) {
				CategoryVO categoryVO = new CategoryVO();
				BeanUtils.copyProperties(entity, categoryVO);
				list.add(categoryVO);
			}
			apiResult.success(list);
		} else {
			apiResult.setMsg(listCategory.getMsg());
		}
		return apiResult;
	}

	/**
	 * @Description: 编辑文章分类
	 * @author: shuyu.wang
	 * @date: 2019-08-15 17:39
	 * @param editCategoryParam
	 * @return null
	 */
	@ApiOperation(value = "编辑文章分类", notes = "更新")
	@PostMapping(value = "/edit")
	public ApiResult<String> editCategory(@Valid @RequestBody EditCategoryParam editCategoryParam) {
		ApiResult apiResult = new ApiResult();
		EditCategoryDTO editCategoryDTO = new EditCategoryDTO();
		BeanUtils.copyProperties(editCategoryParam, editCategoryDTO);
		ReturnClass update = categoryService.update(editCategoryDTO);
		if (update.isSuccess()) {
			apiResult.success();
		} else {
			apiResult.setMsg(update.getMsg());
		}
		return apiResult;
	}

	/**
	 * @Description: 新增文章分类
	 * @author: shuyu.wang
	 * @date: 2019-08-15 17:39
	 * @param newCategoryParam
	 * @return null
	 */
	@ApiOperation(value = "新增文章分类", notes = "新增")
	@PostMapping(value = "/new")
	public ApiResult<String> newCategory(@Valid @RequestBody NewCategoryParam newCategoryParam) {
		ApiResult apiResult = new ApiResult();
		EditCategoryDTO editCategoryDTO = new EditCategoryDTO();
		BeanUtils.copyProperties(newCategoryParam, editCategoryDTO);
		ReturnClass update = categoryService.insertSelective(editCategoryDTO);
		if (update.isSuccess()) {
			apiResult.success();
		} else {
			apiResult.setMsg(update.getMsg());
		}
		return apiResult;
	}

	/**
	 * @Description: 分类删除
	 * @author: shuyu.wang
	 * @date: 2019-08-16 23:18
	 * @param id
	 * @return null
	*/
	@ApiOperation(value = "分类删除", notes = "删除")
	@GetMapping(value = "/del/{id}")
	public ApiResult<String> delCategory(@PathVariable("id") Long id) {
		ApiResult apiResult = new ApiResult();
		ReturnClass delCategory = categoryService.delCategory(id);
		if (delCategory.isSuccess()) {
			apiResult.success(null, delCategory.getMsg());
		} else {
			apiResult.fail(delCategory.getMsg());
		}
		return apiResult;
	}
}
