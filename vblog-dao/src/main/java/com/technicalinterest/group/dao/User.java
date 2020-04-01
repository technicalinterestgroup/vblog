package com.technicalinterest.group.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class User extends BaseDao {
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 密码
	 */
	private String passWord;
	/**
	 * 邮件
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
	 * 博客等级
	 */
	private Short grade;
	/**
	 * 状态0：待激活，1：已激活
	 */
	private Short state;
	/**
	 * 个人简介
	 */
	private String userSummary;
	/**
	 * 文件上传次数限制
	 */
	private Integer uploadNum;

}
