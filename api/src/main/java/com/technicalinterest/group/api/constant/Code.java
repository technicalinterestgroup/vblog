package com.technicalinterest.group.api.constant;

/**
 * 返回码常量
 */
public interface Code {

    int SUCCESS         = 0;

    /** 4xx 请求异常 **/
    /** 参数错误 **/
    int E_400           = 400;

    /** 无权访问 **/
    int E_401           = 401;

    /** 5xx 系统异常 **/
    int E_503           = 503;
    
    int E_500 = 500;//系统异常

    
}
