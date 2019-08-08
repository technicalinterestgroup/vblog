package com.technicalinterest.group.api.vo;

import lombok.Data;

/**
 * @package: com.technicalinterest.group.dao
 * @className: ArticleContentDTO
 * @description: 文章DTO
 * @author: Shuyu.Wang
 * @date: 2019-08-05 13:10
 * @since: 0.1
 **/
@Data
public class ArticlesVO {
	private Long id;

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
	private Short isTopCN;
	/**
	 * 分类名称
	 */
	private Long categoryId;

	/**
	 * 分类
	 */
	private String categoryCN;

	/**
	 * 标签id
	 */
	private Long  tagId;

	/**
	 * 标签名称
	 */
	private String  tagCN;

	/**
	 * 文章作者
	 */
	private String userName;

}
