package com.technicalinterest.group.service.context;

/**
 * @package:com.ganinfo.common.context
 * @className:RequestHeaderContext
 * @description:controller统一拦截accessToken
 * @author:Shuyu.Wang
 * @date:2018-12-01 20:50
 * @version:V1.0
**/

public class RequestHeaderContext {
    
    private static final ThreadLocal<RequestHeaderContext> REQUEST_HEADER_CONTEXT_THREAD_LOCAL = new ThreadLocal<>();
    /** 
    * @Field：accessToken
    */ 
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public static RequestHeaderContext getInstance() {
        return REQUEST_HEADER_CONTEXT_THREAD_LOCAL.get();
    }

    public void setContext(RequestHeaderContext context) {
        REQUEST_HEADER_CONTEXT_THREAD_LOCAL.set(context);
    }

    public static void clean() {
        REQUEST_HEADER_CONTEXT_THREAD_LOCAL.remove();
    }

    private RequestHeaderContext(RequestHeaderContextBuild requestHeaderContextBuild) {
        this.accessToken = requestHeaderContextBuild.accessToken;
        setContext(this);
    }

    public static class RequestHeaderContextBuild {
        private String accessToken;

        public RequestHeaderContextBuild accesToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }


        public RequestHeaderContext bulid() {
            return new RequestHeaderContext(this);
        }
    }
}
