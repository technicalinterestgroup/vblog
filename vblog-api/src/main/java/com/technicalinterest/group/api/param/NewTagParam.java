package com.technicalinterest.group.api.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;


/**
 * @package: com.technicalinterest.group.dto
 * @className: NewTagParam
 * @description: tag
 * @author: Shuyu.Wang
 * @date: 2019-08-15 13:02
 * @since: 0.1
 **/
@Data
@ApiModel(description = "新增博客标签参数")
public class NewTagParam {

	@ApiModelProperty(value = "标签名称")
	@NotBlank(message = "标签名称不能为空")
	private String name;
}
