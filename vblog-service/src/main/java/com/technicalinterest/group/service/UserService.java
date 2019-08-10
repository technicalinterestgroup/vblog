package com.technicalinterest.group.service;


import com.technicalinterest.group.service.dto.EditUserDTO;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.dto.UserDTO;

/**
 * @package: com.shuyu.blog.service
 * @className: UserService
 * @description:
 * @author: Shuyu.Wang
 * @date: 2019-07-14 18:36
 * @since: 0.1
 **/
public interface UserService {

    
    /**
     * 登录
     * @author: shuyu.wang
     * @date: 2019-07-14 18:48
     * @param userDTO
     * @return UserDTO
    */
    ReturnClass login(EditUserDTO userDTO);



    /**
     * 注册新用户
     * @author: shuyu.wang
     * @date: 2019-07-21 21:32
     * @param newUserDTO
     * @return null
    */
    ReturnClass addUser(EditUserDTO newUserDTO);


   /**
    * 更新用户信息
    * @author: shuyu.wang
    * @date: 2019-07-21 22:11
    * @param editUserDTO
    * @return null
   */
    ReturnClass updateUser(EditUserDTO editUserDTO);

    /**
     * @Description: 退出登录
     * @author: shuyu.wang
     * @date: 2019-07-21 22:22
     * @param token
     * @return null
    */
    ReturnClass logout(String token);

    /**
     * @Description: 账号激活
     * @author: shuyu.wang
     * @date: 2019/8/5 21:24
     * @param key
     * @return null
     */
    ReturnClass activationUser(String key);

    /**
     * @Description: 根据toke获取用户信息
     * @author: shuyu.wang
     * @date: 2019-07-28 19:43
     * @return null
    */
    ReturnClass getUserByToken();
    

    /**
     * @Description:根据用户名查询用户信息
     * @author: shuyu.wang
     * @date: 2019-08-08 13:08
     * @param userName
     * @return null
    */
    ReturnClass getUserByuserName(String userName);


    
    /**
     * @Description: 判断用户名是否是当前操作登录的用户
     * @author: shuyu.wang
     * @date: 2019-08-08 13:12
     * @param userName
     * @return null
    */
    ReturnClass userNameIsLoginUser(String userName);

}
