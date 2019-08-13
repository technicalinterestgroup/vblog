package com.technicalinterest.group.dto;

import lombok.Data;

import java.util.Date;

/**
 * @package: com.technicalinterest.group.dao
 * @className: ArticleContentDTO
 * @description: 文章DTO
 * @author: Shuyu.Wang
 * @date: 2019-08-05 13:10
 * @since: 0.1
 **/
@Data
public class ArticlesDTO {
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
	private Short isTop;
	/**
	 * 分类id
	 */
	private Long categoryId;

	/**
	 * 分类
	 */
	private Long categoryCN;

	/**
	 * 标签id
	 */
	private Long  tagId;

	/**
	 * 标签id
	 */
	private Long  tagCN;

	/**
	 * 文章作者
	 */
	private String userName;

	/**
	 * 发布时间
	 */
	private Date updateTime;

	/**
	 * 文章状态阅量
	 */
	private Long readCount;

}
