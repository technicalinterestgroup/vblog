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
@ApiModel(description = "更新用户数据参数")
public class EditPassWordrParam {

	@ApiModelProperty(value = "旧密码")
	@NotBlank(message = "旧密码不能为空")
	private String oldPassWord;


	@ApiModelProperty(value = "新密码")
	@NotBlank(message = "新密码不能为空")
	private String newPassWord;

}
