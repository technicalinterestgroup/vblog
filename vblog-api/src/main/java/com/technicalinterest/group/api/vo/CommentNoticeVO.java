package com.technicalinterest.group.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @package: com.technicalinterest.group.dto
 * @className: CommentNoticeDTO
 * @description: 消息通知VO
 * @author: Shuyu.Wang
 * @date: 2019-08-20 12:38
 * @since: 0.1
 **/
@Data
@ApiModel(description = "评论通知")
public class CommentNoticeVO {

	@ApiModelProperty(value = "评论id")
	private Long d;

	@ApiModelProperty(value = "用户名")
	private String userName;

	@ApiModelProperty(value = "博客id")
	private Long articleId;

	@ApiModelProperty(value = "博客标题")
	private String title;

	@ApiModelProperty(value = "评论时间")
	private Date createTime;
}
