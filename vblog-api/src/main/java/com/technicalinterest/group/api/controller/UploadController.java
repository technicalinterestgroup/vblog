package com.technicalinterest.group.api.controller;

import com.technicalinterest.group.api.vo.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.Kernel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @package: com.technicalinterest.group.api.controller
 * @className: UploadController
 * @description: 文件上传
 * @author: Shuyu.Wang
 * @date: 2019-08-08 19:25
 * @since: 0.1
 **/
@Api(tags = "文件管理")
@RestController
@RequestMapping("file")
@Slf4j
public class UploadController {
	@Value("file_path")
	private String file_path;

	//文件类型
	public static String IMG_TYPE_PNG = "PNG";
	public static String IMG_TYPE_JPG = "JPG";
	public static String IMG_TYPE_JPEG = "JPEG";
	public static String IMG_TYPE_DMG = "BMP";
	public static String IMG_TYPE_GIF = "GIF";

	public static final String FILE_TYPE_ERROR = "文件格式不支持！";

	public static final String SIZE_ERROR = "文件过大！";

	public static final Integer FILE_SIZE = 500;

	@ApiOperation(value = "上传", notes = "上传")
	@PostMapping(value = "/upload")
	public ApiResult<String> uploadFileTest(@RequestParam(value = "file") MultipartFile file) {
		ApiResult<String> apiResult = new ApiResult<>();
		String filePath = file_path;
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
		if (!(IMG_TYPE_DMG.equals(suffix.toUpperCase()) || IMG_TYPE_GIF.equals(suffix.toUpperCase()) || IMG_TYPE_JPEG.equals(suffix.toUpperCase()) || IMG_TYPE_JPG
				.equals(suffix.toUpperCase()) || IMG_TYPE_PNG.equals(suffix.toUpperCase()))) {
			apiResult.fail(FILE_TYPE_ERROR);
			return apiResult;
		}
		if (!checkFileSize(file.getSize(), FILE_SIZE, "")) {
			apiResult.fail(SIZE_ERROR);
			return apiResult;
		}
		FileOutputStream fileOutputStream = null;
		try {

			String targetFileName = UUID.randomUUID().toString().replace("-", "");
			String targetFilePath = filePath + File.separator + getToday() + File.separator + targetFileName + "." + suffix;
			File targetFile = new File(targetFilePath);
			if (!targetFile.getParentFile().exists()) {
				log.info(targetFile.getParentFile() + "路径不存在，创建文件夹！");
				targetFile.getParentFile().mkdirs();
			}
			fileOutputStream = new FileOutputStream(targetFile);
			IOUtils.copy(file.getInputStream(), fileOutputStream);
			apiResult.success(targetFilePath, "文件上传成功");
			log.info("------>>>>>>文件上传成功!<<<<<<------");
		} catch (IOException e) {
			log.error("文件上传异常", e);
			apiResult.fail("文件上传异常");
		} finally {
			try {
				fileOutputStream.close();
			} catch (IOException e) {
				log.error("", e);
				apiResult.fail("文件上传异常");
			}
		}
		return apiResult;
	}

	public String getToday() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(d);
	}

	/**
	 * 判断文件大小
	 * @param len 文件长度
	 * @param size 限制大小
	 * @param unit 限制单位（B,K,M,G）
	 * @return
	 */
	public static boolean checkFileSize(Long len, int size, String unit) {
		//        long len = file.length();
		double fileSize = 0;
		if ("B".equals(unit.toUpperCase())) {
			fileSize = (double) len;
		} else if ("K".equals(unit.toUpperCase())) {
			fileSize = (double) len / 1024;
		} else if ("M".equals(unit.toUpperCase())) {
			fileSize = (double) len / 1048576;
		} else if ("G".equals(unit.toUpperCase())) {
			fileSize = (double) len / 1073741824;
		}
		if (fileSize > size) {
			return false;
		}
		return true;
	}

}
