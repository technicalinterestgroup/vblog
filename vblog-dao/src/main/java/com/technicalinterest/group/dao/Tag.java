package com.technicalinterest.group.dao;

import lombok.Data;

/**
 * @package: com.technicalinterest.group.dao
 * @className: Tag
 * @description: 文章标签
 * @author: Shuyu.Wang
 * @date: 2019-08-11 19:18
 * @since: 0.1
 **/
@Data
public class Tag extends BaseDao {

	private String name;

	private String userName;
}
