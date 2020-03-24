package com.technicalinterest.group.api.param;

import lombok.Data;

/**
 * @package: com.technicalinterest.group.api.param
 * @className: AdminEditUserParam
 * @description:
 * @author: Shuyu.Wang
 * @date: 2020-03-24 22:53
 * @since: 0.1
 **/
@Data
public class AdminEditUserParam {

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 */
	private Short isDel;
}
