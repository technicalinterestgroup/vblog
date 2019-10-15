package com.technicalinterest.group.service.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @package: com.technicalinterest.group.service.util
 * @className: SpringContextUtil
 * @description:
 * @author: Shuyu.Wang
 * @date: 2019-10-12 12:48
 * @since: 0.1
 **/
@Component
public class SpringContextUtil implements ApplicationContextAware {

	// Spring应用上下文环境
	private static ApplicationContext applicationContext;
    @Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtil.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
		return (T) applicationContext.getBean(name);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<?> clz) throws BeansException {
		return (T) applicationContext.getBean(clz);
	}

}
