package com.technicalinterest.group.aop;

import com.alibaba.fastjson.JSONException;
import com.technicalinterest.group.api.constant.ResultCode;
import com.technicalinterest.group.api.constant.ResultMessage;
import com.technicalinterest.group.api.vo.ApiResult;
import com.technicalinterest.group.service.exception.VLogException;
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
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

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
	@ExceptionHandler({ MethodArgumentNotValidException.class, BindException.class })
	@ResponseBody
	public ApiResult exception(BindException e) {
		BindingResult bindingResult = e.getBindingResult();
		ApiResult apiResult = new ApiResult();
		if (bindingResult.hasErrors()) {
			log.info("校验参数异常 message:{}", bindingResult.getFieldError().getDefaultMessage());
			apiResult.fail(bindingResult.getFieldError().getDefaultMessage());
			apiResult.setCode(ResultCode.PARAM_ERROR);
			return apiResult;
		} else {
			apiResult.success();
			return apiResult;
		}

	}

	/**
	 * 校验异常捕捉
	 * @param e
	 * @return
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseBody
	public ApiResult handleValidationException(ConstraintViolationException e) {
		ApiResult apiResult = new ApiResult();
		StringBuilder sb = new StringBuilder();
		for (ConstraintViolation<?> s : e.getConstraintViolations()) {
			log.info("value:{} message:{}", s.getInvalidValue(), s.getMessage());
			sb.append(s.getMessage()).append('!');
		}
		if (sb != null && sb.length() > 0) {
			log.info("校验参数异常 message:{}", sb.toString());
			apiResult.fail(sb.toString());

		}
		apiResult.setCode(ResultCode.PARAM_ERROR);
		return apiResult;
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
	public ApiResult handleException(Exception e, HttpServletRequest request) {
		log.error("未catch异常！", e);
		return new ApiResult(ResultCode.SERVER_ERROR, ResultMessage.SERVER_ERROR);
	}



	/**
	 * @Description: json异常拦截
	 * @author: shuyu.wang
	 * @date: 2019-08-05 17:41
	 * @param e
	 * @return null
	*/
	@ExceptionHandler(value = { JSONException.class})
	@ResponseBody
	public ApiResult noJSONFormatException(JSONException e) {
		ApiResult apiResult = new ApiResult();
		if (log.isDebugEnabled()) {
			log.debug(e != null ? e.getMessage() : "");
		}
		log.error("json格式转换异常", e);
		apiResult.fail("json格式异常!");
		apiResult.setCode(ResultCode.DATA_ERROR);
		return apiResult;
	}


    /**
     * @Description:自定义异常拦截
     * @author: shuyu.wang
     * @date: 2019-08-05 17:56
     * @param e
     * @return null
    */
	@ResponseBody
	@ExceptionHandler(value = VLogException.class)
	public ApiResult myErrorHandler(VLogException e) {
		ApiResult apiResult = new ApiResult();
		apiResult.fail(e.getMessage());
		apiResult.setCode(ResultCode.SERVICE_ERROR);
		return apiResult;
	}



}
