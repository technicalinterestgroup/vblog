package com.technicalinterest.group.service;

import com.technicalinterest.group.dao.Role;
import com.technicalinterest.group.dto.RoleDTO;
import com.technicalinterest.group.service.dto.ReturnClass;

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
}
