package com.technicalinterest.group.service.dto;

import lombok.Data;

import java.util.Date;

/**
 * @package: com.technicalinterest.group.service.dto
 * @className: ArticleContentDTO
 * @description: 新增文章DTO
 * @author: Shuyu.Wang
 * @date: 2019-08-04 15:08
 * @since: 0.1
 **/
@Data
public class ArticleContentDTO {

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
	private Short isTop=0;
	/**
	 * 分类id
	 */
	private Long categoryId;
	/**
	 * 是否审核
	 */
	private Short isReview=1;
	/**
	 * 标签id
	 */
	private Long  tagId;

	/**
	 * 文章作者
	 */
	private String userName;

	/**
	 * 文章内容
	 */
	private String content;
	/**
	 * 文章状态 0：草稿，1：发布
	 */
	private Short state;
}
