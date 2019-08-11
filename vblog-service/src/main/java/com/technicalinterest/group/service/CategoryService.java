package com.technicalinterest.group.service;

import com.technicalinterest.group.dao.Category;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

import com.technicalinterest.group.mapper.CategoryMapper;

public interface CategoryService{



     Integer insertSelective(Category pojo);

     Integer update(Category pojo);
}
