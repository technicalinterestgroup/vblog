package com.technicalinterest.group.dao;

import lombok.Data;

/**
 * @package: com.technicalinterest.group.dao
 * @className: Category
 * @description: 文章分类
 * @author: Shuyu.Wang
 * @date: 2019-08-11 19:07
 * @since: 0.1
 **/
@Data
public class Category extends BaseDao {

	private String name;

	private String userName;
}
