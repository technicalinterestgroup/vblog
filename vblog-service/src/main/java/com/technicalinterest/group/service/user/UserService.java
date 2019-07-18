package com.technicalinterest.group.service.user;


import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.dto.UserDTO;
import com.technicalinterest.group.service.vo.UserVO;

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
    ReturnClass<UserVO> login(UserDTO userDTO);
    
}
