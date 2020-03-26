package com.technicalinterest.group.service;

import com.technicalinterest.group.dao.Auth;
import com.technicalinterest.group.dao.Role;
import com.technicalinterest.group.dao.RoleAuth;
import com.technicalinterest.group.dto.RoleDTO;
import com.technicalinterest.group.service.dto.ReturnClass;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName: RoleService
 * @Author: shuyu.wang
 * @Description: 角色
 * @Date: 2020/3/25 11:15
 * @Version: 1.0
 */
public interface RoleService {

    ReturnClass userRoleList(Role role);


    ReturnClass saveRole(Role role);

    /**
     * 权限树
     * @param roleId
     * @param type
     * @return
     */
    ReturnClass getAuthTree(Long roleId,Short type);

    /**
     * 修改角色权限
     * @param roleAuthList
     * @return
     */
    ReturnClass editAuthRole(Long roleId, List<RoleAuth> roleAuthList);
    /**
     * 查询菜单列表
     * @param pojo
     * @return
     */
    ReturnClass getAuthList(Auth pojo);
}
