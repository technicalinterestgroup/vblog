package com.technicalinterest.group.api.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
public class EditVSystemParam {
	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "用户名")
	@NotBlank(message = "userName不能为空")
	private String userName;
	/**
	 * 博客名
	 */
	@ApiModelProperty(value = "博客名")
	@NotBlank(message = "vTitle不能为空")
	private String vTitle;
	/**
	 * icon url
	 */
	@ApiModelProperty(value = "icon")
	@NotBlank(message = "vIcon不能为空")
	private String vIcon;

	/**
	 *seo 关键词
	 */
	@ApiModelProperty(value = "seo 关键词")
	@NotBlank(message = " seoKeyWords不能为空")
	private String seoKeyWords;
	/**
	 * footer info
	 */
	@ApiModelProperty(value = "footer info")
	@NotBlank(message = "vFooterInfo不能为空")
	private String vFooterInfo;
	/**
	 * 博客介绍
	 */
	@ApiModelProperty(value = "博客介绍")
	@NotBlank(message = "vIntroduct不能为空")
	private String vIntroduct;
	/**
	 * 模块配置
	 */
	@ApiModelProperty(value = "模块配置")
	@NotBlank(message = "module不能为空")
	private String module;
	/**
	 * 通知开关
	 */
	@ApiModelProperty(value = "通知开关")
	@NotBlank(message = "noticeSwitch不能为空")
	private String noticeSwitch;
	/**
	 * 评论开关
	 */
	@ApiModelProperty(value = "评论开关")
	@NotBlank(message = "commentSwitch不能为空")
	private String commentSwitch;
}
