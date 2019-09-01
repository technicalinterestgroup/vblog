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
@ApiModel(description = "管理员查询博客参数")
public class QueryArticleAdminParam extends PageBaseParam {

	@ApiModelProperty(value = "搜索条件")
	private String condition;

	@ApiModelProperty(value = "文章作者")
	private String userName;

}
