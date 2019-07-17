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
@Data
@ApiModel(description = "统一返回封装类")
public final class ApiResult implements Serializable {

	/**
	 * @Field： 状态编码
	 */
	@ApiModelProperty(value = "状态码")
	private Integer code;
	/**
	 * @Field：提示信息
	 */
	@ApiModelProperty(value = "提示信息")
	private String message;
	/**
	 * @Field：主要数据
	 */
	@ApiModelProperty(value = "数据体")
	private Object data;

	public ApiResult() {
		super();
	}

	private ApiResult(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public ApiResult(Integer code, String message, Object data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}

	/**
	 * @author:shuyu.wang
	 * @description:构造方法
	 * @date: 2018/12/1 20:09
	 * @param: code 返回状态编码
	 * @param: message 返回信息
	 * @return: null
	 */
	public ApiResult(ResultCode code, ResultMessage message) {
		this(code.getCode(), message.getMessage());
	}

	public void setCode(ResultCode code) {
		this.code = code.getCode();
	}

	public void setMessage(ResultMessage message) {
		this.message = message.getMessage();
	}

	/**
	 * @author:shuyu.wang
	 * @description:请求成功设备默认的状态编码和消息提示
	 * @date: 2018/11/30 18:37
	 * @param: null
	 * @return: null
	 */
	public void setSuccess(Object data) {
		this.setCode(ResultCode.SUCCESS);
		this.setMessage(ResultMessage.SUCCESS);
		this.data = data;
	}

	public void setSuccess() {
		this.setCode(ResultCode.SUCCESS);
		this.setMessage(ResultMessage.SUCCESS);
	}

	public void setFail(ResultMessage resultMessage) {
		this.setCode(ResultCode.ERROR);
		this.setMessage(resultMessage);
	}

}
