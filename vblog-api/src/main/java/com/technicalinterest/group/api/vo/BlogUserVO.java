package com.technicalinterest.group.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @package: com.technicalinterest.group.dto
 * @className: UserDTO
 * @description: 博客用户文展示
 * @author: Shuyu.Wang
 * @date: 2019-08-19 12:25
 * @since: 0.1
 **/
@Data
public class BlogUserVO {

	@ApiModelProperty(value = "用户名")
	private String userName;

	@ApiModelProperty(value = "昵称")
	private String nickName;

	@ApiModelProperty(value = "博客数量")
	private Integer blogNum;

	@ApiModelProperty(value = "个人简介")
	private String userSummary;

	@ApiModelProperty(value = "头像url")
	private String photo;

}
