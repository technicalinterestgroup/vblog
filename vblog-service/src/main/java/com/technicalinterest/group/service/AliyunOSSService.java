package com.technicalinterest.group.service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.technicalinterest.group.service.dto.FileUploadDTO;
import com.technicalinterest.group.service.exception.VLogException;
import com.technicalinterest.group.service.util.FileIdUtils;
import com.technicalinterest.group.service.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
    @Value("${Oos.endpoint}")
    private String endpoint;
    @Value("${Oos.accessKeyId}")
    private String accessKeyId;
    @Value("${Oos.accessKeySecret}")
    private String accessKeySecret;
    @Value("${Oos.bucketName}")
    private String bucketName;

    private String url="https://shuyu-vblog.oss-cn-beijing.aliyuncs.com/";
    @Autowired
    private FileUploadService fileUploadService;


    /**
     *
     * 上传图片
     * @param file
     * @return
     */
    public String uploadImg2Oss(MultipartFile file,String userName) {
        log.info("图片上传，文件名：{}",file.getOriginalFilename());
        String name = FileIdUtils.creater(null,file.getOriginalFilename());
        try {
            InputStream inputStream = file.getInputStream();
            String path= uploadFile2OSS(inputStream, name,userName);
            FileUploadDTO fileUploadDTO=FileUploadDTO.builder().userName(userName).fileName(file.getOriginalFilename())
                    .newFileName(name).filePath(path).fileSize(FileUtil.getFileSize(file.getSize(),"K")).build();
            fileUploadService.insert(fileUploadDTO);
            return path;
        } catch (Exception e) {
            log.error("图片流异常",e);
            throw new VLogException("图片上传失败");
        }
    }

    /**
     * 上传图片获取fileUrl
     * @param instream
     * @param fileName
     * @return
     */
    private String uploadFile2OSS(InputStream instream, String fileName,String filedir) {
        String ret = "";
        try {
            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            //上传文件
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            String key=filedir +"/"+ fileName;
            PutObjectResult putResult = ossClient.putObject(bucketName, key, instream, objectMetadata);
            log.info("ETag",putResult.getETag());
            ret = url+key;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new VLogException("图片上传失败");
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public static String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        return "image/jpeg";
    }

    /**
     * 删除文件
     *
     * @param fileName
     * @param bucketName
     */
    public void deleteFile(String fileName, String bucketName) {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        ossClient.deleteObject(bucketName, fileName);
        ossClient.shutdown();
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
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        ossClient.deleteObject(bucketName, fileId);
        ossClient.shutdown();
    }



    /**
     * 多图片上传
     * @param fileList
     * @return
     */
    public String checkList(List<MultipartFile> fileList,String userName) {
        String  fileUrl = "";
        String  str = "";
        String  photoUrl = "";
        for(int i = 0;i< fileList.size();i++){
            fileUrl = uploadImg2Oss(fileList.get(i),userName);
            //            str = getImgUrl(fileUrl);
            if(i == 0){
                photoUrl = str;
            }else {
                photoUrl += "," + str;
            }
        }
        return photoUrl.trim();
    }

}
