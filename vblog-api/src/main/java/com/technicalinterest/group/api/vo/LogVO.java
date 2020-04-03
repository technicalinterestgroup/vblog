package com.technicalinterest.group.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.technicalinterest.group.dao.BaseDao;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @package: com.technicalinterest.group.dao
 * @className: Log
 * @description: 日志管理
 * @author: Shuyu.Wang
 * @date: 2019-09-01 19:20
 * @since: 0.1
 **/
@Data
@ApiModel(description = "日志信息")
public class LogVO {
	@ApiModelProperty(value = "请求url")
	private String url;
	@ApiModelProperty(value = "请求ip")
	private String ip;
	@ApiModelProperty(value = "请求用户名")
	private String userName;
	@ApiModelProperty(value = "请求方法")
	private String classMethod;
	@ApiModelProperty(value = "强求方法描述")
	private String operation;
	@ApiModelProperty(value = "请求参数")
	private String params;
	@ApiModelProperty(value = "请求结果")
	private String result;
	@ApiModelProperty(value = "请求结果")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
}
