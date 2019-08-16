package com.technicalinterest.group.service.dto;

import lombok.Data;

/**
 * @package: com.technicalinterest.group.service.dto
 * @className: EditCategoryDTO
 * @description: 文章分类
 * @author: Shuyu.Wang
 * @date: 2019-08-15 13:01
 * @since: 0.1
 **/
@Data
public class EditCategoryDTO {
	private Long id;

	private String name;

	private String userName;
}
