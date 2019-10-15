package com.technicalinterest.group.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.technicalinterest.group.api.vo.ApiResult;
import com.technicalinterest.group.constant.UrlConstant;
import com.technicalinterest.group.service.UserService;
import com.technicalinterest.group.service.constant.ResultEnum;
import com.technicalinterest.group.service.constant.UserConstant;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.dto.UserDTO;
import com.technicalinterest.group.service.util.IpAdrressUtil;
import com.technicalinterest.group.service.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * @package: com.ganinfo.common.interceptor
 * @className: MybatisInterceptor
 * @description: 权限拦截器
 * @author: Shuyu.Wang
 * @date: 2018-11-30 18:13
 * @version: V1.0
 **/
@Slf4j
public class MyInterceptor implements HandlerInterceptor {

	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private UserService userService;

	private static final long ACTIVATION_TIME = 60 * 60 * 24;

	/**
	 * @param request
	 * @param response
	 * @param handler
	 * @return 只有返回true才会继续向下执行，返回false取消当前请求
	 * @throws Exception
	 * @author: shuyu.wang
	 * @description: 在请求处理之前进行调用（Controller方法调用之前）
	 * @date: 2018/12/2 21:06
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		response.setHeader(UrlConstant.ORIGIN_HEADER_STRING, UrlConstant.HEADER_ALL_VALUE_STRING);
		response.setHeader(UrlConstant.CREDENTIALS_HEADER_STRING, UrlConstant.CREDENTIALS_VALUE_STRING);
		response.setHeader(UrlConstant.METHODS_HEADER_STRING, UrlConstant.HEADER_ALL_VALUE_STRING);
		response.setHeader(UrlConstant.AGE_HEADER_STRING, UrlConstant.HEADER_AGE_VALUE_STRING);
		response.setHeader(UrlConstant.ALLOW_HEADERS_HEADER_STRING, UrlConstant.ALLOW_HEADERS_VALUE_STRING);
		response.setHeader(UrlConstant.EXPOSE_HEADERS_HEADER_STRING, UrlConstant.HEADER_ALL_VALUE_STRING);

		String url = request.getRequestURI();
		String ACCESS_TOKEN_STRING = request.getHeader(UrlConstant.ACCESS_TOKEN_STRING);
		if (!url.startsWith(UrlConstant.NOT_AUTH_URL_STRING) && !url.contains(UrlConstant.DOC_URL_STRING)) {
			if (!Objects.isNull(ACCESS_TOKEN_STRING)) {
				if (!redisUtil.hasKey(ACCESS_TOKEN_STRING)) {
					log.error(">>>无效请求：登录超时;ip:【{}】,url:【{}】", IpAdrressUtil.getIpAdrress(request), request.getRequestURL().toString());
					printJson(response, ResultEnum.TIME_OUT);
					return false;
				}
				String userInfo = (String) redisUtil.get(ACCESS_TOKEN_STRING);
				UserDTO userDTO = JSONObject.parseObject(userInfo, UserDTO.class);
				if (Objects.nonNull(userDTO)&& StringUtils.isNotEmpty(userDTO.getUserName())) {
					//判断是否是普通用户
					if (userDTO.getRoleType() == 2) {
						String o = (String) redisUtil.get(UserConstant.ADMIN_AUTH_URL);
						if (o.contains(url + ",")) {
							log.error(">>>非法请求：普通用户请求管理员接口;ip:【{}】,url:【{}】", IpAdrressUtil.getIpAdrress(request), request.getRequestURL().toString());
							printJson(response, ResultEnum.NO_AUTH);
							return false;
						}
					}
					redisUtil.expire(ACCESS_TOKEN_STRING, ACTIVATION_TIME);
					return true;
				}else {
					log.error(">>>无效请求：用户不存在;ip:【{}】,url:【{}】", IpAdrressUtil.getIpAdrress(request), request.getRequestURL().toString());
					printJson(response, ResultEnum.USERINFO_ERROR);
					return false;
				}
			} else {
				log.error(">>>非法请求：无token;ip:【{}】,url:【{}】", IpAdrressUtil.getIpAdrress(request), request.getRequestURL().toString());
				printJson(response, ResultEnum.ACCESTOKEN_NULL);
				return false;
			}
		}
		if (Objects.nonNull(ACCESS_TOKEN_STRING)) {
			if (redisUtil.hasKey(ACCESS_TOKEN_STRING)) {
				redisUtil.expire(ACCESS_TOKEN_STRING, ACTIVATION_TIME);
			}
		}
		return true;
	}

	/**
	 * @param request
	 * @param response
	 * @param handler
	 * @param modelAndView
	 * @throws Exception
	 * @author: shuyu.wang
	 * @description: 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
	 * @date: 2018/12/2 21:06
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	/**
	 * @param request
	 * @param response
	 * @param handler
	 * @param ex
	 * @throws Exception
	 * @author: shuyu.wang
	 * @description: 在整个请求结束之后被调用，也就是在DispatcherServlet渲染了对应的视图之后执行（主要是用于进行资源清理工作）
	 * @date: 2018/12/2 21:06
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	}

	private void printJson(HttpServletResponse response, ResultEnum resultEnum) {
		try {
			response.setContentType(UrlConstant.CONTENT_TYPE_STRING);
			ApiResult result = new ApiResult(ResultEnum.NO_AUTH);
			PrintWriter out = response.getWriter();
			out.write(JSONObject.toJSONString(result));
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
