package com.technicalinterest.group.dto;

import com.technicalinterest.group.dao.BaseDao;
import lombok.Data;

/**
 * @package: com.technicalinterest.group.dao
 * @className: Tag
 * @description: 文章标签
 * @author: Shuyu.Wang
 * @date: 2019-08-11 19:18
 * @since: 0.1
 **/
@Data
public class TagDTO{

	private Long id;

	private String name;
    /** 
    * 文章数
    */ 
	private Integer articleNum;
}
