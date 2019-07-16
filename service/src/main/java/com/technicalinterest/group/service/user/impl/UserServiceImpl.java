package com.technicalinterest.group.service.user.impl;
import com.technicalinterest.group.mapper.UserMapper;
import com.technicalinterest.group.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

/**
 * @package: com.shuyu.blog.service.impl
 * @className: UserServiceImpl
 * @description:
 * @author: Shuyu.Wang
 * @date: 2019-07-14 18:37
 * @since: 0.1
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 登录
     * @param userVO
     * @return UserDTO
     * @author: shuyu.wang
     * @date: 2019-07-14 18:48
     */
//    @Override
//    public UserDTO login(UserVO userVO) {
//        UserDTO userDTO=new UserDTO();
//        User user=new User();
//        user.setUserName(userVO.getUserName());
//        //用户名判断
//        User user1 = userMapper.userByUser(user);
//        if (Objects.isNull(user1)){
//            userDTO.setMsg(UserConstant.NO_USER);
//            return userDTO;
//        }
//        //密码判断
//        user.setPassWord(user.getPassWord());
//        user1 = userMapper.userByUser(user);
//        if (Objects.isNull(user1)){
//            userDTO.setMsg(UserConstant.PASSWORD_ERROR);
//            return userDTO;
//        }
//        //生成token
//        String uuid = UUID.randomUUID().toString();
//        userDTO.success();
//        userDTO.setUserToken(uuid);
//        userDTO.setUserName(userVO.getUserName());
//        return userDTO;
//    }
}
