package com.technicalinterest.group.service.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @ClassName: FileIdUtils
 * @Author: shuyu.wang
 * @Description:
 * @Date: 2020/3/17 16:44
 * @Version: 1.0
 */
public class FileIdUtils {

    public static String creater(Integer code,String OriginalFilename){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileSuffix = OriginalFilename.substring(OriginalFilename.lastIndexOf(".") + 1, OriginalFilename.length());
//        String fileName =sdf.format(new Date())+"_"+code+"_"+ UUID.randomUUID().toString().toUpperCase()
//                .replace("-", "")+"."+fileSuffix;
        Random random = new Random();
        String fileName =sdf.format(new Date())+"_"+random.nextInt(10000)+"."+fileSuffix;
        return fileName;
    }
}
