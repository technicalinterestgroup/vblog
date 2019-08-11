package com.technicalinterest.group.dto;

import com.technicalinterest.group.dao.PageBase;
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
public class QueryArticleDTO extends PageBase {
	/**
	 * 搜索条件
	 */
	private String condition;
	/**
	 * 是否置顶
	 */
	private Short isTop;
	/**
	 * 分类
	 */
	private Long categoryId;
	/**
	 * 标签id
	 */
	private Long  tagId;

	/**
	 * 文章作者
	 */
	private String userName;

	/**
	 * 文章状态 0：草稿，1：发布
	 */
	private Short state;
}
