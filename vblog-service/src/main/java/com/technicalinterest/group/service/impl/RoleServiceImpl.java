package com.technicalinterest.group.service.impl;

import com.technicalinterest.group.dao.Role;
import com.technicalinterest.group.dao.RoleAuth;
import com.technicalinterest.group.dao.User;
import com.technicalinterest.group.dto.RoleDTO;
import com.technicalinterest.group.dto.TreeDTO;
import com.technicalinterest.group.mapper.AuthMapper;
import com.technicalinterest.group.mapper.RoleAuthMapper;
import com.technicalinterest.group.mapper.RoleMapper;
import com.technicalinterest.group.mapper.UserMapper;
import com.technicalinterest.group.service.RoleService;
import com.technicalinterest.group.service.constant.UserConstant;
import com.technicalinterest.group.service.dto.EditUserDTO;
import com.technicalinterest.group.service.dto.ReturnClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Slf4j
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleAuthMapper roleAuthMapper;
    @Autowired
    private AuthMapper authMapper;

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


    /**
     * 权限树
     *
     * @param roleId
     * @param type
     * @return
     */
    @Override
    public ReturnClass getAuthTree(Long roleId, Short type) {
        List<RoleAuth> roleAuthList = roleAuthMapper.getRoleAuthList(roleId, type);
        List<TreeDTO> parentTree = getParentTree(type, roleAuthList);
        return ReturnClass.success(parentTree);
    }



    public  List<TreeDTO> getParentTree(Short type,List<RoleAuth> selectList){
        List<TreeDTO> tree = authMapper.getTree(type, 0L);
        for (TreeDTO entity:tree) {
            List<TreeDTO> childTree = getChildTree(type, entity,selectList);
            if (!childTree.isEmpty()){
                entity.setChildren(childTree);
            }else {
                for (RoleAuth roleAuthDTO: selectList) {
                    if (entity.getId().longValue()==roleAuthDTO.getAuthId()){
                        log.info("entity.getId()"+entity.getId());
                        log.info("roleAuthDTO.getAuthId()"+roleAuthDTO.getAuthId());
                        entity.setChecked(true);
                        break;
                    }
                }
            }
        }
        return tree;
    }

    public  List<TreeDTO> getChildTree(Short type,TreeDTO parent,List<RoleAuth> selectList){
        List<TreeDTO> tree = authMapper.getTree(type, parent.getId());
        int num=0;
        for (TreeDTO entity:tree) {
            for (RoleAuth roleAuthDTO: selectList) {
                if (entity.getId().longValue()==roleAuthDTO.getAuthId()){
                    log.info("entity.getId()"+entity.getId());
                    log.info("roleAuthDTO.getAuthId()"+roleAuthDTO.getAuthId());
                    entity.setChecked(true);
                    num++;
                    break;
                }
            }
            List<TreeDTO> childTree = getChildTree(type, entity,selectList);
            if (!childTree.isEmpty()){
                parent.setChildren(childTree);
            }
        }
        return tree;
    }

    /**
     * 修改角色权限
     * @param roleAuthList
     * @return
     */
    @Override
    @Transactional
    public ReturnClass editAuthRole(Long roleId,List<RoleAuth> roleAuthList) {
        Integer integer = roleAuthMapper.delRoleAuthByRoleId(roleId);
        int i = roleAuthMapper.insertList(roleAuthList);
        if (i>0){
            return ReturnClass.success();
        }
        return ReturnClass.fail();
    }
}
