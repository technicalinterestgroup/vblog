package com.technicalinterest.group.api.param;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @ClassName: EditAuthParam
 * @Author: shuyu.wang
 * @Description:
 * @Date: 2020/3/27 16:10
 * @Version: 1.0
 */
@Data
public class EditAuthParam {
    private Long id;
    /**
     * 权限名称
     */
    @NotBlank(message = "")
    private String name;
    /**
     * 权限类型 1：菜单，2：接口
     */
    @NotNull(message = "")
    private Short type;
    /**
     * pic地址  或 url 地址
     */
    @NotBlank(message = "")
    private String url;
    /**
     * 图标
     */
    private String icon;
    /**
     * 上级权限id
     */
    private Long parentId;
}
