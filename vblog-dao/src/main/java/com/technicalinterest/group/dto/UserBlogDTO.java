package com.technicalinterest.group.dto;

import lombok.Data;

/**
 * @ClassName: UserBlogInfoDTO
 * @Author: shuyu.wang
 * @Description:
 * @Date: 2020/3/6 17:05
 * @Version: 1.0
 */
@Data
public class UserBlogDTO {

    /**
     * 博客数
     */
    private Integer blogCount;
    /**
     * 获赞数
     */
    private Integer likeCount;
    /**
     * 评论顺
     */
    private Integer commentCount;
    /**
     * 阅读数
     */
    private Integer readCount;
}
