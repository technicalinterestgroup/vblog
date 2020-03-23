package com.technicalinterest.group.api.vo.websitenotice;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @ClassName: WebsiteNoticeVO
 * @Author: shuyu.wang
 * @Description: 轮播数据
 * @Date: 2020/3/23 12:42
 * @Version: 1.0
 */
@Data
@ApiModel(description = "轮播数据")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebsiteNoticeVO {

    private Long id;

    /**
     * 标题
     */
    private String title;
    /**
     * 轮播图地址
     */
    private String carouselUrl;
}
