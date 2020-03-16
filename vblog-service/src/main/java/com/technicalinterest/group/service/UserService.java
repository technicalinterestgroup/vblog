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
    ReturnClass updateUser(Boolean authCheck,EditUserDTO editUserDTO);

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
    ReturnClass getUserByuserName(Boolean authCheck,String userName);

    /**
     * @Description:查询登陆者用户信息
     * @author: shuyu.wang
     * @date: 2019-08-08 13:08
     * @return null
     */
    ReturnClass getUserDetail();


    
    /**
     * @Description: 判断用户名是否是当前操作登录的用户
     * @author: shuyu.wang
     * @date: 2019-08-08 13:12
     * @param userName
     * @return null
    */
    ReturnClass userNameIsLoginUser(String userName);


    /**
     * @Description:获取博客用户信息
     * @author: shuyu.wang
     * @date: 2019-08-19 12:56
     * @param userName
     * @return null
    */
    ReturnClass getBlogUserInfo(String userName);


    /**
     * @Description: 发送忘记密码邮件
     * @author: shuyu.wang
     * @date: 2019-10-08 17:04
     * @param userName
     * @return null
    */
    ReturnClass sendForgetPassMail(String userName,String email,String img,String token);


    /**
     * @Description: 修改密码
     * @author: shuyu.wang
     * @date: 2019-10-08 17:23
     * @param editUserDTO
     * @return null
    */
    ReturnClass resetPassWord(String key,EditUserDTO editUserDTO);
    /**
     * @Description: 生成验证码
     * @author: shuyu.wang
     * @date: 2019-10-08 17:23
     * @return null
     */
    ReturnClass createImage();

    /**
     * 查询用户信息
     * @param userName
     * @return
     */
    ReturnClass getUserInfo(String userName);
}
