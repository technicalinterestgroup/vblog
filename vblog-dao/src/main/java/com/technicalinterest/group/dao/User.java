package com.technicalinterest.group.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @package: com.shuyu.blog.dao
 * @className: User
 * @description: 用户信息
 * @author: Shuyu.Wang
 * @date: 2019-07-14 15:44
 * @since: 0.1
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends Base {
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 密码
	 */
	private String passWord;
	/**
	 * 头像
	 */
	private String email;
	/**
	 * 头像
	 */
	private String photo;
	/**
	 * 积分
	 */
	private Integer integral;
	/**
	 * 状态0：待激活，1：已激活ßßß
	 */
	private Short state;

}
