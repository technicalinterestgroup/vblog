package com.technicalinterest.group.service;


import com.technicalinterest.group.dao.User;
import com.technicalinterest.group.dto.UserRoleDTO;
import com.technicalinterest.group.service.dto.EditUserDTO;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.dto.UserBlogInfoDTO;
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
    ReturnClass login(EditUserDTO userDTO,Short type);



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
     * 修改密码
     * @author: shuyu.wang
     * @date: 2019-07-21 22:11
     * @param editUserDTO
     * @return null
     */
    ReturnClass updateUserPassWord(EditUserDTO editUserDTO);

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
    ReturnClass<UserBlogInfoDTO> getUserInfo(String userName,String loginUser);

    /**
     * 根据登录token获取用户名
     * @return
     */
    String getUserNameByLoginToken();

  /**
   * 获取信息
   * @return
   */
  UserRoleDTO getUserRoleDTOByLoginToken();
   /**
    * 根据用户名查询用户信息
    * @return
    */
    User getUserByUserName(String userName);
}
