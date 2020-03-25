package com.technicalinterest.group.service.impl;

import com.technicalinterest.group.dao.Role;
import com.technicalinterest.group.dto.RoleDTO;
import com.technicalinterest.group.mapper.RoleMapper;
import com.technicalinterest.group.service.RoleService;
import com.technicalinterest.group.service.dto.ReturnClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName: RoleServiceImpl
 * @Author: shuyu.wang
 * @Description:
 * @Date: 2020/3/25 11:15
 * @Version: 1.0
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public ReturnClass userRoleList(Role role) {
        List<RoleDTO> roleList = roleMapper.getRoleList(role);
        return ReturnClass.success(roleList);
    }

    @Override
    public ReturnClass saveRole(Role role) {
        if (role.getId()==null){
            Role query=new Role();
            query.setName(role.getName());
            Role role1 = roleMapper.getRole(query);
            if (Objects.nonNull(role1)){
                return ReturnClass.fail("角色名重复");
            }
            role.setCreateTime(new Date());
            int i = roleMapper.insertSelective(role);
            if (i>0){
                return ReturnClass.success();
            }
        }else {
            Role query=new Role();
            query.setName(role.getName());
            Role role1 = roleMapper.getRole(query);
            if (Objects.nonNull(role1)){
                if (role.getId().longValue()!=role1.getId()){
                    return ReturnClass.fail("角色名重复");
                }
            }
            role.setUpdateTime(new Date());
            int i =roleMapper.update(role);
            if (i>0){
                return ReturnClass.success();
            }
        }
        return ReturnClass.fail("保存失败");
    }
}
