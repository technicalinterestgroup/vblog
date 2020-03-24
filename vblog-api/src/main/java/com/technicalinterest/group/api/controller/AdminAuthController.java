package com.technicalinterest.group.api.controller;

import com.technicalinterest.group.api.param.AdminEditUserParam;
import com.technicalinterest.group.api.param.QueryUserRoleParam;
import com.technicalinterest.group.api.vo.ApiResult;
import com.technicalinterest.group.api.vo.UserInfoVO;
import com.technicalinterest.group.dto.UserRoleDTO;
import com.technicalinterest.group.service.AdminService;
import com.technicalinterest.group.service.UserService;
import com.technicalinterest.group.service.dto.EditUserDTO;
import com.technicalinterest.group.service.dto.PageBean;
import com.technicalinterest.group.service.dto.ReturnClass;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: AdminAuthController
 * @Author: shuyu.wang
 * @Description: 菜单接口权限
 * @Date: 2020/3/24 12:00
 * @Version: 1.0
 */
@Api(tags = "权限管理")
@RestController
@RequestMapping("admin/auth")
public class AdminAuthController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户列表", notes = "用户列表")
    @GetMapping(value = "/user/list")
    public ApiResult<PageBean<UserInfoVO>> listUser(QueryUserRoleParam queryUserRoleParam) {
        ApiResult apiResult = new ApiResult();
        UserRoleDTO user=new UserRoleDTO();
        BeanUtils.copyProperties(queryUserRoleParam,user);
        ReturnClass listUser = adminService.userAll(user);
        if (listUser.isSuccess()) {
            PageBean<UserRoleDTO> pageBean = (PageBean<UserRoleDTO>) listUser.getData();
            List<UserInfoVO> list = new ArrayList<>();
            for (UserRoleDTO entity : pageBean.getPageData()) {
                UserInfoVO userInfoVO = new UserInfoVO();
                BeanUtils.copyProperties(entity, userInfoVO);
                list.add(userInfoVO);
            }
            PageBean<UserInfoVO> pageInfo = new PageBean<UserInfoVO>();
            BeanUtils.copyProperties(listUser.getData(), pageInfo);
            pageInfo.setPageData(list);
            apiResult.success(pageInfo);

        } else {
            apiResult.setMsg(listUser.getMsg());
        }
        return apiResult;
    }

    @ApiOperation(value = "修改用户状态")
    @PostMapping(value = "/user/edit")
    public ApiResult<String> userEdit(AdminEditUserParam adminEditUserParam) {
        ApiResult apiResult = new ApiResult();
        EditUserDTO editUserDTO=new EditUserDTO();
        BeanUtils.copyProperties(adminEditUserParam,editUserDTO);
        ReturnClass addUser = adminService.updateUser(editUserDTO);
        if (addUser.isSuccess()) {
            apiResult.success();
        } else {
            apiResult.setMsg(addUser.getMsg());
        }
        return apiResult;
    }
}
