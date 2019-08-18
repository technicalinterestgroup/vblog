package com.technicalinterest.group.dao;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @package: com.shuyu.blog.dao
 * @className: BaseEntity
 * @description: 基础类
 * @author: Shuyu.Wang
 * @date: 2019-07-14 15:50
 * @since: 0.1
 **/
@Data
public class BaseDao {
    private Long id;

    private Short isDel;

    private Date createTime;

    private Date updateTime;
}
