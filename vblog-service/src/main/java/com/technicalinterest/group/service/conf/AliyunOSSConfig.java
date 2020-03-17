package com.technicalinterest.group.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName: AliyunOSSConfig
 * @Author: shuyu.wang
 * @Description: 阿里云oss配置
 * @Date: 2020/3/17 16:18
 * @Version: 1.0
 */
@Component
@ConfigurationProperties(prefix = "aliyun-oss")
@Data
public class AliyunOSSConfig {

    /**
     * 服务器地点
     */
    private String region;
    /**
     * 服务器地址
     */
    private String endpoint;
    /**
     * OSS身份id
     */
    private String accessKeyId;
    /**
     * 身份密钥
     */
    private String accessKeySecret;

    /**
     * 文件bucketName
     */
    private String bucketName;
    /**
     * 包文件地址前缀
     */
    private String domainApp;

}
