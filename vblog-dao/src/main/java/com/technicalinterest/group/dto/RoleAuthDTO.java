package com.technicalinterest.group.dto;

import lombok.Data;

import java.util.List;

/**
 * @package: com.technicalinterest.group.dto
 * @className: RoleAuthDTO
 * @description: 角色对应的权限
 * @author: Shuyu.Wang
 * @date: 2019-08-31 20:24
 * @since: 0.1
 **/
@Data
public class RoleAuthDTO {

	private Long roleId;

	private String roleName;

	private Long authId;

	private String authName;

	private String url;

	private String authType;

	private String icon;

	private List<RoleAuthDTO> children;

}
