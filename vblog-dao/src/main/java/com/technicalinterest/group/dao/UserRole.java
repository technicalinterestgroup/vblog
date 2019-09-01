package com.technicalinterest.group.dao;

import lombok.Data;

/**
 * @package: com.technicalinterest.group.dao
 * @className: UserRole
 * @description: 用户角色表
 * @author: Shuyu.Wang
 * @date: 2019-08-30 21:45
 * @since: 0.1
 **/
@Data
public class UserRole extends BaseDao{
	/**
	* 用户ID
	*/
	private Long userId;
	/**
	 * 角色id
	 */
	private Long roleId;
}
