package com.technicalinterest.group.api.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @package: com.technicalinterest.group.dto
 * @className: EditTagParam
 * @description: EditTagParam
 * @author: Shuyu.Wang
 * @date: 2019-08-15 13:02
 * @since: 0.1
 **/
@Data
@ApiModel(description = "编辑博客标签参数")
public class EditTagParam {
	@ApiModelProperty(value = "标签id")
	@NotNull(message = "id不能为空")
	private Long id;
	@ApiModelProperty(value = "标签名称")
	@NotBlank(message = "标签名称不能为空")
	private String name;
}
