package com.technicalinterest.group.api.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @package: com.shuyu.blog.vo
 * @className: UserVO
 * @description:
 * @author: Shuyu.Wang
 * @date: 2019-07-14 19:00
 * @since: 0.1
 **/
@Data
@ApiModel
public class UserVO {
    @NotEmpty(message="用户名不能为空！")
    private String userName;
    @Size(min=6,max=10,message = "密码长度必须6到10位")
    private String passWord;
}
