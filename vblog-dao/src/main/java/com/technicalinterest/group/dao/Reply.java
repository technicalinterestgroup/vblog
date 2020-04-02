package com.technicalinterest.group.dao;

import lombok.Data;

/**
 * @ClassName: Ask
 * @Author: shuyu.wang
 * @Description:
 * @Date: 2020/3/31 15:16
 * @Version: 1.0
 */
@Data
public class Reply extends BaseDao {
    private Long askId;

    /**
     * 作者
     */
    private String userName;


    /**
     * 文章内容html格式
     */
    private String contentFormat;

    /**
     * 文章内容md格式
     */
    private String content;

    /**
     * 点赞数量
     */
    private Integer likeCount;

    /**
     * 是否被采纳
     */
    private Short  state;
}
