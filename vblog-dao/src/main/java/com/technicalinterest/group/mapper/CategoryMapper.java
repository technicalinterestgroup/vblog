package com.technicalinterest.group.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.technicalinterest.group.dao.Category;

@Mapper
public interface CategoryMapper {
    int insert(@Param("pojo") Category pojo);

    int insertSelective(@Param("pojo") Category pojo);

    int insertList(@Param("pojos") List<Category> pojo);

    int update(@Param("pojo") Category pojo);
}
