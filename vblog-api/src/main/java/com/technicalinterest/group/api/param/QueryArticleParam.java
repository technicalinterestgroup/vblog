package com.technicalinterest.group.api.param;

import com.technicalinterest.group.dao.PageBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @package: com.technicalinterest.group.dao
 * @className: QueryArticleDTO
 * @description:
 * @author: Shuyu.Wang
 * @date: 2019-08-05 13:14
 * @since: 0.1
 **/
@Data
@ApiModel(description = "新增文章参数")
public class QueryArticleParam extends PageBase {

	@ApiModelProperty(value = "搜索条件")
	private String condition;
	@ApiModelProperty(value = "文章分类id")
	private String categoryId;
	@ApiModelProperty(value = "文章标签id")
	private String  tagId;
	@ApiModelProperty(value = "文章状态",allowableValues = " 0：草稿，1：发布")
	private Short state=1;
	@ApiModelProperty(hidden = true)
	private String userName;
}
