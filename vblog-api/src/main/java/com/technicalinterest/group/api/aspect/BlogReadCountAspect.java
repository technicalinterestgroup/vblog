package com.technicalinterest.group.api.aspect;

import com.alibaba.fastjson.JSONObject;
import com.technicalinterest.group.api.vo.ApiResult;
import com.technicalinterest.group.api.vo.ArticleContentVO;
import com.technicalinterest.group.service.ArticleService;
import com.technicalinterest.group.service.constant.ResultEnum;
import com.technicalinterest.group.service.dto.ArticleContentDTO;
import com.technicalinterest.group.service.util.IpAdrressUtil;
import com.technicalinterest.group.service.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

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
	private RedisUtil redisUtil;

	public static final Long ARTICL_READ_TIME = 60 * 10L;

	@Pointcut("@annotation(com.technicalinterest.group.service.annotation.VBlogReadCount)")
	public void read() {
	}

	@AfterReturning(value = "read()&&args(id)", returning = "result")
	public void doAfterReturningAdvice1(JoinPoint joinPoint, Long id, ApiResult result) {
		log.info("博客阅读数累加后置通知：id={},result={}", id, JSONObject.toJSON(result));
		if (StringUtils.equals(result.getCode(), ResultEnum.SUCCESS.getCode())) {
			//获取用户ip地址
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			String ip = IpAdrressUtil.getIpAdrress(request);
			ArticleContentVO articleContentVO = (ArticleContentVO) result.getData();
			try {
				String key="ip="+ip+",id="+id;
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

	}

}
