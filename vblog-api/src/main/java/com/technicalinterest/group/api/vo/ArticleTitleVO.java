package com.technicalinterest.group.api.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @package: com.technicalinterest.group.dao
 * @className: ArticleContentDTO
 * @description: 文章名称列表
 * @author: Shuyu.Wang
 * @date: 2019-08-05 13:10
 * @since: 0.1
 **/
@Data
@ApiModel(description = "文章名列表")
public class ArticleTitleVO {
	@ApiModelProperty(value = "文章id")
	private Long id;
	@ApiModelProperty(value = "标题")
	private String title;

	@ApiModelProperty(value = "文章作者")
	private String userName;

	@ApiModelProperty(value = "发布时间")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createTime;

	@ApiModelProperty(value = "文章阅读量")
	private Long readCount;

	@ApiModelProperty(value = "评论数")
	private Integer commentCount;

	@ApiModelProperty(value = "点赞数")
	private Integer likeCount;


}
