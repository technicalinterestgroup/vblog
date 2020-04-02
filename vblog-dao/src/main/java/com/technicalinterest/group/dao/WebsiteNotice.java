package com.technicalinterest.group.dao;

import lombok.Data;


/**
 * @ClassName: WebsiteNotice
 * @Author: shuyu.wang
 * @Description: 网站广告
 * @Date: 2020/3/23 11:27
 * @Version: 1.0
 */
@Data
public class WebsiteNotice extends BaseDao{
    /**
     * 标题
     */
    private String title;
    /**
     * 轮播图地址
     */
    private String carouselUrl;

    /**
     * 文章内容html格式
     */
    private String contentFormat;

    /**
     * 文章内容md格式
     */
    private String content;
    /**
     * 文章描述
     */
    private String description;
    /**
     * 文章作者
     */
    private String userName;

    /**
     * 文章状态 0：草稿，1：发布
     */
    private Boolean state;
    /**
     * 是否在首页轮播
     */
    private Boolean isIndex;
    /**
     * 1:首页 2：博客页
     */
    private Short type;
    /**
     * 文章状态阅量
     */
    private Integer readCount;

    /**
     * 文章点赞量
     */
    private Integer likeCount;

    /**
     * 文章评论量
     */
    private Integer commentCount;

}
