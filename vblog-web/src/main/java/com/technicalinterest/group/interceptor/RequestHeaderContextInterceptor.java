package com.technicalinterest.group.interceptor;

import com.technicalinterest.group.constant.UrlConstant;
import com.technicalinterest.group.service.context.RequestHeaderContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @package:com.ganinfo.interceptor
 * @className:RequestHeaderContextInterceptor
 * @description:统一拦截request header
 * @author:Shuyu.Wang
 * @date:2018-11-30 18:13
 * @version:V1.0
**/
public class RequestHeaderContextInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        initHeaderContext(request);
        return super.preHandle(request, response, handler);
    }

    private void initHeaderContext(HttpServletRequest request){
        new RequestHeaderContext.RequestHeaderContextBuild()
                .accesToken(request.getHeader(UrlConstant.ACCESS_TOKEN_STRING))
                .bulid();
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        RequestHeaderContext.clean();
        super.postHandle(request, response, handler, modelAndView);
    }
}
