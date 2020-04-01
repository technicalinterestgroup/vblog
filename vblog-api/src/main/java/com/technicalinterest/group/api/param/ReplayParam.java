package com.technicalinterest.group.api.param;

import lombok.Data;

/**
 * @ClassName: ReplayParam
 * @Author: shuyu.wang
 * @Description:
 * @Date: 2020/4/1 13:29
 * @Version: 1.0
 */
@Data
public class ReplayParam {
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
}
