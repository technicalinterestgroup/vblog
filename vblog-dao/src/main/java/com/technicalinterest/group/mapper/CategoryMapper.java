package com.technicalinterest.group.mapper;

import com.technicalinterest.group.dto.CategoryDTO;
import com.technicalinterest.group.dto.TagDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.technicalinterest.group.dao.Category;

@Mapper
public interface CategoryMapper {

    Integer insertSelective(@Param("pojo") Category pojo);

    Integer update(@Param("pojo") Category pojo);

    List<CategoryDTO> queryCategoryListByUser(String userName);

    Category queryCategory(Category pojo);

    Integer delCategory(@Param("id")Long id);
}
