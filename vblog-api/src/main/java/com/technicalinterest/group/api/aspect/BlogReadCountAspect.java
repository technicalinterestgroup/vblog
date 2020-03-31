package com.technicalinterest.group.api.aspect;

import com.alibaba.fastjson.JSONObject;
import com.technicalinterest.group.api.vo.ApiResult;
import com.technicalinterest.group.api.vo.ArticleContentVO;
import com.technicalinterest.group.api.vo.AskVO;
import com.technicalinterest.group.service.ArticleService;
import com.technicalinterest.group.service.AskService;
import com.technicalinterest.group.service.Enum.ResultEnum;
import com.technicalinterest.group.service.annotation.BlogOperation;
import com.technicalinterest.group.service.annotation.VBlogReadCount;
import com.technicalinterest.group.service.util.IpAdrressUtil;
import com.technicalinterest.group.service.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @package: com.technicalinterest.group.service.aspect
 * @className: VBlogReadCountAspect
 * @description: 文章阅读数量
 * @author: Shuyu.Wang
 * @date: 2019-08-17 17:50
 * @since: 0.1
 **/
@Aspect
@Component
@Slf4j
public class BlogReadCountAspect {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private AskService askService;
	@Autowired
	private RedisUtil redisUtil;

	public static final Long ARTICL_READ_TIME = 60 * 10L;

	@Pointcut("@annotation(com.technicalinterest.group.service.annotation.VBlogReadCount)")
	public void read() {
	}

	@AfterReturning(value = "read()&&args(id,userName)", returning = "result")
	public void doAfterReturningAdvice1(JoinPoint joinPoint,Long id, String userName, ApiResult result) {
		//从切面织入点处通过反射机制获取织入点处的方法
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		//获取切入点所在的方法
		Method method = signature.getMethod();
		//获取操作
		VBlogReadCount operation = method.getAnnotation(VBlogReadCount.class);
		//博客阅读
        if ("1".equals(operation.type())){
			log.info("博客阅读数累加后置通知：id={},result={}", id, JSONObject.toJSON(result));
			if (StringUtils.equals(result.getCode(), ResultEnum.SUCCESS.getCode())) {
				//获取用户ip地址
				HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
				String ip = IpAdrressUtil.getIpAdrress(request);
				ArticleContentVO articleContentVO = (ArticleContentVO) result.getData();
				try {
					String key="article_read_ip="+ip+",id="+id;
					if (!redisUtil.hasKey(key)) {
						redisUtil.set(key, key, ARTICL_READ_TIME);
						articleService.addReadCount(id);
						articleContentVO.setReadCount(articleContentVO.getReadCount() + 1);
						log.info("新的访问阅读数增加");
					}else {
						log.info("重复请求，阅读数不增加！");
					}
				} catch (Exception e) {
					log.error("", e);
				}
			}
			//问答
		}else if("2".equals(operation.type())) {
			log.info("问答阅读数累加后置通知：id={},result={}", id, JSONObject.toJSON(result));
			if (StringUtils.equals(result.getCode(), ResultEnum.SUCCESS.getCode())) {
				//获取用户ip地址
				HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
				String ip = IpAdrressUtil.getIpAdrress(request);
				AskVO askVO = (AskVO) result.getData();
				try {
					String key="ask_read_ip="+ip+",id="+id;
					if (!redisUtil.hasKey(key)) {
						redisUtil.set(key, key, ARTICL_READ_TIME);
						askService.updateReadCount(id);
						askVO.setReadCount(askVO.getReadCount() + 1);
						log.info("新的访问阅读数增加");
					}else {
						log.info("重复请求，阅读数不增加！");
					}
				} catch (Exception e) {
					log.error("", e);
				}
			}
			//通告
		}else if("3".equals(operation.type())) {
			log.info("通告阅读数累加后置通知：id={},result={}", id, JSONObject.toJSON(result));
			if (StringUtils.equals(result.getCode(), ResultEnum.SUCCESS.getCode())) {
				//获取用户ip地址
				HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
				String ip = IpAdrressUtil.getIpAdrress(request);
				AskVO askVO = (AskVO) result.getData();
				try {
					String key="notice_read_ip="+ip+",id="+id;
					if (!redisUtil.hasKey(key)) {
						redisUtil.set(key, key, ARTICL_READ_TIME);
//						askService.updateReadCount(id);
						askVO.setReadCount(askVO.getReadCount() + 1);
						log.info("新的访问阅读数增加");
					}else {
						log.info("重复请求，阅读数不增加！");
					}
				} catch (Exception e) {
					log.error("", e);
				}
			}
		}


	}

}
