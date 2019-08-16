package com.technicalinterest.group.mapper;

import com.technicalinterest.group.dao.VSystem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VSystemMapper {

    Integer insertSelective(@Param("pojo") VSystem pojo);

    Integer update(@Param("pojo") VSystem pojo);

    VSystem querySystemByUser(@Param("userName")String userName);
}
