package com.technicalinterest.group.dto;

import lombok.Data;

/**
 * @package: com.technicalinterest.group.dto
 * @className: BlogUserInfoDTO
 * @description: 博客用户信息
 * @author: Shuyu.Wang
 * @date: 2019-08-30 21:32
 * @since: 0.1
 **/
@Data
public class BlogUserInfoDTO {

	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 邮件
	 */
	private String email;
	/**
	 * 头像
	 */
	private String photo;
	/**
	 * 博客等级
	 */
	private Short grade;
	/**
	 * 博客数量
	 */
	private Integer blogNum;

	/**
	 * 状态
	 */
	private Short state;
}
