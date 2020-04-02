package com.technicalinterest.group.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName: ReplyVO
 * @Author: shuyu.wang
 * @Description:
 * @Date: 2020/4/1 13:34
 * @Version: 1.0
 */
@Data
public class ReplyVO {
    private Long id;

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

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
}
