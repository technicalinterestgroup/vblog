package com.technicalinterest.group.dto;

import lombok.Data;

/**
 * @package: com.technicalinterest.group.dto
 * @className: UserDTO
 * @description: 用户文章数统计
 * @author: Shuyu.Wang
 * @date: 2019-08-19 12:25
 * @since: 0.1
 **/
@Data
public class BlogUserDTO {

	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 博客数量
	 */
	private Integer blogNum;
	/**
	 * 个人简介
	 */
	private String userSummary;
	/**
	 * 头像
	 */
	private String photo;

}
