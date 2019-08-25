package com.technicalinterest.group.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @package: com.technicalinterest.group.dao
 * @className: Comment
 * @description: 博客评论
 * @author: Shuyu.Wang
 * @date: 2019-08-18 17:37
 * @since: 0.1
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseDao {
	/**
	* 评论内容
	*/
	private String commentContent;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * ip地址
	 */
	private String ipAddress;
	/**
	 * 点赞数量
	 */
	private Long voltNumber;
	/**
	 * 文章id
	 */
	private Long articleId;
	/**
	 * 上级评论id
	 */
	private Long parentId;
	/**
	 * 是否是作者回复
	 */
	private Short isAuther;
	/**
	 * 是否被查看
	 */
	private Short isView;
}
