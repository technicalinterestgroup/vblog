package com.technicalinterest.group.api.param;

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
public class QueryArticleParam extends PageBase {
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
	private String categoryCN;
	/**
	 * 标签
	 */
	private String  tagCN;

	/**
	 * 文章状态 0：草稿，1：发布
	 */
	private Short state;
}
