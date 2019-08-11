package com.technicalinterest.group.api.vo;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @package: com.technicalinterest.group.dao
 * @className: ArticleContentDTO
 * @description: 文章DTO
 * @author: Shuyu.Wang
 * @date: 2019-08-05 13:10
 * @since: 0.1
 **/
@Data
@ApiModel(description = "文章详情")
public class ArticleContentVO {
	@ApiModelProperty(value = "文章id")
	private Long id;
	@ApiModelProperty(value = "标题")
	private String title;
	@ApiModelProperty(value = "摘要")
	private String submit;

	@ApiModelProperty(value = "是否置顶")
	private Short isTop;

	@ApiModelProperty(value = "分类id")
	private Long categoryId;

	@ApiModelProperty(value = "分类")
	private String categoryCN;

	@ApiModelProperty(value = "标签id")
	private Long tagId;

	@ApiModelProperty(value = "标签名称")
	private String tagCN;
	@ApiModelProperty(value = "文章详情")
	private String content;

}