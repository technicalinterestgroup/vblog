package com.technicalinterest.group.api.param;

import lombok.Data;

/**
 * @ClassName: QueryWebsiteNoticeParam
 * @Author: shuyu.wang
 * @Description:
 * @Date: 2020/3/23 12:55
 * @Version: 1.0
 */
@Data
public class QueryWebsiteNoticeParam extends PageBaseParam {

    /**
     * 标题
     */
    private String title;
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
}
