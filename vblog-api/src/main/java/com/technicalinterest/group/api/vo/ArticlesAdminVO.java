package com.technicalinterest.group.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ArticlesAdminVO {
	@ApiModelProperty(value = "文章id")
	private Long id;
	@ApiModelProperty(value = "标题")
	private String title;
	@ApiModelProperty(value = "摘要")
	private String submit;
	@ApiModelProperty(value = "分类")
	private String categoryCN;
	@ApiModelProperty(value = "标签名称")
	private String tagCN;
	@ApiModelProperty(value = "文章作者")
	private String userName;
	@ApiModelProperty(value = "发布时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	@ApiModelProperty(value = "状态",allowableValues = "1:被禁 0:可正常访问")
	private Short isDel;


}
