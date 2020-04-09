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
public class WebNoticeDTO {
	private Long id;

	/**
	 * 标题
	 */
	private String title;


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
	 * 文章内容html格式
	 */
	private String contentFormat;

	/**
	 * 文章内容md格式
	 */
	private String content;

	/**
	 * 发布时间
	 */
	private Date updateTime;

	/**
	 * 文章状态阅量
	 */
	private Integer readCount;

	/**
	 * 评论数
	 */
	private Integer commentCount;
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


	private String description;


}
