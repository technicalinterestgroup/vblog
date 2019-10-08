package com.technicalinterest.group.config;


import com.technicalinterest.group.interceptor.MyInterceptor;
import com.technicalinterest.group.interceptor.RequestHeaderContextInterceptor;
import com.technicalinterest.group.interceptor.RequestLimitInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @package:com.ganinfo.common.configuration
 * @className:MyWebAppConfigurer
 * @description:自定义拦截器
 * @author:Shuyu.Wang
 * @date:2018-11-30 18:13
 * @version:V1.0
 **/
@Configuration
@Slf4j
public class MyWebAppConfig extends WebMvcConfigurerAdapter {

	@Value("${spring.profiles.active}")
	private String profile;

	private static final String ENV="prod";

    @Bean
    MyInterceptor myInterceptor() {
        return new MyInterceptor();
    }

	@Bean
	public HandlerInterceptor getRequestLimitInterceptor(){
		return new RequestLimitInterceptor();
	}

	@Bean
	public RequestHeaderContextInterceptor requestHeaderContextInterceptor() {
		return new RequestHeaderContextInterceptor();
	}


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	log.info("profile={}",profile);
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
		if (StringUtils.equals(ENV,profile)){
			registry.addInterceptor(myInterceptor()).addPathPatterns("/**");
		}
		registry.addInterceptor(requestHeaderContextInterceptor()).addPathPatterns("/**");
		registry.addInterceptor(getRequestLimitInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


}
