package com.technicalinterest.group.api.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * @package: com.technicalinterest.group.service.dto
 * @className: NewUserDTO
 * @description: 注册新用户
 * @author: Shuyu.Wang
 * @date: 2019-07-21 21:14
 * @since: 0.1
 **/
@Data
@ApiModel(description = "注册用户参数")
public class NewUserParam {
	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "用户名",required = true,example = "5-13位字母数字下划线组合")
	@NotBlank(message = "用户名不能为空")
	@Pattern(regexp ="^[a-zA-Z]\\w{4,12}$",message = "不符合用户名规范!")
	private String userName;
	/**
	 * 密码
	 */
	@ApiModelProperty(value = "密码",required = true,example = "6-16位字母数字组合")
	@NotBlank(message = "密码不能为空")
	@Pattern(regexp ="^[a-zA-Z0-9]{6,16}$",message = "密码不符合规范!")
	private String passWord;
	/**
	 * 邮箱
	 */
//	@ApiModelProperty(value = "邮箱",required = true)
	@NotBlank(message = "邮箱不能为空")
	@Pattern(regexp ="^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$",message = "邮箱格式不正确!")
	private String email;

	@ApiModelProperty(value = "验证码",required = true)
	@NotBlank(message = "验证码不能为空")
	private String img;

	@ApiModelProperty(value = "验证码token",required = true)
	@NotBlank(message = "不能为空")
	private String token;

}
