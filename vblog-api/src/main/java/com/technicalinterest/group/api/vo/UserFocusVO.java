package com.technicalinterest.group.api.vo;

import lombok.Data;

/**
 * @package: com.technicalinterest.group.dto
 * @className: UserFocusDTO
 * @description: 关注用户列表
 * @author: Shuyu.Wang
 * @date: 2020-04-12 17:26
 * @since: 0.1
 **/
@Data
public class UserFocusVO {

	private String userName;

	private String avatar;
	/**
	 * 1：关注 2：互粉
	 */
	private Short state;

}
