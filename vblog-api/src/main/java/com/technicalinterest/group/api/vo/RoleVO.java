package com.technicalinterest.group.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @package: com.technicalinterest.group.dao
 * @className: Role
 * @description: 角色
 * @author: Shuyu.Wang
 * @date: 2019-08-30 21:37
 * @since: 0.1
 **/
@Data
public class RoleVO {

	private Long id;
	/** 
	* 角色名
	*/ 
	private String name;
	/** 
	* 类型 1：管理员 2：普通用户
	*/ 
	private Integer type;

	private Integer userNum;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createTime;

}
