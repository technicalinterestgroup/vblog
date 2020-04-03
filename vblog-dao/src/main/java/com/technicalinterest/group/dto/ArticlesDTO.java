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
	 * 是否推荐
	 */
	private Short isRecommend;
	/**
	 * 分类id
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
	 * 标签
	 */
	private String  tagCN;

	/**
	 * 文章作者
	 */
	private String userName;

	/**
	 * 发布时间
	 */
	private Date createTime;

	/**
	 * 文章状态阅量
	 */
	private Long readCount;
	/**
	 * 归档时间
	 */
	private String time;

	/**
	 * 归档条数
	 */
	private Integer sum;
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
	/**
	 * 状态
	 */
	private Short isDel;

	private String description;


}
