package com.technicalinterest.group.api.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = "分类查询返回")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryVO {
	@ApiModelProperty(value = "分类id")
	private Long id;
	@ApiModelProperty(value = "分类名称")
	private String name;
	/**
	 * 文章数
	 */
	@ApiModelProperty(value = "对应文章数")
	private Integer articleNum;
}
