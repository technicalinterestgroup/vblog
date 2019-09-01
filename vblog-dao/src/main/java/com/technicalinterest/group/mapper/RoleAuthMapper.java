package com.technicalinterest.group.mapper;

import com.technicalinterest.group.dto.RoleAuthDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.technicalinterest.group.dao.RoleAuth;

@Mapper
public interface RoleAuthMapper {
    int insert(@Param("pojo") RoleAuth pojo);

    int insertSelective(@Param("pojo") RoleAuth pojo);

    int insertList(@Param("pojos") List<RoleAuth> pojo);

    int update(@Param("pojo") RoleAuth pojo);


    List<RoleAuthDTO> queryAuthByRole(@Param("roleId")Long roleId,@Param("type")Short type);
}
