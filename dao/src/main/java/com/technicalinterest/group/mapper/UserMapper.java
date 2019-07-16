package com.technicalinterest.group.mapper;

import com.technicalinterest.group.dao.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    int insertSelective(@Param("pojo") User pojo);


    int update(@Param("pojo") User pojo);

    /**
     * @param user
     * @return User
     * @Description:
     * @author: shuyu.wang
     * @date: 2019-07-14 18:41
     */
    User userByUser(@Param("user") User user);
}
