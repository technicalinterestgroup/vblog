package com.technicalinterest.group.service.dto;

import lombok.Data;


/**
 * @package: com.technicalinterest.group.service.dto
 * @className: NewUserDTO
 * @description: 注册新用户
 * @author: Shuyu.Wang
 * @date: 2019-07-21 21:14
 * @since: 0.1
 **/
@Data
public class EditUserDTO {
	private Long id;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 密码
	 */
	private String passWord;
	/**
	 * 邮箱
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
	 * 状态0：待激活，1：已激活
	 */
	private Short state;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 个人简介
	 */
	private String userSummary;


}
