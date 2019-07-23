package com.technicalinterest.group.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
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
@ApiModel(description = "登录请求参数")
public class UserDTO {
    @ApiParam(value = "用户名",required = true)
    private String userName;
    @ApiParam(value = "密码",required = true)
    private String passWord;
}
