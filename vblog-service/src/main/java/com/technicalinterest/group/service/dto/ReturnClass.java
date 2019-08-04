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
public final class ReturnClass implements Serializable {
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
	private Object data;

	public ReturnClass() {
		this.success = false;
		this.msg = "请求失败";
	}

	public ReturnClass(Boolean success, String msg) {
		super();
		this.success = success;
		this.msg = msg;
	}

	public ReturnClass(Boolean success, String msg, Object data) {
		super();
		this.success = success;
		this.msg = msg;
		this.data = data;
	}

	public Boolean isSuccess() {
		return success;
	}

	public static ReturnClass success() {
		return new ReturnClass(true, "请求成功！");
	}

	public static ReturnClass success(Object data) {
		return new ReturnClass(true, "请求成功！", data);
	}

	public static ReturnClass success(String msg, Object data) {
		return new ReturnClass(true, msg, data);
	}

	public static ReturnClass success(String msg) {
		return new ReturnClass(true, msg);
	}

	public static ReturnClass fail(String msg) {
		return new ReturnClass(false, msg);
	}
	public static ReturnClass fail() {
		return new ReturnClass(false, "请求失败");
	}
}
