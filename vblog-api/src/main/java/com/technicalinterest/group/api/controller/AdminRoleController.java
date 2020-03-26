package com.technicalinterest.group.api.controller;

import com.technicalinterest.group.api.param.EditRoleAuthParam;
import com.technicalinterest.group.api.vo.EditRoleParam;
import com.technicalinterest.group.api.vo.QueryRoleParam;
import com.technicalinterest.group.api.vo.ApiResult;
import com.technicalinterest.group.api.vo.RoleVO;
import com.technicalinterest.group.dao.Role;
import com.technicalinterest.group.service.RoleService;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.util.ListBeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: AdminRoleController
 * @Author: shuyu.wang
 * @Description: 角色控制层
 * @Date: 2020/3/24 11:59
 * @Version: 1.0
 */
@Api(tags = "管理员模块")
@RestController
@RequestMapping("admin/role")
public class AdminRoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "角色列表")
    @GetMapping(value = "/list")
    public ApiResult<List<RoleVO>> roleList(QueryRoleParam queryRoleParam) {
        ApiResult apiResult = new ApiResult();
        Role role=new Role();
        BeanUtils.copyProperties(queryRoleParam, role);
        ReturnClass listRole = roleService.userRoleList(role);
        if (listRole.isSuccess()) {
            List list = ListBeanUtils.copyProperties(listRole.getData(), RoleVO.class);
            apiResult.success(list);
        } else {
            apiResult.setMsg(listRole.getMsg());
        }
        return apiResult;
    }

    @ApiOperation(value = "角色列表")
    @PostMapping(value = "/new")
    public ApiResult<String> saveRole(@RequestBody EditRoleParam EditRoleParam) {
        ApiResult apiResult = new ApiResult();
        Role role=new Role();
        BeanUtils.copyProperties(EditRoleParam, role);
        ReturnClass saveRole = roleService.saveRole(role);
        if (saveRole.isSuccess()) {
            apiResult.success(saveRole.getData());
        } else {
            apiResult.setMsg(saveRole.getMsg());
        }
        return apiResult;
    }

    @ApiOperation(value = "菜单树")
    @GetMapping(value = "/tree")
    public ApiResult getAuthTree(@RequestParam(value = "roleId")Long roleId,@RequestParam(value = "type")Short type) {
        ApiResult apiResult = new ApiResult();
        ReturnClass authTree = roleService.getAuthTree(roleId, type);
        if (authTree.isSuccess()) {
            apiResult.success(authTree.getData());
        } else {
            apiResult.setMsg(authTree.getMsg());
        }
        return apiResult;
    }

    @ApiOperation(value = "修改角色权限")
    @PostMapping(value = "/auth/edit")
    public ApiResult editRoleAuth(@RequestBody @Validated EditRoleAuthParam editRoleAuthParam) {
        ApiResult apiResult = new ApiResult();
        ReturnClass editRoleAuth = roleService.editAuthRole(editRoleAuthParam.getRoleId(),editRoleAuthParam.getRoleAuthList());
        if (editRoleAuth.isSuccess()) {
            apiResult.success();
        } else {
            apiResult.setMsg(editRoleAuth.getMsg());
        }
        return apiResult;
    }

}
