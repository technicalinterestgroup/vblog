package com.technicalinterest.group.dao;

import lombok.Data;

/**
 * @package: com.technicalinterest.group.dao
 * @className: Content
 * @description: 文章内容
 * @author: Shuyu.Wang
 * @date: 2019-07-28 17:10
 * @since: 0.1
 **/
@Data
public class Content extends Base{

	private Long articleId;

	/** 
	* 文章内容
	*/ 
	private String content;
}
