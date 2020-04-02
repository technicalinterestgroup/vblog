package com.technicalinterest.group.api.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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

	@ApiModelProperty(value = "作者")
	private String userName;

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

	@ApiModelProperty(value = "文章阅读量")
	private Long readCount;

	@ApiModelProperty(value = "文章详情")
	private String content;

	@ApiModelProperty(value = "文章详情")
	private String contentFormat;

	@ApiModelProperty(value = "是否收藏",allowableValues = "1:是,null:否")
	@JsonProperty("vCollection")
	private Integer vCollection;

	@ApiModelProperty(value = "是否点赞",allowableValues = "1:是,null:否")
	@JsonProperty("vLike")
	private Integer vLike;

	@ApiModelProperty(value = "点赞数量")
	private Integer likeCount;


	@ApiModelProperty(value = "发布时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

}
