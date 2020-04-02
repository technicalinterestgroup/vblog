package com.technicalinterest.group.service.constant;

/**
 * @ClassName: RedisKeyConstant
 * @Author: shuyu.wang
 * @Description:
 * @Date: 2020/4/1 14:05
 * @Version: 1.0
 */
public class RedisKeyConstant {
    private static final String USER_INFO="user_info_";

    private static final String upload_time="user_upload_";

    public static String userInfoKey(String userName){
        return USER_INFO+userName;
    }

    public static String uploadTimeKey(String userName){
        return upload_time+userName;
    }
}
