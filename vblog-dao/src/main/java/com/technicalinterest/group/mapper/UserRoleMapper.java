package com.technicalinterest.group.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.technicalinterest.group.dao.UserRole;

@Mapper
public interface UserRoleMapper {
    int insert(@Param("pojo") UserRole pojo);

    int insertSelective(@Param("pojo") UserRole pojo);

    int insertList(@Param("pojos") List<UserRole> pojo);

    int update(@Param("pojo") UserRole pojo);
}
