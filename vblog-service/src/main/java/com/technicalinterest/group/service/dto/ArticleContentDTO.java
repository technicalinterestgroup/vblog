package com.technicalinterest.group.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
	private Short isTop;
	/**
	 * 分类id
	 */
	private Long categoryId;

	/**
	 * 分类
	 */
	private String categoryCN;

	/**
	 * 是否审核
	 */
	private Short isReview;
	/**
	 * 标签id
	 */
	private Long  tagId;

	/**
	 * 标签
	 */
	private String  tagCN;

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
	/** 
	* 文章状态阅量
	*/ 
	private Long readCount;

	/**
	 * 是否收藏 1是
	 */
	private Integer vCollection;
	/**
	 * 是否点赞 1是
	 */
	private Integer vLike;

	/**
	 * 点赞数量
	 */
	private Integer likeCount;

	/**
	 * 访问者ip
	 */
	private String ip;

	private Date updateTime;

	private Date createTime;

}
