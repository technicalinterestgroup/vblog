package com.technicalinterest.group.api.vo;

import com.technicalinterest.group.api.constant.ResultCode;
import com.technicalinterest.group.api.constant.ResultMessage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @package:com.ganinfo.common.bean
 * @className:ApiResult
 * @description:api 接口返回参数封装
 * @author:Shuyu.Wang
 * @date:2018-11-30 18:13
 * @version:V1.0
 **/
@ApiModel(description = "返回响应数据")
@Data
public final class ApiResult<T> implements Serializable{

	/**
	 * @Field： 状态编码
	 */
	@ApiModelProperty(value = "状态码")
	private Integer code;
	/**
	 * @Field：提示信息
	 */
	@ApiModelProperty(value = "提示信息")
	private String msg;
	/**
	 * @Field：主要数据
	 */
	@ApiModelProperty(value = "数据体")
	private T data;

	public ApiResult() {
		this.setCode(ResultCode.ERROR);
	}

	private ApiResult(Integer code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	private ApiResult(Integer code, String msg, T data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public ApiResult(ResultCode code, ResultMessage message) {
		this(code.getCode(), message.getMessage());
	}

	public void setCode(ResultCode code) {
		this.code = code.getCode();
	}

	public void setMsg(ResultMessage message) {
		this.msg = message.getMessage();
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void success() {
		this.setCode(ResultCode.SUCCESS);
		this.setMsg(ResultMessage.SUCCESS);
	}

	public void success(T data) {
		this.setCode(ResultCode.SUCCESS);
		this.setMsg(ResultMessage.SUCCESS);
		this.data = data;

	}


	public void fail(ResultMessage resultMessage) {
		this.setCode(ResultCode.ERROR);
		this.setMsg(resultMessage);
	}

	public void fail(String msg) {
		this.setCode(ResultCode.ERROR);
		this.setMsg(msg);
	}

}
