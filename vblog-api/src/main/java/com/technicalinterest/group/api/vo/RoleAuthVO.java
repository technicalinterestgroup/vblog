package com.technicalinterest.group.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @package: com.technicalinterest.group.dto
 * @className: RoleAuthDTO
 * @description: 角色对应的权限
 * @author: Shuyu.Wang
 * @date: 2019-08-31 20:24
 * @since: 0.1
 **/
@Data
@ApiModel(description = "权限菜单列表")
public class RoleAuthVO {

	@ApiModelProperty(value = "菜单名")
	private String authName;
	@ApiModelProperty(value = "url")
	private String url;
	@ApiModelProperty(value = "图标")
	private String icon;

}
