package com.technicalinterest.group.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @package:com.ganinfo.common.bean
 * @className:ReturnClass
 * @description:service层方法返回数据封装
 * @author:Shuyu.Wang
 * @date:2018-11-30 18:13
 * @version:V1.0
 **/
@Data
public final class ReturnClass<T> implements Serializable {
	private static final long serialVersionUID = 1098033339357933985L;
	/**
	 * @Field：请求状态
	 */
	private Boolean success;
	/**
	 * @Field：请求消息提示
	 */
	private String msg;
	/**
	 * @Field：数据
	 */
	private T data;

	public ReturnClass() {
		this.success = false;
		this.msg = "请求失败";
	}

	public Boolean isSuccess() {
		return success;
	}

	public void success() {
		this.success = true;
	}

	public void success(T data) {
		this.success = true;
		this.data = data;
	}

	@Override
	public String toString() {
		return "ReturnClass [success=" + success + ", msg=" + msg + ", data=" + data + "]";
	}
}
