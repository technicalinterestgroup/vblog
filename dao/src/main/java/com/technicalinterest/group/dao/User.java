package com.technicalinterest.group.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @package: com.shuyu.blog.dao
 * @className: User
 * @description: 用户信息
 * @author: Shuyu.Wang
 * @date: 2019-07-14 15:44
 * @since: 0.1
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends Base{

    private String userName;

    private String passWord;

    private String email;


}
