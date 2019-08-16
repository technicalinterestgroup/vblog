package com.technicalinterest.group.service.impl;

import com.technicalinterest.group.dao.Category;
import com.technicalinterest.group.dto.CategoryDTO;
import com.technicalinterest.group.mapper.CategoryMapper;
import com.technicalinterest.group.service.CategoryService;
import com.technicalinterest.group.service.UserService;
import com.technicalinterest.group.service.constant.Constant;
import com.technicalinterest.group.service.constant.ResultEnum;
import com.technicalinterest.group.service.dto.EditCategoryDTO;
import com.technicalinterest.group.service.dto.EditTagDTO;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.dto.UserDTO;
import com.technicalinterest.group.service.exception.VLogException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private UserService userService;

	@Override
	public ReturnClass insertSelective(EditCategoryDTO pojo) {
		Category category = new Category();
		BeanUtils.copyProperties(pojo, category);
		ReturnClass userByToken = userService.getUserByToken();
		if (userByToken.isSuccess()) {
			UserDTO userDTO = (UserDTO) userByToken.getData();
			category.setUserName(userDTO.getUserName());
		} else {
			throw new VLogException(ResultEnum.USERINFO_ERROR);
		}
		//名称是否重复
		Category category2 = categoryMapper.queryCategory(Category.builder().name(category.getName()).userName(category.getUserName()).build());
		if (Objects.nonNull(category2)) {
			return ReturnClass.fail(Constant.CATEGORY_REPEAT);
		}
		Category category1 = categoryMapper.queryCategory(category);
		if (Objects.nonNull(category1)) {
			return ReturnClass.fail(Constant.CATEGORY_REPEAT);
		}
		Integer flag = categoryMapper.insertSelective(category);
		if (flag > 0) {
			return ReturnClass.success();
		}
		return ReturnClass.fail();
	}

	@Override
	public ReturnClass update(EditCategoryDTO pojo) {
		Category category = new Category();
		category.setId(pojo.getId());
		//数据是否存在
		Category category1 = categoryMapper.queryCategory(category);
		if (Objects.isNull(category1)) {
			throw new VLogException(ResultEnum.NO_DATA);
		}
		BeanUtils.copyProperties(pojo, category);
		//名称是否重复
		Category category2 = categoryMapper.queryCategory(Category.builder().name(category.getName()).userName(category.getUserName()).build());
		if (Objects.nonNull(category2)) {
			if (!StringUtils.equals(category2.getName(), pojo.getName())) {
				return ReturnClass.fail(Constant.CATEGORY_REPEAT);
			}
		}
		ReturnClass userByToken = userService.getUserByToken();
		if (userByToken.isSuccess()) {
			UserDTO userDTO = (UserDTO) userByToken.getData();
			category.setUserName(userDTO.getUserName());
		} else {
			throw new VLogException(ResultEnum.USERINFO_ERROR);
		}

		//是否是本人操作
		if (!StringUtils.equals(category1.getUserName(), category.getUserName())) {
			throw new VLogException(ResultEnum.NO_AUTH);
		}
		Integer flag = categoryMapper.update(category);
		if (flag > 0) {
			return ReturnClass.success();
		}
		return ReturnClass.fail();
	}

	@Override
	public ReturnClass listCategoryByUser(Boolean authCheck, String userName) {
		ReturnClass returnClass = userService.getUserByuserName(authCheck, userName);
		if (!returnClass.isSuccess()) {
			throw new VLogException(ResultEnum.NO_URL);
		}
		List<CategoryDTO> categoryDTOList = categoryMapper.queryCategoryListByUser(userName);
		if (categoryDTOList.isEmpty()) {
			return ReturnClass.fail(ResultEnum.NO_DATA.getMsg());
		}
		return ReturnClass.success(categoryDTOList);
	}

	@Override
	public ReturnClass delCategory(Long id) {
		Category category = new Category();
		category.setId(id);
		//数据是否存在
		Category category1 = categoryMapper.queryCategory(category);
		if (Objects.isNull(category1)) {
			throw new VLogException(ResultEnum.NO_DATA);
		}
		ReturnClass userByToken = userService.getUserByToken();
		if (userByToken.isSuccess()) {
			UserDTO userDTO = (UserDTO) userByToken.getData();
			category.setUserName(userDTO.getUserName());
		} else {
			throw new VLogException(ResultEnum.USERINFO_ERROR);
		}
		//是否是本人操作
		if (!StringUtils.equals(category1.getUserName(), category.getUserName())) {
			throw new VLogException(ResultEnum.NO_AUTH);
		}
		Integer integer = categoryMapper.delCategory(id);
		if (integer > 0) {
			return ReturnClass.success();
		}
		return ReturnClass.fail();
	}
}
