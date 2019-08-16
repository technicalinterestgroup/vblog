package com.technicalinterest.group.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @package: com.technicalinterest.group.dao
 * @className: Tag
 * @description: 文章标签
 * @author: Shuyu.Wang
 * @date: 2019-08-11 19:18
 * @since: 0.1
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tag extends BaseDao {

	private String name;

	private String userName;
}
