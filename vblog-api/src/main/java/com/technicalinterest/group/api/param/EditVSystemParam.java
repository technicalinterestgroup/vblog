package com.technicalinterest.group.api.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * @package: com.technicalinterest.group.dao
 * @className: VSystem
 * @description: 博客设置
 * @author: Shuyu.Wang
 * @date: 2019-08-14 12:57
 * @since: 0.1
 **/
@Data
@ApiModel(description = "博客设置参数")
public class EditVSystemParam {
	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "用户名")
	@NotBlank(message = "不能为空")
	private String userName;
	/**
	 * 博客名
	 */
	@ApiModelProperty(value = "博客名")
	@NotBlank(message = "不能为空")
	@JsonProperty("vTitle")
	private String vTitle;
	/**
	 * icon url
	 */
	@ApiModelProperty(value = "icon")
	@NotBlank(message = "不能为空")
	@JsonProperty("vIcon")
	private String vIcon;
	/**
	 *seo 关键词
	 */
	@ApiModelProperty(value = "seo 关键词")
	@NotBlank(message = " 不能为空")
	@JsonProperty("seoKeyWords")
	private String seoKeyWords;
	/**
	 * footer info
	 */
	@ApiModelProperty(value = "footer info")
	@NotBlank(message = "不能为空")
	@JsonProperty("vFooterInfo")
	private String vFooterInfo;
	/**
	 * 博客介绍
	 */
	@ApiModelProperty(value = "博客介绍")
	@NotBlank(message = "不能为空")
	@JsonProperty("vIntroduct")
	private String vIntroduct;
	/**
	 * 模块配置
	 */
	@ApiModelProperty(value = "模块配置")
	@NotBlank(message = "不能为空")
	private String module;
	/**
	 * 通知开关
	 */
	@ApiModelProperty(value = "通知开关")
	@NotBlank(message = "不能为空")
	private String noticeSwitch;
	/**
	 * 评论开关
	 */
	@ApiModelProperty(value = "评论开关")
	@NotBlank(message = "不能为空")
	private String commentSwitch;

	/**
	 * 最新文章
	 */
	@JsonProperty("vNew")
	private Boolean vNew;
	/**
	 * 推荐文章
	 */
	@JsonProperty("vRecommend")
	private Boolean vRecommend;
	/**
	 * 热门文章
	 */
	@JsonProperty("vHot")
	private Boolean vHot;
	/**
	 * 归档
	 */
	@JsonProperty("vArchive")
	private Boolean vArchive;
	/**
	 * 标签
	 */
	@JsonProperty("vTag")
	private Boolean vTag;
	/**
	 * 最新评论
	 */
	@JsonProperty("vComment")
	private Boolean vComment;
}
