package com.technicalinterest.group.dao;

import lombok.Data;

/**
 * @package: com.technicalinterest.group.dao
 * @className: Article
 * @description: 文章表
 * @author: Shuyu.Wang
 * @date: 2019-07-28 17:01
 * @since: 0.1
 **/
@Data
public class Article extends Base{
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
	 * 是否审核
	 */
	private Short isReview;
	/**
	 * 标签id
	 */
	private Long  tag;
	
	/** 
	* 文章作者
	*/ 
	private String userName;


	
	
}
