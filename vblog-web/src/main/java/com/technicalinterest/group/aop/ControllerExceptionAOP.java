package com.technicalinterest.group.aop;

import com.technicalinterest.group.api.constant.ResultCode;
import com.technicalinterest.group.api.constant.ResultMessage;
import com.technicalinterest.group.api.vo.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	ApiResult handleException(Exception e, HttpServletRequest request) {
		log.error("出错啦！", e);
		return new ApiResult(ResultCode.SERVER_ERROR, ResultMessage.SERVER_ERROR);
	}

}
