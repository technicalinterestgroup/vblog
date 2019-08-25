package com.technicalinterest.group.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
	private Long id;

	@ApiModelProperty(value = "用户名")
	private String userName;

	@ApiModelProperty(value = "博客id")
	private Long articleId;

	@ApiModelProperty(value = "博客标题")
	private String title;

	@ApiModelProperty(value = "是否查看过",allowableValues = "0:未查看，1:已经查看")
	private Short isView;

	@ApiModelProperty(value = "评论时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
}
