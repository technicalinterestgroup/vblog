package com.technicalinterest.group.api.param;

import com.technicalinterest.group.dao.PageBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @package: com.technicalinterest.group.dao
 * @className: Log
 * @description: 日志管理
 * @author: Shuyu.Wang
 * @date: 2019-09-01 19:20
 * @since: 0.1
 **/
@Data
@ApiModel(description = "日志查询参数")
public class QueryLogParam extends PageBaseParam {
	@ApiModelProperty(value = "请求用户名")
	private String userName;
	@ApiModelProperty(value = "请求方法名")
	private String classMethod;
	@ApiModelProperty(value = "请求方法描述")
	private String operation;



}
