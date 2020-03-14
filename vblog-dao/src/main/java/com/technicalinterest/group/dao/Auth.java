package com.technicalinterest.group.dao;

import lombok.Data;

/**
 * @package: com.technicalinterest.group.dao
 * @className: Auth
 * @description: 权限控制表
 * @author: Shuyu.Wang
 * @date: 2019-08-30 21:39
 * @since: 0.1
 **/
@Data
public class Auth extends BaseDao {
	/**
	* 权限名称
	*/
	private String name;
	/**
	 * 权限类型 1：菜单，2：接口
	 */
	private String type;
	/**
	 * pic地址  或 url 地址
	 */
	private String url;
	/**
	 * 描述
	 */
	private String icon;
	/**
	 * 上级权限id
	 */
	private Long parentId;
}
