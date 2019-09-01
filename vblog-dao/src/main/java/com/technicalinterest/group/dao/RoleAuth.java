package com.technicalinterest.group.dao;

import lombok.Data;

/**
 * @package: com.technicalinterest.group.dao
 * @className: RoleAuth
 * @description: 角色权限关联
 * @author: Shuyu.Wang
 * @date: 2019-08-30 21:43
 * @since: 0.1
 **/
@Data
public class RoleAuth extends BaseDao{
	/**
	* 角色ID
	*/
	private Long roleId;
	/**
	 * 权限ID
	 */
	private Long authId;


}
