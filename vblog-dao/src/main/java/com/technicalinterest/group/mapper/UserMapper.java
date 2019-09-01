package com.technicalinterest.group.mapper;

import com.technicalinterest.group.dao.User;
import com.technicalinterest.group.dto.BlogUserDTO;
import com.technicalinterest.group.dto.UserRoleDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

	Integer insertSelective(@Param("pojo") User pojo);

	Integer update(@Param("pojo") User pojo);

	/**
	 * @param user
	 * @return User
	 * @Description:
	 * @author: shuyu.wang
	 * @date: 2019-07-14 18:41
	 */
	User getUserByUser(@Param("user") User user);

	/**
	 * @Description: 查询用户博客信息
	 * @author: shuyu.wang
	 * @date: 2019-08-19 12:28
	 * @param userName
	 * @return null
	 */
	List<BlogUserDTO> queryUserBlog(@Param("userName") String userName);

	/**
	 * @Description: 获取用户和角色信息
	 * @author: shuyu.wang
	 * @date: 2019-08-31 21:30
	 * @param user
	 * @return null
	 */
	UserRoleDTO queryUserRoleDTO(@Param("user") User user);

	/**
	 * @Description: 查询所有博客用户
	 * @author: shuyu.wang
	 * @date: 2019-09-01 15:16
	 * @param user
	 * @return null
	 */
	List<UserRoleDTO> queryAllUser(@Param("user") User user);
}
