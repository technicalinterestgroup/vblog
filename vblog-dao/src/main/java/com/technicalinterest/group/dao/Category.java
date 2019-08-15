package com.technicalinterest.group.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @package: com.technicalinterest.group.dao
 * @className: Category
 * @description: 文章分类
 * @author: Shuyu.Wang
 * @date: 2019-08-11 19:07
 * @since: 0.1
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseDao {

	private String name;

	private String userName;
}
