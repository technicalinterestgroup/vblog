package com.technicalinterest.group.service.impl;

import com.github.pagehelper.PageHelper;
import com.technicalinterest.group.dao.*;
import com.technicalinterest.group.dto.ArticlesDTO;
import com.technicalinterest.group.dto.RoleDTO;
import com.technicalinterest.group.dto.TreeDTO;
import com.technicalinterest.group.mapper.AuthMapper;
import com.technicalinterest.group.mapper.RoleAuthMapper;
import com.technicalinterest.group.mapper.RoleMapper;
import com.technicalinterest.group.mapper.UserMapper;
import com.technicalinterest.group.service.Enum.ResultEnum;
import com.technicalinterest.group.service.RoleService;
import com.technicalinterest.group.service.constant.UserConstant;
import com.technicalinterest.group.service.dto.AuthListDTO;
import com.technicalinterest.group.service.dto.EditUserDTO;
import com.technicalinterest.group.service.dto.PageBean;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.util.ListBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    /**
     * 查询菜单列表
     *
     * @param pojo
     * @return
     */
    @Override
    public ReturnClass getAuthList(Auth pojo, PageBase pageBase) {
        List<AuthListDTO> resultList=new ArrayList<>();
        pojo.setParentId(0L);
        Integer authListCount = authMapper.getAuthListCount(pojo);
        if (authListCount<1){
            return ReturnClass.fail(ResultEnum.NO_DATA.getMsg());
        }
        PageHelper.startPage(pageBase.getCurrentPage(), pageBase.getPageSize());
        List<Auth> authList = authMapper.getAuthList(pojo);
        for (Auth entity:authList) {
            Auth child=new Auth();
            child.setParentId(entity.getId());
            child.setType(pojo.getType());
            List<Auth> authList1 = authMapper.getAuthList(child);
            AuthListDTO target=new AuthListDTO();
            BeanUtils.copyProperties(entity, target);
            target.setIsParent(true);
            if (!authList1.isEmpty()){
                List list = ListBeanUtils.copyProperties(authList1, AuthListDTO.class);
                target.setChildren(list);
            }
            resultList.add(target);
        }
        PageBean<AuthListDTO> pageBean = new PageBean<>(resultList, pageBase.getCurrentPage(), pageBase.getPageSize(), authListCount);
        return ReturnClass.success(pageBean);
    }

    @Override
    public ReturnClass saveOrUpdateAuth(Auth auth) {
        if (Objects.isNull(auth.getId())){
            Auth auth1 = authMapper.getAuth(auth);
            if (Objects.nonNull(auth1)){
                return ReturnClass.fail("菜单重复");
            }
            int a=authMapper.insertSelective(auth);
            if (a>0){
                return ReturnClass.success();
            }
        }else {
            Auth auth1 = authMapper.getAuth(auth);
            if (Objects.nonNull(auth1)){
                if (auth.getId().longValue()!=auth1.getId()){
                    return ReturnClass.fail("菜单重复");
                }
            }
            int a= authMapper.update(auth);
            if (a>0){
                return ReturnClass.success();
            }
        }
        return ReturnClass.fail("操作失败");
    }

    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    @Override
    public ReturnClass delAuth(Long id) {
        Integer integer = authMapper.delAuth(id);
        if (integer>0){
            return ReturnClass.success();
        }
        return ReturnClass.fail();
    }
}
