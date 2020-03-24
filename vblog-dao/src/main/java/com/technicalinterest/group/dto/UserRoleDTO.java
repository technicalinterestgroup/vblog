package com.technicalinterest.group.dto;

import com.technicalinterest.group.dao.PageBase;
import lombok.Data;

import java.util.Date;

/**
 * @package: com.technicalinterest.group.dto
 * @className: UserRoleDTO
 * @description: 用户角色关联
 * @author: Shuyu.Wang
 * @date: 2019-08-31 21:17
 * @since: 0.1
 **/
@Data
public class UserRoleDTO extends PageBase {

	private Long id;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 头像
	 */
	private String photo;
	/**
	 * 博客数量
	 */
	private Integer blogNum;
	/**
	 * 状态
	 */
	private Short state;

	private Short isDel;

	private Long roleId;
	/**
	 * 角色名称
	 */
	private String roleName;
	/**
	 * 角色类型
	 */
	private Short roleType;

	private Date createTime;
}
