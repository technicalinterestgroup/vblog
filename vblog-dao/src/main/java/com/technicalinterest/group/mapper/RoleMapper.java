package com.technicalinterest.group.mapper;

import com.technicalinterest.group.dto.RoleDTO;
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

    /**
     * 角色列表
     * @param pojo
     * @return
     */
    List<RoleDTO> getRoleList(@Param("pojo") Role pojo);

    Role getRole(@Param("pojo") Role pojo);
}
