package com.technicalinterest.group.config;


import com.technicalinterest.group.interceptor.MyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
public class MyWebAppConfig extends WebMvcConfigurerAdapter {

//    @Bean
//    MyInterceptor myInterceptor() {
//        return new MyInterceptor();
//    }

//	@Bean
//	public HandlerInterceptor getRequestLimitInterceptor(){
//		return new RequestLimitInterceptor();
//	}

//	@Bean
//	public RequestHeaderContextInterceptor requestHeaderContextInterceptor() {
//		return new RequestHeaderContextInterceptor();
//	}


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
//        registry.addInterceptor(myInterceptor()).addPathPatterns("/**");
//		registry.addInterceptor(requestHeaderContextInterceptor()).addPathPatterns(MyWebConstant.PATH_PATTERN_STRING);
//		registry.addInterceptor(getRequestLimitInterceptor()).addPathPatterns(MyWebConstant.PATH_PATTERN_STRING);
        super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("docs.html").addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


}
