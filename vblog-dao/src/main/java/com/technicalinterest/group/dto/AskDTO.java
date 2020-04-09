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
public class AskDTO{

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


    private Date createTime;

    private Integer vCollection;

    private String contentFormat;

    private String content;
}
