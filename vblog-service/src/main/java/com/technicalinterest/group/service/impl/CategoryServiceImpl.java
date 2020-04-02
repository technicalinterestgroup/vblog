package com.technicalinterest.group.service.impl;

import com.technicalinterest.group.dao.Category;
import com.technicalinterest.group.dto.CategoryDTO;
import com.technicalinterest.group.mapper.CategoryMapper;
import com.technicalinterest.group.service.CategoryService;
import com.technicalinterest.group.service.UserService;
import com.technicalinterest.group.service.constant.CategoryConstant;
import com.technicalinterest.group.service.Enum.ResultEnum;
import com.technicalinterest.group.service.dto.EditCategoryDTO;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.exception.VLogException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if (Objects.isNull(pojo.getId())){
            Category category = new Category();
            BeanUtils.copyProperties(pojo, category);
            category.setUserName(userService.getUserNameByLoginToken());
            //名称是否重复
            Category category2 = categoryMapper.queryCategory(Category.builder().name(category.getName()).userName(category.getUserName()).build());
            if (Objects.nonNull(category2)) {
                return ReturnClass.fail(CategoryConstant.CATEGORY_REPEAT);
            }
            Integer flag = categoryMapper.insertSelective(category);
            if (flag > 0) {
                return ReturnClass.success(CategoryConstant.SUS_ADD);
            }
            return ReturnClass.fail(CategoryConstant.FAIL_ADD);
        }else {
            ReturnClass update = update(pojo);
            return update;
        }

    }

    @Override
    public ReturnClass update(EditCategoryDTO pojo) {
        Category category = new Category();
        category.setId(pojo.getId());
        category.setUserName(userService.getUserNameByLoginToken());

        //数据是否存在
        Category category1 = categoryMapper.queryCategory(category);
        if (Objects.isNull(category1)) {
            throw new VLogException(ResultEnum.NO_DATA);
        }
        BeanUtils.copyProperties(pojo, category);

        //是否是本人操作
        if (!StringUtils.equals(category1.getUserName(), category.getUserName())) {
            throw new VLogException(ResultEnum.NO_AUTH);
        }
        //名称是否重复
        Category category2 = categoryMapper.queryCategory(Category.builder().name(category.getName()).userName(category.getUserName()).build());
        if (Objects.nonNull(category2)) {
            if (!StringUtils.equals(category2.getName(), pojo.getName())) {
                return ReturnClass.fail(CategoryConstant.CATEGORY_REPEAT);
            }
        }
        Integer flag = categoryMapper.update(category);
        if (flag > 0) {
            return ReturnClass.success(CategoryConstant.SUS_EDITE);
        }
        return ReturnClass.fail(CategoryConstant.FAIL_EDITE);
    }

    @Override
    public ReturnClass listCategoryByUser(String userName) {
        List<CategoryDTO> categoryDTOList = categoryMapper.queryCategoryListByUser(userName,null);
        if (categoryDTOList.isEmpty()) {
            return ReturnClass.fail(CategoryConstant.NO_DATA);
        }
        return ReturnClass.success(categoryDTOList);
    }

    @Override
    public ReturnClass delCategory(Long id) {
        Category category = new Category();
        category.setId(id);
        category.setUserName(userService.getUserNameByLoginToken());

        //数据是否存在
        Category category1 = categoryMapper.queryCategory(category);
        if (Objects.isNull(category1)) {
            throw new VLogException(ResultEnum.NO_DATA);
        }
        //是否是本人操作
        if (!StringUtils.equals(category1.getUserName(), category.getUserName())) {
            throw new VLogException(ResultEnum.NO_AUTH);
        }
        Integer articleCountCategory = categoryMapper.getArticleCountCategory(id);
        if (articleCountCategory > 0) {
            return ReturnClass.fail(CategoryConstant.HAVA_ARTICLE);
        }
        Integer integer = categoryMapper.delCategory(id);
        if (integer > 0) {
            return ReturnClass.success(CategoryConstant.SUS_DEL);
        }
        return ReturnClass.fail(CategoryConstant.FAIL_DEL);
    }

    @Override
    public ReturnClass listCategoryByUser() {
        List<CategoryDTO> categoryDTOS = categoryMapper.queryCategorysByUser(userService.getUserNameByLoginToken());
        return ReturnClass.success(categoryDTOS);
    }

    /**
     * 文章分类列表
     * @return
     */
    @Override
    public ReturnClass categoryListPage(String name) {
        List<CategoryDTO> categoryDTOList = categoryMapper.queryCategoryListByUser(userService.getUserNameByLoginToken(),name);
        if (categoryDTOList.isEmpty()) {
            return ReturnClass.fail(CategoryConstant.NO_DATA);
        }
        return ReturnClass.success(categoryDTOList);
    }
}
