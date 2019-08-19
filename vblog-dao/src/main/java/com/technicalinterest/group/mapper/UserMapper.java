package com.technicalinterest.group.mapper;

import com.technicalinterest.group.dao.User;
import com.technicalinterest.group.dto.BlogUserDTO;
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
    List<BlogUserDTO> queryUserBlog(@Param("userName")String userName);
}
