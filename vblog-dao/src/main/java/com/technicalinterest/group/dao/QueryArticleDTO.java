package com.technicalinterest.group.dao;

import lombok.Data;

/**
 * @package: com.technicalinterest.group.dao
 * @className: QueryArticleDTO
 * @description:
 * @author: Shuyu.Wang
 * @date: 2019-08-05 13:14
 * @since: 0.1
 **/
@Data
public class QueryArticleDTO {
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 摘要
	 */
	private String submit;
	/**
	 * 是否置顶
	 */
	private Short isTop;
	/**
	 * 分类
	 */
	private Long categoryCN;
	/**
	 * 标签id
	 */
	private Long  tagCN;

	/**
	 * 文章作者
	 */
	private String userName;

	/**
	 * 文章状态 0：草稿，1：发布
	 */
	private Short state=0;
}
