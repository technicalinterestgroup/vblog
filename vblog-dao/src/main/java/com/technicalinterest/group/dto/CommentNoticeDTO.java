package com.technicalinterest.group.dto;

import lombok.Data;

import java.util.Date;

/**
 * @package: com.technicalinterest.group.dto
 * @className: CommentNoticeDTO
 * @description: 消息通知DTO
 * @author: Shuyu.Wang
 * @date: 2019-08-20 12:38
 * @since: 0.1
 **/
@Data
public class CommentNoticeDTO {
	/**
	 * 评论id
	 */
	private Long id;
	/**
	 * 用户名
	 */
	private String userName;

	private String sourceUserName;
	/**
	 * id
	 */
	private Long sourceId;

	/**
	 * 博客标题
	 */
	private String title;
	/**
	 * 是否查看过
	 */
	private Short isView;


	private Date createTime;
}
