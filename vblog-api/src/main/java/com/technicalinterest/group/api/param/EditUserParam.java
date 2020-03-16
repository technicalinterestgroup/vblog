package com.technicalinterest.group.api.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
@ApiModel(description = "更新用户数据参数")
public class EditUserParam {
	/**
	 * 头像
	 */
	@ApiModelProperty(value = "头像")
	private String photo;


	@ApiModelProperty(value = "昵称")
	private String nickName;

	@ApiModelProperty(value = "个人简介")
	private String userSummary;

}
