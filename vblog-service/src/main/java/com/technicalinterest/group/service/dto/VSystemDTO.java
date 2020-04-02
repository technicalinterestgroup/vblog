package com.technicalinterest.group.service.dto;

import com.technicalinterest.group.dao.BaseDao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
	private Boolean noticeSwitch;
	/**
	 * 评论开关
	 */
	private Boolean commentSwitch;
	/**
	 * 系统主题
	 */
	private String theme;
	/**
	 * 最新文章
	 */
	private Boolean vNew;
	/**
	 * 推荐文章
	 */
	private Boolean vRecommend;
	/**
	 * 热门文章
	 */
	private Boolean vHot;
	/**
	 * 归档
	 */
	private Boolean vArchive;
	/**
	 * 标签
	 */
	private Boolean vTag;
	/**
	 * 最新评论
	 */
	private Boolean vComment;
}
