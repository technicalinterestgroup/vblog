package com.technicalinterest.group.api.vo.websitenotice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.technicalinterest.group.dao.BaseDao;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * @ClassName: WebsiteNotice
 * @Author: shuyu.wang
 * @Description: 网站广告
 * @Date: 2020/3/23 11:27
 * @Version: 1.0
 */
@Data
@ApiModel(description = "通知详情展示")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebsiteNoticeDetailVO {
    private Long id;
    /**
     * 标题
     */
    private String title;

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
    @ApiModelProperty(value = "发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /**
     * 文章状态 0：草稿，1：发布
     */
    private Boolean state;
    /**
     * 是否在首页轮播
     */
    private Integer isIndex;
    /**
     * 1:首页 2：博客页
     */
    private Short type;

    private String carouselUrl;

    /**
     * 是否收藏 1是
     */
    @JsonProperty("vCollection")
    private Integer vCollection;
    /**
     * 是否点赞 1是
     */
    @JsonProperty("vLike")
    private Integer vLike;



}
