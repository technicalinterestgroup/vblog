package com.technicalinterest.group.service.dto;

import com.technicalinterest.group.dao.PageBase;
import lombok.Data;

/**
 * @ClassName: WebsiteNoticeDTO
 * @Author: shuyu.wang
 * @Description:
 * @Date: 2020/3/23 12:57
 * @Version: 1.0
 */
@Data
public class WebsiteNoticeDTO extends PageBase {

    private Long id;
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
