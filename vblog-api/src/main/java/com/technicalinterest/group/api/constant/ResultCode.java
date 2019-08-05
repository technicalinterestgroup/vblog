package com.technicalinterest.group.api.constant;

/**
 * @package:com.ganinfo.utils
 * @className:ResultCode
 * @description:请求状态编码枚举类
 * @author:Shuyu.Wang
 * @date:2018-11-30 18:13
 * @version:V1.0
**/
public enum ResultCode {
    /**
     * @Field：成功状态
     */
    SUCCESS(0),
    /**
     * @Field：请求失败
     */
    ERROR(-1),
    /**
     * @Field：参数异常
     */
    PARAM_ERROR(2),
    /**
     * @Field：数据格式异常
     */
    DATA_ERROR(3),
    /**
     * @Field：ACCESTOKEN为空
     */
    ACCESTOKEN_NULL(4),
    /**
     * @Field：登录超时
     */
    TIME_OUT(5),
    /**
     * @Field：业务异常
     */
    SERVICE_ERROR(6),

    /**
     * @Field：媒体类型不支持
     */
    MEDIATYPE_ERROR(7),
    /**
     * @Field：url不存在
     */
    NO_URL(404),
    /**
     * @Field：请求方式不支持
     */
    METHOD_NOT_ALLOWED(405),
    /**
     * @Field：网络阻塞
     */
    NET_BLOCK(409),

    /**
     * @Field：服务器异常
     */
    SERVER_ERROR(500);

    private Integer code;

    private ResultCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
