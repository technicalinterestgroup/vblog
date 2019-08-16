package com.technicalinterest.group.service.dto;

import com.technicalinterest.group.dao.BaseDao;
import lombok.Data;

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
public class VSystemDTO{
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 博客名
	 */
	private String vTitle;
	/**
	 * icon url
	 */
	private String vIcon;
	/**
	 * 博客开始时间
	 */
	private Date vStart;
	/**
	 *seo 关键词
	 */
	private String seoKeyWords;
	/**
	 * footer info
	 */
	private String vFooterInfo;
	/**
	 * 博客介绍
	 */
	private String vIntroduct;
	/**
	 * 模块配置
	 */
	private String module;
	/**
	 * 通知开关
	 */
	private String noticeSwitch;
	/**
	 * 评论开关
	 */
	private String commentSwitch;
}
