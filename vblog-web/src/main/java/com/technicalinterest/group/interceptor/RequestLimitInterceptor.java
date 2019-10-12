package com.technicalinterest.group.interceptor;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.RateLimiter;
import com.technicalinterest.group.api.vo.ApiResult;
import com.technicalinterest.group.service.constant.ResultEnum;
import com.technicalinterest.group.service.util.IpAdrressUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * @description: 限流拦截器
 * @author: Shuyu.Wang
 * @date: 2018-11-30 18:13
 * @version: V1.0
 **/
@Slf4j
public class RequestLimitInterceptor implements HandlerInterceptor {

    private static final RateLimiter LIMITER = RateLimiter.create(30);


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        if (!LIMITER.tryAcquire()) {
            log.info("请求数量超过网站限流，请求被拦截! ulr={},ip={}",httpServletRequest.getRequestURL().toString(), IpAdrressUtil.getIpAdrress(httpServletRequest));
            ApiResult result = new ApiResult(ResultEnum.NET_BLOCK);
            returnJson(httpServletResponse, JSON.toJSONString(result));
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    private void returnJson(HttpServletResponse response, String json) throws Exception {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/json; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);
        } catch (IOException e) {
            log.error("LoginInterceptor response error ---> {}", e.getMessage(), e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
