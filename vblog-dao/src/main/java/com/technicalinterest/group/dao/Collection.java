package com.technicalinterest.group.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @package: com.technicalinterest.group.dao
 * @className: Collection
 * @description: 收藏表实体类
 * @author: Shuyu.Wang
 * @date: 2019-08-21 13:00
 * @since: 0.1
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Collection extends BaseDao {

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 点赞类型1：博客，2 评论 3 通告
	 */
	private Short type;
	/**
	 * 文章/评论/通告 id
	 */
	private Long sourceId;

}
