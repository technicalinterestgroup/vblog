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
	 * 收藏文章id
	 */
	private Long articleId;

}
