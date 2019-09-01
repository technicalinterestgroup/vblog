package com.technicalinterest.group.constant;

/**
 * @author Shuyu.Wang
 * @package:com.ganinfo.common.constant
 * @className:UrlConstant
 * @description:Url共用参数
 * @date 2018-02-07 17:35
**/

public class UrlConstant {

    public static final String ACCESS_TOKEN_STRING = "accessToken";

    public static final String NOT_AUTH_URL_STRING = "/vblog/view/";

    public static final String LOGIN_URL_STRING = "login";

    public static final String LOGOUT_URL_STRING = "logout";

    public static final String NEW_URL_STRING = "user/new";

    public static final String DOC_URL_STRING = "swagger-resources";

    public static final String CONTENT_TYPE_STRING = "text/json;charset=UTF-8";

    public static final String ORIGIN_HEADER_STRING="Access-Control-Allow-Origin";

    public static final String CREDENTIALS_HEADER_STRING="Access-Control-Allow-Credentials";

    public static final String METHODS_HEADER_STRING="Access-Control-Allow-Methods";

    public static final String AGE_HEADER_STRING="Access-Control-Max-Age";

    public static final String ALLOW_HEADERS_HEADER_STRING="Access-Control-Allow-Headers";

    public static final String EXPOSE_HEADERS_HEADER_STRING="Access-Control-Expose-Headers";

    public static final String HEADER_ALL_VALUE_STRING="*";

    public static final String HEADER_AGE_VALUE_STRING="3600";

    public static final String ALLOW_HEADERS_VALUE_STRING="Origin,Content-Type,accessToken,X-Requested-With,Accept";

    public static final String CREDENTIALS_VALUE_STRING="true";

    public static final String REQUEST_PRODUCES_JSON_STRING="application/json;charset=UTF-8";
}
