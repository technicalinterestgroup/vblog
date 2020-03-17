package com.technicalinterest.group.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.technicalinterest.group.config.AliyunOSSConfig;
import com.technicalinterest.group.service.Enum.FileTypeEnum;
import com.technicalinterest.group.service.exception.VLogException;
import com.technicalinterest.group.service.util.FileIdUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: AliyunOSSUtil
 * @Author: shuyu.wang
 * @Description:
 * @Date: 2020/3/17 16:27
 * @Version: 1.0
 */
@Component
@Slf4j
public class AliyunOSSService {
    @Autowired
    private AliyunOSSConfig aliyunOSSProperties;


    /**
     * 文件不存在
     */
    private final String NO_SUCH_KEY = "NoSuchKey";
    /**
     * 存储空间不存在
     */
    private final String NO_SUCH_BUCKET = "NoSuchBucket";

    /**
     * 上传文件到阿里云 OSS 服务器
     *
     * @param files
     * @param fileTypeEnum
     * @param storagePath
     * @return
     */
    public List<String> uploadFile(MultipartFile[] files, FileTypeEnum fileTypeEnum, String storagePath, String prefix) {
        //创建OSSClient实例
        log.info("配置信息"+ JSONObject.toJSON(aliyunOSSProperties));
        OSSClient ossClient = new OSSClient(aliyunOSSProperties.getEndpoint(), aliyunOSSProperties.getAccessKeyId(), aliyunOSSProperties.getAccessKeySecret());
        List<String> fileIds = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                //创建一个唯一的文件名，类似于id，就是保存在OSS服务器上文件的文件名(自定义文件名)
                String fileName = FileIdUtils.creater(fileTypeEnum.getCode(),file.getOriginalFilename());
                InputStream inputStream = file.getInputStream();
                ObjectMetadata objectMetadata = new ObjectMetadata();
                //设置数据流里有多少个字节可以读取
                objectMetadata.setContentLength(inputStream.available());
                objectMetadata.setCacheControl("no-cache");
                objectMetadata.setHeader("Pragma", "no-cache");
                objectMetadata.setContentType(file.getContentType());
                objectMetadata.setContentDisposition("inline;filename=" + fileName);
                fileName = StringUtils.isNotBlank(storagePath) ? storagePath + "/" + fileName : fileName;
                //上传文件
                PutObjectResult result = ossClient.putObject(aliyunOSSProperties.getBucketName(), fileName, inputStream, objectMetadata);
                log.info("Aliyun OSS AliyunOSSUtil.uploadFileToAliyunOSS,result:{}", result);
                fileIds.add(prefix + fileName);
            }
        } catch (IOException e) {
            log.error("Aliyun OSS AliyunOSSUtil.uploadFileToAliyunOSS fail,reason:{}", e);
        } finally {
            ossClient.shutdown();
        }
        return fileIds;
    }

    /**
     * 删除文件
     *
     * @param fileName
     * @param bucketName
     */
    public void deleteFile(String fileName, String bucketName) {
        OSSClient ossClient = new OSSClient(aliyunOSSProperties.getEndpoint(), aliyunOSSProperties.getAccessKeyId(), aliyunOSSProperties.getAccessKeySecret());
        ossClient.deleteObject(bucketName, fileName);
        shutdown(ossClient);
    }

    /**
     * 根据图片全路径删除就图片
     *
     * @param imgUrl     图片全路径
     * @param bucketName 存储路径
     */
    public void delImg(String imgUrl, String bucketName) {
        if (StringUtils.isBlank(imgUrl)) {
            return;
        }
        //问号
        int index = imgUrl.indexOf('?');
        if (index != -1) {
            imgUrl = imgUrl.substring(0, index);
        }
        int slashIndex = imgUrl.lastIndexOf('/');
        String fileId = imgUrl.substring(slashIndex + 1);
        OSSClient ossClient = new OSSClient(aliyunOSSProperties.getEndpoint(), aliyunOSSProperties.getAccessKeyId(), aliyunOSSProperties.getAccessKeySecret());
        ossClient.deleteObject(bucketName, fileId);
        shutdown(ossClient);
    }

    /**
     * 判断文件是否存在
     *
     * @param fileName   文件名称
     * @param bucketName 文件储存空间名称
     * @return true:存在,false:不存在
     */
    public boolean doesObjectExist(String fileName, String bucketName) {
        Validate.notEmpty(fileName, "fileName can be not empty");
        Validate.notEmpty(bucketName, "bucketName can be not empty");
        OSSClient ossClient = new OSSClient(aliyunOSSProperties.getEndpoint(), aliyunOSSProperties.getAccessKeyId(), aliyunOSSProperties.getAccessKeySecret());
        try {
            boolean found = ossClient.doesObjectExist(bucketName, fileName);
            return found;
        } finally {
            shutdown(ossClient);
        }

    }

    /**
     * 复制文件
     *
     * @param fileName              源文件名称
     * @param bucketName            源储存空间名称
     * @param destinationBucketName 目标储存空间名称
     * @param destinationObjectName 目标文件名称
     */
    public void ossCopyObject(String fileName, String bucketName, String destinationBucketName, String destinationObjectName) {
        Validate.notEmpty(fileName, "fileName can be not empty");
        Validate.notEmpty(bucketName, "bucketName can be not empty");
        Validate.notEmpty(destinationBucketName, "destinationBucketName can be not empty");
        Validate.notEmpty(destinationObjectName, "destinationObjectName can be not empty");
        OSSClient ossClient = new OSSClient(aliyunOSSProperties.getEndpoint(), aliyunOSSProperties.getAccessKeyId(), aliyunOSSProperties.getAccessKeySecret());
        // 拷贝文件。
        try {
            ossClient.copyObject(bucketName, fileName, destinationBucketName, destinationObjectName);
        } catch (OSSException oe) {
            log.error("errorCode:{},Message:{},requestID:{}", oe.getErrorCode(), oe.getMessage(), oe.getRequestId());
            if (oe.getErrorCode().equals(NO_SUCH_KEY)) {
                throw new VLogException("文件不存在");
            } else if (oe.getErrorCode().equals(NO_SUCH_BUCKET)) {
                throw new VLogException("存储空间不存在");
            } else {
                throw new VLogException("文件存储失败");
            }
        } finally {
            shutdown(ossClient);
        }
    }

    /**
     * 关闭oos
     *
     * @param ossClient ossClient
     */
    private void shutdown(OSSClient ossClient) {
        ossClient.shutdown();
    }
}
