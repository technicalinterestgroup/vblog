package com.technicalinterest.group.api.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @package: com.technicalinterest.group.dao
 * @className: Comment
 * @description: 博客评论
 * @author: Shuyu.Wang
 * @date: 2019-08-18 17:37
 * @since: 0.1
 **/
@Data
@ApiModel(description = "新增博客评论参数")
public class NewCommentParam {

	@ApiModelProperty(value = "评论内容")
	@NotBlank(message = "commentContent不能为空")
	private String commentContent;

	@ApiModelProperty(value = "文章id")
	@NotNull(message = "文章id不能为空")
	private Long articleId;

	@ApiModelProperty(value = "上级评论id")
	private Long parentId;

}
