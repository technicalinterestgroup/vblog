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
	 * 密码
	 */
	private String passWord;
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

	private Short isDel;
}
