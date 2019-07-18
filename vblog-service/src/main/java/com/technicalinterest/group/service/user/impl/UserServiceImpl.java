package com.technicalinterest.group.service.user.impl;
import com.technicalinterest.group.dao.User;
import com.technicalinterest.group.mapper.UserMapper;
import com.technicalinterest.group.service.constant.UserConstant;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.dto.UserDTO;
import com.technicalinterest.group.service.user.UserService;
import com.technicalinterest.group.service.vo.UserVO;
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
    @Override
    public ReturnClass<UserVO> login(UserDTO userDTO) {
        ReturnClass<UserVO> returnClass=new ReturnClass<>();
        User user=new User();
        user.setUserName(userDTO.getUserName());
        //用户名判断
        User user1 = userMapper.userByUser(user);
        if (Objects.isNull(user1)){
            returnClass.setMsg(UserConstant.NO_USER);
            return returnClass;
        }
        //密码判断
        user.setPassWord(user.getPassWord());
        user1 = userMapper.userByUser(user);
        if (Objects.isNull(user1)){
            returnClass.setMsg(UserConstant.PASSWORD_ERROR);
            return returnClass;
        }
        //生成token
        String uuid = UUID.randomUUID().toString();
        returnClass.success();
        UserVO userVO=new UserVO();
        userVO.setUserToken(uuid);
        userVO.setUserName(userDTO.getUserName());
        return returnClass;
    }
}
