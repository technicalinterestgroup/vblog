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
@ApiModel(description = "查询文章参数")
public class QueryArticleParam extends PageBaseParam {

	@ApiModelProperty(value = "搜索条件")
	private String condition;
	@ApiModelProperty(value = "文章分类id")
	private String categoryId;
	@ApiModelProperty(value = "文章标签id")
	private String  tagId;
	@ApiModelProperty(value = "归档时间")
	private String archiveTime;
}
