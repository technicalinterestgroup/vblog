package com.technicalinterest.group.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.technicalinterest.group.dao.Role;

@Mapper
public interface RoleMapper {
    int insert(@Param("pojo") Role pojo);

    int insertSelective(@Param("pojo") Role pojo);

    int insertList(@Param("pojos") List<Role> pojo);

    int update(@Param("pojo") Role pojo);
}
