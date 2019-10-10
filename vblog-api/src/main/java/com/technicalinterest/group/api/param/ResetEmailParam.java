package com.technicalinterest.group.api.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * @package: com.technicalinterest.group.api.param
 * @className: ResetEmailParam
 * @description: 重置邮件参数
 * @author: Shuyu.Wang
 * @date: 2019-10-10 16:21
 * @since: 0.1
 **/
@Data
@ApiModel(description = "发送重置邮件参数")
public class ResetEmailParam {
	@ApiModelProperty(value = "邮箱",required = true)
	@NotBlank(message = "邮箱不能为空")
	@Pattern(regexp ="^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$",message = "邮箱格式不正确!")
	private String email;
	@ApiModelProperty(value = "验证码",required = true)
	@NotBlank(message = "验证码不能为空")
	private String img;
	@ApiModelProperty(value = "验证码token",required = true)
	@NotBlank(message = "验证码token不能为空")
	private String token;
}
