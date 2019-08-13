package com.technicalinterest.group.api.vo;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @package: com.technicalinterest.group.api.vo
 * @className: ArticleArchiveVO
 * @description: 文章归档Vo
 * @author: Shuyu.Wang
 * @date: 2019-08-13 22:43
 * @since: 0.1
 **/
@Data
@ApiModel(description = "文章归档")
public class ArticleArchiveVO {
	@ApiModelProperty(value = "归档时间")
	private String time;

	@ApiModelProperty(value = "文章条数")
	private Integer sum;

	@ApiModelProperty(value = "作者名")
	private String userName;
}
