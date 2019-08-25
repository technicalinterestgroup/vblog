package com.technicalinterest.group.service.dto;

import com.technicalinterest.group.dao.BaseDao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @package: com.technicalinterest.group.dao
 * @className: Like
 * @description: 点赞表
 * @author: Shuyu.Wang
 * @date: 2019-08-21 13:00
 * @since: 0.1
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikeDTO {

	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * ip地址
	 */
	private String ipAddress;
	/**
	 * 点赞类型1：博客，2 评论
	 */
	private Short type;
	/**
	 * 文章/评论id
	 */
	private Long sourceId;

}
