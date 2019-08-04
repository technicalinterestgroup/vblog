package com.technicalinterest.group.aop;

import com.technicalinterest.group.api.constant.ResultCode;
import com.technicalinterest.group.api.constant.ResultMessage;
import com.technicalinterest.group.api.vo.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/** 异常 code 转换
 * @description:
 * @author: Guo Lixiao
 * @date: 2018-1-9 19:15
 * @see:
 * @since:
 **/
@ControllerAdvice
@Slf4j
public class ControllerExceptionAOP {


	/**
	 * @Description: 全局参数校验拦截
	 * @author: shuyu.wang
	 * @date: 2019-08-04 16:47
	 * @param e
	 * @return null
	 */
	@ExceptionHandler(BindException.class)
	@ResponseBody
	public ApiResult exception(BindException e) {
		BindingResult bindingResult = e.getBindingResult();
		ApiResult apiResult = new ApiResult();
		if (bindingResult.hasErrors()) {
			apiResult.fail(bindingResult.getFieldError().getDefaultMessage());
			return apiResult;
		} else {
			apiResult.success();
			return apiResult;
		}

	}
	/**
	 * @Description: 全局异常拦截
	 * @author: shuyu.wang
	 * @date: 2019-08-04 16:47
	 * @param e
	 * @return null
	*/
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	ApiResult handleException(Exception e, HttpServletRequest request) {
		log.error("未catch异常！", e);
		return new ApiResult(ResultCode.SERVER_ERROR, ResultMessage.SERVER_ERROR);
	}




}
