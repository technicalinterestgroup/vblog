package com.technicalinterest.group.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = "博客评论")
public class CommentVO {
	@ApiModelProperty(value = "记录id")
	private Long id;

	@ApiModelProperty(value = "评论内容")
	private String commentContent;

	@ApiModelProperty(value = "用户名")
	private String userName;

	@ApiModelProperty(value = "点赞数量")
	private Long voltNumber;

	@ApiModelProperty(value = "文章id")
	private Long articleId;

	@ApiModelProperty(value = "是否是作者回复")
	private Short isAuther;

	@ApiModelProperty(value = "评论时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**
	 * 子评论
	 */
	private List<CommentVO> childComment;
}
