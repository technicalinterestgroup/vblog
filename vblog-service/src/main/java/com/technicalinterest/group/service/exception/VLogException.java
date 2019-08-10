package com.technicalinterest.group.service.exception;

import com.technicalinterest.group.service.constant.ResultEnum;

/**
 * @package: com.technicalinterest.group.service.exception
 * @className: VLogException
 * @description: 自定义异常
 * @author: Shuyu.Wang
 * @date: 2019-07-17 22:14
 * @since: 0.1
 **/

public class VLogException extends RuntimeException {

	private static final long serialVersionUID = -8616472009504184287L;

	protected String code;

	protected String message;

	public VLogException() {
		super();
	}

	public VLogException(String message) {
		this.code=ResultEnum.ERROR.getCode();
		this.message = message;
	}

	public VLogException(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public VLogException(ResultEnum resultEnum) {
		super();
		this.code = resultEnum.getCode();
		this.message = resultEnum.getMsg();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
