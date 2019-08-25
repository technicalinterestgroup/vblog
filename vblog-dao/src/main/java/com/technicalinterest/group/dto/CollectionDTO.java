package com.technicalinterest.group.dto;

import lombok.Data;

import java.util.Date;

/**
 * @package: com.technicalinterest.group.dto
 * @className: CollectionDTO
 * @description: 收藏列表返回
 * @author: Shuyu.Wang
 * @date: 2019-08-21 13:26
 * @since: 0.1
 **/
@Data
public class CollectionDTO {

	/**
	 * 文章作者
	 */
	private String userName;

	/**
	 * 收藏文章id
	 */
	private Long articleId;
	/**
	 * 收藏文章名称
	 */
	private String title;
	/**
	 * 收藏时间
	 */
	private Date createTime;
}
