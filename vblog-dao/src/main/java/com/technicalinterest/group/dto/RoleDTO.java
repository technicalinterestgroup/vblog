package com.technicalinterest.group.dto;

import com.technicalinterest.group.dao.BaseDao;
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
public class RoleDTO {

	private Long id;
	/** 
	* 角色名
	*/ 
	private String name;
	/** 
	* 类型 1：管理员 2：普通用户
	*/ 
	private String type;

	private Integer userNum;

	private Date createTime;
}
