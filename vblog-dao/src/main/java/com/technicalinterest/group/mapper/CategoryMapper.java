package com.technicalinterest.group.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.technicalinterest.group.dao.Category;

@Mapper
public interface CategoryMapper {
    Integer insert(@Param("pojo") Category pojo);

    Integer insertSelective(@Param("pojo") Category pojo);

    Integer insertList(@Param("pojos") List<Category> pojo);

    Integer update(@Param("pojo") Category pojo);
}
