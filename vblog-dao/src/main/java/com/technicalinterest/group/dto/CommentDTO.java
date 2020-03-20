package com.technicalinterest.group.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @package: com.technicalinterest.group.dao
 * @className: CommentDTO
 * @description: 评论查询返回实体
 * @author: Shuyu.Wang
 * @date: 2019-08-18 19:32
 * @since: 0.1
 **/
@Data
public class CommentDTO {

	private Long id;
	/**
	 * 评论内容
	 */
	private String commentContent;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 点赞数量
	 */
	private Long voltNumber;
	/**
	 * 文章id
	 */
	private Long articleId;
	/**
	 * 上级评论id
	 */
	private Long parentId;
	/**
	 * 是否是作者回复
	 */
	private Short isAuther;

	private Date createTime;

	private String avatar;

}
