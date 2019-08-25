package com.technicalinterest.group.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @package: com.technicalinterest.group.dto
 * @className: CollectionDTO
 * @description: 收藏列表返回
 * @author: Shuyu.Wang
 * @date: 2019-08-21 13:26
 * @since: 0.1
 **/
@Data
@ApiModel(description = "分类查询返回")
public class CollectionVO {

	/**
	 * 文章作者
	 */
	@ApiModelProperty(value = "收藏文章作者")
	private String userName;

	/**
	 * 收藏文章id
	 */
	@ApiModelProperty(value = "收藏文章id")
	private Long articleId;
	/**
	 * 收藏文章名称
	 */
	@ApiModelProperty(value = "收藏文章名称")
	private String title;
	/**
	 * 收藏时间
	 */
	@ApiModelProperty(value = "收藏时间")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createTime;
}
