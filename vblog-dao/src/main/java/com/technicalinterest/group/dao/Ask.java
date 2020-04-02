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
public class Ask extends BaseDao {
    private String title;

    /**
     * 标签id
     */
    private Long  tagId;

    /**
     * 作者
     */
    private String userName;

    /**
     * 描述
     */
    private String description;

    /**
     * 文章内容html格式
     */
    private String contentFormat;

    /**
     * 文章内容md格式
     */
    private String content;

    /**
     * 阅量
     */
    private Integer readCount;

    /**
     * 回答数量
     */
    private Integer replyCount;
    /**
     * 状态
     */
    private Short  state;
}
