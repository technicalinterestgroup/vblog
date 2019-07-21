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
public class NewUserDTO {
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
}
