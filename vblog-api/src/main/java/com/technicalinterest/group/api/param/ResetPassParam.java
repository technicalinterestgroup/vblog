package com.technicalinterest.group.api.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @package: com.technicalinterest.group.service.dto
 * @className: NewUserDTO
 * @description: 注册新用户
 * @author: Shuyu.Wang
 * @date: 2019-07-21 21:14
 * @since: 0.1
 **/
@Data
@ApiModel(description = "重置密码参数")
public class ResetPassParam {
	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "用户名")
	@NotBlank(message = "用户名不能为空")
	private String userName;
	/**
	 * 密码
	 */
	@ApiModelProperty(value = "密码")
	@NotBlank(message = "密码不能为空")
	private String passWord;

}
