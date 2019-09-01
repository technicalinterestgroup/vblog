package com.technicalinterest.group.dto;

import lombok.Data;

import java.util.Date;

/**
 * @package: com.technicalinterest.group.dto
 * @className: FileDTO
 * @description:
 * @author: Shuyu.Wang
 * @date: 2019-08-25 19:16
 * @since: 0.1
 **/
@Data
public class FileDTO {

	private Long id;
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
	 * 文件类型
	 */
	private Short fileType;
	/**
	 * 上传时间
	 */
	private Date createTime;
	/**
	 * 状态
	 */
	private Short isDel;


}
