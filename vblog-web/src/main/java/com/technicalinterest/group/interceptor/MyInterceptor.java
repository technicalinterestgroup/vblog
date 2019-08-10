package com.technicalinterest.group.interceptor;



import com.alibaba.fastjson.JSONObject;
import com.technicalinterest.group.api.vo.ApiResult;
import com.technicalinterest.group.constant.UrlConstant;
import com.technicalinterest.group.service.constant.ResultEnum;
import com.technicalinterest.group.service.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * @package: com.ganinfo.common.interceptor
 * @className: MybatisInterceptor
 * @description: 自定义拦截器
 * @author: Shuyu.Wang
 * @date: 2018-11-30 18:13
 * @version: V1.0
**/
@Slf4j
public class MyInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtil;

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
        boolean flag = true;
        response.setHeader(UrlConstant.ORIGIN_HEADER_STRING, UrlConstant.HEADER_ALL_VALUE_STRING);
        response.setHeader(UrlConstant.CREDENTIALS_HEADER_STRING, UrlConstant.CREDENTIALS_VALUE_STRING);
        response.setHeader(UrlConstant.METHODS_HEADER_STRING, UrlConstant.HEADER_ALL_VALUE_STRING);
        response.setHeader(UrlConstant.AGE_HEADER_STRING, UrlConstant.HEADER_AGE_VALUE_STRING);
        response.setHeader(UrlConstant.ALLOW_HEADERS_HEADER_STRING, UrlConstant.ALLOW_HEADERS_VALUE_STRING);
        response.setHeader(UrlConstant.EXPOSE_HEADERS_HEADER_STRING, UrlConstant.HEADER_ALL_VALUE_STRING);
        String url = request.getRequestURL().toString();
            if (!url.endsWith(UrlConstant.LOGIN_URL_STRING) && !url.endsWith(UrlConstant.LOGOUT_URL_STRING)&&!url.endsWith(UrlConstant.NEW_URL_STRING)&&!url.endsWith(UrlConstant.DOC_URL_STRING)) {
                String ACCESS_TOKEN_STRING = request.getHeader(UrlConstant.ACCESS_TOKEN_STRING);
                if (!Objects.isNull(ACCESS_TOKEN_STRING)) {
                    String userName =(String)redisUtil.get(ACCESS_TOKEN_STRING);
                    if (Objects.isNull(userName)) {
                        response.setContentType(UrlConstant.CONTENT_TYPE_STRING);
                        ApiResult result = new ApiResult(ResultEnum.TIME_OUT);
                        PrintWriter out = response.getWriter();
                        out.write(JSONObject.toJSONString(result));
                        out.close();
                        return false;
                    }
                    redisUtil.expire(ACCESS_TOKEN_STRING,ACTIVATION_TIME);
                    redisUtil.expire(userName,ACTIVATION_TIME);
                    return true;
                } else {
                    response.setContentType(UrlConstant.CONTENT_TYPE_STRING);
                    ApiResult result = new ApiResult(ResultEnum.ACCESTOKEN_NULL);
                    PrintWriter out = response.getWriter();
                    out.write(JSONObject.toJSONString(result));
                    out.close();
                    return false;
                }
            }
        return flag;
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

}
