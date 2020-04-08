package com.technicalinterest.group.dto;

import com.technicalinterest.group.dao.BaseDao;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName: Ask
 * @Author: shuyu.wang
 * @Description:
 * @Date: 2020/3/31 15:16
 * @Version: 1.0
 */
@Data
public class ReplyDTO{

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

    private Date createTime;

    private Integer vLike;
}
