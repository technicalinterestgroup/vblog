package com.technicalinterest.group.api.param;

import com.technicalinterest.group.dao.BaseDao;
import com.technicalinterest.group.dao.RoleAuth;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @package: com.technicalinterest.group.dao
 * @className: RoleAuth
 * @description: 角色权限关联
 * @author: Shuyu.Wang
 * @date: 2019-08-30 21:43
 * @since: 0.1
 **/
@Data
public class EditRoleAuthParam{
	/**
	* 角色ID
	*/
	@NotNull
	private Long roleId;
	/**
	 * 权限
	 */
	@NotEmpty
	private List<RoleAuth> roleAuthList;


}
