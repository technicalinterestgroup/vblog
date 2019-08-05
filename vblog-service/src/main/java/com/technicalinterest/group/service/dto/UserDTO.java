package com.technicalinterest.group.service.dto;

import lombok.Data;


/**
 * @package: com.shuyu.blog.vo
 * @className: UserVO
 * @description:
 * @author: Shuyu.Wang
 * @date: 2019-07-14 19:00
 * @since: 0.1
 **/
@Data
public class UserDTO {

    private Long id;

    private String userToken;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 头像
     */
    private String photo;
    /**
     * 积分
     */
    private Integer integral;
}
