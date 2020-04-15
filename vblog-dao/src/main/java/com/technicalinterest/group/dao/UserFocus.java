package com.technicalinterest.group.dao;

import lombok.Data;

/**
 * @package: com.technicalinterest.group.dao
 * @className: UserFocus
 * @description: 用户关注关系表
 * @author: Shuyu.Wang
 * @date: 2020-04-12 17:07
 * @since: 0.1
 **/
@Data
public class UserFocus extends BaseDao{
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 被关注用户名
	 */
	private String focusUserName;

	/**
	 * 关注关系存续状态，true-存在关注关系，false-取消关注
	 */
	private Boolean state;
}
