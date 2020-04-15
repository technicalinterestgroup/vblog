package com.technicalinterest.group.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@ApiModel(description = "用户信息")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserBlogInfoDTO {
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @ApiModelProperty(value = "头像url")
    private String photo;
    @ApiModelProperty(value = "积分")
    private Integer integral;
    @ApiModelProperty(value = "个人简介")
    private String userSummary;
    @ApiModelProperty(value = "博客等级")
    private Short grade;
    @ApiModelProperty(value = "博客数")
    private Integer blogCount;
    @ApiModelProperty(value = "获赞数")
    private Integer likeCount;
    @ApiModelProperty(value = "评论数")
    private Integer commentCount;
    @ApiModelProperty(value = "阅读数")
    private Integer readCount;

    private Boolean isFocus=false;
}
