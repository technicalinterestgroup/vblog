package com.technicalinterest.group.dto;

import lombok.Data;

/**
 * @package: com.technicalinterest.group.dto
 * @className: CategoryDTO
 * @description: 分类查询返回
 * @author: Shuyu.Wang
 * @date: 2019-08-15 13:02
 * @since: 0.1
 **/
@Data
public class CategoryDTO {
	private Long id;

	private String name;
	/**
	 * 文章数
	 */
	private Integer articleNum;
}
