package com.technicalinterest.group.api.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @package: com.technicalinterest.group.dto
 * @className: CategoryDTO
 * @description: 分类查询返回
 * @author: Shuyu.Wang
 * @date: 2019-08-15 13:02
 * @since: 0.1
 **/
@Data
@ApiModel(description = "编辑博客分类参数")
public class EditCategoryParam {
	@ApiModelProperty(value = "分类id")
	@NotNull(message = "id不能为空")
	private Long id;
	@ApiModelProperty(value = "分类名称")
	@NotBlank(message = "分类名称不能为空")
	private String name;
}
