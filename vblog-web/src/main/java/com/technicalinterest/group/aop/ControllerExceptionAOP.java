package com.technicalinterest.group.aop;

import com.alibaba.fastjson.JSONException;
import com.technicalinterest.group.api.constant.ResultCode;
import com.technicalinterest.group.api.constant.ResultMessage;
import com.technicalinterest.group.api.vo.ApiResult;
import com.technicalinterest.group.service.exception.VLogException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

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
	@ExceptionHandler(BindException.class)
	@ResponseBody
	public ApiResult exception(BindException e) {
		BindingResult bindingResult = e.getBindingResult();
		ApiResult apiResult = new ApiResult();
		log.info("校验参数异常 message:{}", bindingResult.getFieldError().getDefaultMessage());
		apiResult.fail(bindingResult.getFieldError().getDefaultMessage());
		apiResult.setCode(ResultCode.PARAM_ERROR);
		return apiResult;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ApiResult validationError(MethodArgumentNotValidException ex) {
		ApiResult apiResult = new ApiResult();
		FieldError fieldError = ex.getBindingResult().getFieldError();
		log.info("校验参数异常 message:{}", fieldError.getField() + fieldError.getDefaultMessage());
		apiResult.fail(fieldError.getField() + fieldError.getDefaultMessage());
		apiResult.setCode(ResultCode.PARAM_ERROR);
		return apiResult;
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
	@ExceptionHandler(value = { JSONException.class })
	@ResponseBody
	public ApiResult noJSONFormatException(HttpServletRequest req,JSONException e) {
		ApiResult apiResult = new ApiResult();
		if (log.isDebugEnabled()) {
			log.debug(e != null ? e.getMessage() : "");
		}
		log.info("json格式转换异常", e);
		apiResult.fail(ResultMessage.DATA_ERROR);
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

	@ExceptionHandler(value = VLogException.class)
	@ResponseBody
	public ApiResult myErrorHandler(VLogException e) {
		log.info("自定义异常", e);
		ApiResult apiResult = new ApiResult();
		apiResult.fail(e.getMessage());
		apiResult.setCode(ResultCode.SERVICE_ERROR);
		return apiResult;
	}

	/**
	 * @Description: 参数未接收到
	 * @author: shuyu.wang
	 * @date: 2019-08-05 18:02
	 * @param e
	 * @return null
	 */
	@ExceptionHandler(value = HttpMessageNotReadableException.class)
	@ResponseBody
	public ApiResult handleHttpMessageNotReadableException(HttpServletRequest req,HttpMessageNotReadableException e) {
		log.info("参数获取异常", e);
		ApiResult apiResult = new ApiResult();
		apiResult.fail(ResultMessage.PARAM_ERROR);
		apiResult.setCode(ResultCode.PARAM_ERROR);
		return apiResult;
	}

	/**
	 * @Description: 请求方式不支持
	 * @author: shuyu.wang
	 * @date: 2019-08-05 18:02
	 * @param e
	 * @return null
	 */
	@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
	@ResponseBody
	public ApiResult httpRequestMethodNotSupportedException(HttpServletRequest req,HttpMessageNotReadableException e) {
		log.info("请求方式不支持", e);
		ApiResult apiResult = new ApiResult();
		apiResult.fail(ResultMessage.METHOD_NOT_ALLOWED);
		apiResult.setCode(ResultCode.METHOD_NOT_ALLOWED);
		return apiResult;
	}

	/**
	 * @Description: 媒体类型检查
	 * @author: shuyu.wang
	 * @date: 2019-08-05 18:10
	 * @param e
	 * @return null
	 */
	@ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
	@ResponseBody
	public ApiResult httpMessageNotReadableException(HttpServletRequest req, HttpMediaTypeNotSupportedException e) {
		log.info("媒体类型异常", e);
		ApiResult apiResult = new ApiResult();
		apiResult.fail(ResultMessage.MEDIATYPE_ERROR);
		apiResult.setCode(ResultCode.MEDIATYPE_ERROR);
		return apiResult;
	}

	/**
	 * @Description:数据库唯一key重复
	 * @author: shuyu.wang
	 * @date: 2019-08-06 13:07
	 * @param e
	 * @return null
	 */
	@ExceptionHandler(DuplicateKeyException.class)
	@ResponseBody
	public ApiResult handleDuplicateKeyException(DuplicateKeyException e) {
		log.info("数据库唯一键重复异常", e);
		ApiResult apiResult = new ApiResult();
		apiResult.fail("数据库唯一键重复异常");
		apiResult.setCode(ResultCode.ERROR);
		return apiResult;
	}

	/**
	 * @Description:数据库唯一key重复
	 * @author: shuyu.wang
	 * @date: 2019-08-06 13:07
	 * @param e
	 * @return null
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseBody
	public ApiResult handleNoHandlerFoundException(HttpServletRequest req,NoHandlerFoundException e) {
		log.info("url无效");
		ApiResult apiResult = new ApiResult();
		apiResult.fail(ResultCode.NO_URL, ResultMessage.NO_URL);
		return apiResult;
	}

	private String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		// 如果是多级代理，那么取第一个ip为客户端ip
		if (ip != null && ip.indexOf(",") != -1) {
			ip = ip.substring(0, ip.indexOf(",")).trim();
		}

		return ip;
	}
}
