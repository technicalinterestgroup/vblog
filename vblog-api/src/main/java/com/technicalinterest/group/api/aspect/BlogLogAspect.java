package com.technicalinterest.group.api.aspect;

import com.alibaba.fastjson.JSONObject;
import com.technicalinterest.group.api.param.UserParam;
import com.technicalinterest.group.api.vo.ApiResult;
import com.technicalinterest.group.dao.Log;
import com.technicalinterest.group.dao.User;
import com.technicalinterest.group.dto.UserRoleDTO;
import com.technicalinterest.group.mapper.UserMapper;
import com.technicalinterest.group.service.LogService;
import com.technicalinterest.group.service.UserService;
import com.technicalinterest.group.service.annotation.BlogOperation;
import com.technicalinterest.group.service.context.RequestHeaderContext;
import com.technicalinterest.group.service.util.IpAdrressUtil;
import com.technicalinterest.group.service.util.RedisUtil;
import com.technicalinterest.group.service.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * @package: com.technicalinterest.group.api.aspect
 * @className: LogAspect
 * @description: 日志Aop
 * @author: Shuyu.Wang
 * @date: 2019-08-18 13:46
 * @since: 0.1
 **/
@Aspect
@Component
@Slf4j
public class BlogLogAspect {
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private LogService logService;

	/**
	 * 请求切点方法(已提供@RequestMapping,@GetMapping,@PostMapping注解，需要其它请增加)
	 */
	@Pointcut(" @annotation(org.springframework.web.bind.annotation.RequestMapping) || " +
			"   @annotation(org.springframework.web.bind.annotation.GetMapping) || " +
			"   @annotation(org.springframework.web.bind.annotation.PostMapping)")
	void requestMapping() {
	}

	@Pointcut("@annotation(com.technicalinterest.group.service.annotation.BlogOperation)")
	public void logPoinCut() {
	}

	@AfterReturning(value = "requestMapping() && logPoinCut()", returning = "result")
	public void doAfterReturningAdvice1(JoinPoint joinPoint, ApiResult result) {
		log.info(">>>日志拦截AOP开始");
		//获取用户ip地址
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		BlogLogAspect blogLogAspect=SpringContextUtil.getBean(BlogLogAspect.class);
		String accessToken = RequestHeaderContext.getInstance().getAccessToken();
		blogLogAspect.analisy(joinPoint,result,request.getRequestURL().toString(), IpAdrressUtil.getIpAdrress(request),accessToken);
		log.info(">>>日志拦截AOP结束");
	}
    @Async
	public void analisy(JoinPoint joinPoint, ApiResult result,String url,String ip,String token){
        log.info("异步AOP日志");
		//从切面织入点处通过反射机制获取织入点处的方法
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		//获取切入点所在的方法
		Method method = signature.getMethod();
		//获取操作
		BlogOperation operation = method.getAnnotation(BlogOperation.class);
		String operationName = null;
		if (operation != null) {
			operationName = operation.value();
		}
		//获取请求的类名
		String className = joinPoint.getTarget().getClass().getName();
		//获取请求的方法名
		String methodName = method.getName();
		String methodStr = className + "." + methodName;
		//请求的参数
		Object[] args = joinPoint.getArgs();
		//将参数所在的数组转换成json
		String params = null;
		try {
			params = JSONObject.toJSONString(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String userName=null;
		if ("登录".equals(operationName)){
			List<UserParam> userParams = JSONObject.parseArray(params, UserParam.class);
			userName = userParams.get(0).getUserName();
		}else {
			userName = (String) redisUtil.get(token);
		}
		log.info(">>>请求返回结果：{}", JSONObject.toJSON(result));
		try {
			Log log = Log.builder().url(url).ip(ip).userName(userName).classMethod(methodStr)
					.operation(operationName).params(params).result(result.getMsg()).build();
			logService.insert(log);
		} catch (Exception e) {
			log.error("日志保存异常", e);
		}
	}

}
