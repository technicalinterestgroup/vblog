package com.technicalinterest.group.service.dto;

import com.technicalinterest.group.dao.BaseDao;
import lombok.Data;

/**
 * @package: com.technicalinterest.group.dao
 * @className: FileUpload
 * @description: 文件上传记录
 * @author: Shuyu.Wang
 * @date: 2019-08-25 14:53
 * @since: 0.1
 **/
@Data
public class FileUploadDTO {

	private String userName;

	/**
	* 原始文件名称
	*/
	private String fileName;
	/**
	 * 新文件名称
	 */
	private String newFileName;
	/**
	 * 文件路径
	 */
	private String filePath;
	/**
	 * 文件大小
	 */
	private Double fileSize;
	/**
	 * 文件类型 1：图片 2：文件
	 */
	private Short fileType;



}
