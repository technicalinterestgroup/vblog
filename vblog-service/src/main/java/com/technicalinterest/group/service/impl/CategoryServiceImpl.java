package com.technicalinterest.group.service.impl;

import com.technicalinterest.group.dao.Category;
import com.technicalinterest.group.mapper.CategoryMapper;
import com.technicalinterest.group.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Integer insertSelective(Category pojo){
        return categoryMapper.insertSelective(pojo);
    }
    @Override
    public Integer update(Category pojo){
        return categoryMapper.update(pojo);
    }
}
