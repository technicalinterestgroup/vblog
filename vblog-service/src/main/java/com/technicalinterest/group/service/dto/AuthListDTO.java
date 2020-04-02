package com.technicalinterest.group.service.dto;

import lombok.Data;

import java.util.List;

/**
 * @ClassName: AuthListVO
 * @Author: shuyu.wang
 * @Description:
 * @Date: 2020/3/27 12:30
 * @Version: 1.0
 */
@Data
public class AuthListDTO {
    private Long id;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 权限类型 1：菜单，2：接口
     */
    private Short type;
    /**
     * pic地址  或 url 地址
     */
    private String url;
    /**
     * 图标
     */
    private String icon;

    private Boolean isParent;

    private List<AuthListDTO> children;
}
