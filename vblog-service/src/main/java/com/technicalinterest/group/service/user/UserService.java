package com.technicalinterest.group.service.user;


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
    ReturnClass<UserDTO> login(EditUserDTO userDTO);



    /**
     * 注册新用户
     * @author: shuyu.wang
     * @date: 2019-07-21 21:32
     * @param newUserDTO
     * @return null
    */
    ReturnClass<String> addUser(EditUserDTO newUserDTO);


   /**
    * 更新用户信息
    * @author: shuyu.wang
    * @date: 2019-07-21 22:11
    * @param editUserDTO
    * @return null
   */
    ReturnClass<String> updateUser(EditUserDTO editUserDTO);

    /**
     * @Description: 退出登录
     * @author: shuyu.wang
     * @date: 2019-07-21 22:22
     * @param token
     * @return null
    */
    ReturnClass<Boolean> logout(String token);


    /**
     * @Description: 根据toke获取用户信息
     * @author: shuyu.wang
     * @date: 2019-07-28 19:43
     * @return null
    */
    ReturnClass<UserDTO> getUserByToken();
}
