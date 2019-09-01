package com.technicalinterest.group.dto;

import lombok.Data;

/**
 * @package: com.technicalinterest.group.dto
 * @className: UserRoleDTO
 * @description: 用户角色关联
 * @author: Shuyu.Wang
 * @date: 2019-08-31 21:17
 * @since: 0.1
 **/
@Data
public class UserRoleDTO {
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 头像
	 */
	private String photo;

	private Short state;

	private Short isDel;

	private Long roleId;

	private String roleName;

	private Short roleType;
}
