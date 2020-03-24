package com.technicalinterest.group.api.controller;

import com.technicalinterest.group.api.param.QueryUserRoleParam;
import com.technicalinterest.group.api.vo.ApiResult;
import com.technicalinterest.group.api.vo.UserInfoVO;
import com.technicalinterest.group.dto.UserRoleDTO;
import com.technicalinterest.group.service.AdminService;
import com.technicalinterest.group.service.dto.PageBean;
import com.technicalinterest.group.service.dto.ReturnClass;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
