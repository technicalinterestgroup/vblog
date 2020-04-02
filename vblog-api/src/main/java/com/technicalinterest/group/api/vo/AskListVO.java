package com.technicalinterest.group.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName: AskVO
 * @Author: shuyu.wang
 * @Description:
 * @Date: 2020/3/31 17:32
 * @Version: 1.0
 */
@Data
public class AskListVO {

    private Long id;

    private String title;

    /**
     * 标签id
     */
    private Long  tagId;

    /**
     * 标签id
     */
    private String  tagCN;
    /**
     * 作者
     */
    private String userName;

    /**
     * 描述
     */
    private String description;

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

    @ApiModelProperty(value = "发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
}
