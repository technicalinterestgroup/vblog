package com.technicalinterest.group.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @package: com.shuyu.blog.dto
 * @className: UserDTO
 * @description:
 * @author: Shuyu.Wang
 * @date: 2019-07-14 18:46
 * @since: 0.1
 **/
@Data
@ApiModel(description = "登录返回参数")
public class UserVO {
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "唯一token")
    private String userToken;
    @ApiModelProperty(value = "昵称")
    private String nickName;
}
