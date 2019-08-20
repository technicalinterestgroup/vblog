package com.technicalinterest.group.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.technicalinterest.group.dao.BaseDao;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
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
public class VSystemVO{

	/**
	 * 博客开始时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@JsonProperty("vStart")
	private Date vStart;
	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "用户名")
	private String userName;
	/**
	 * 博客名
	 */
	@ApiModelProperty(value = "博客名")
	@JsonProperty("vTitle")
	private String vTitle;
	/**
	 * icon url
	 */
	@ApiModelProperty(value = "icon")
	@JsonProperty("vIcon")
	private String vIcon;

	/**
	 *seo 关键词
	 */
	@ApiModelProperty(value = "seo 关键词")
	private String seoKeyWords;
	/**
	 * footer info
	 */
	@ApiModelProperty(value = "footer info")
	@JsonProperty("vFooterInfo")
	private String vFooterInfo;
	/**
	 * 博客介绍
	 */
	@ApiModelProperty(value = "博客介绍")
	@JsonProperty("vIntroduct")
	private String vIntroduct;
	/**
	 * 模块配置
	 */
	@ApiModelProperty(value = "模块配置")
	private String module;
	/**
	 * 通知开关
	 */
	@ApiModelProperty(value = "通知开关")
	private String noticeSwitch;
	/**
	 * 评论开关
	 */
	@ApiModelProperty(value = "评论开关")
	private String commentSwitch;

	/**
	 * 系统主题
	 */
	@ApiModelProperty(value = "系统主题")
	private String theme;
}
