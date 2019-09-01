package com.technicalinterest.group.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.technicalinterest.group.dao.Auth;

@Mapper
public interface AuthMapper {
    int insert(@Param("pojo") Auth pojo);

    int insertSelective(@Param("pojo") Auth pojo);

    int insertList(@Param("pojos") List<Auth> pojo);

    int update(@Param("pojo") Auth pojo);
}
