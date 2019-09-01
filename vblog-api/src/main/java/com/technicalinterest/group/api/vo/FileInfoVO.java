package com.technicalinterest.group.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = "文件上传记录")
public class FileInfoVO {
	@ApiModelProperty(value = "记录id")
	private Long id;
	@ApiModelProperty(value = "原始文件名称")
	private String fileName;
	@ApiModelProperty(value = "新文件名称")
	private String newFileName;
	@ApiModelProperty(value = "文件路径")
	private String filePath;
	@ApiModelProperty(value = "文件大小")
	private Double fileSize;
	@ApiModelProperty(value = "文件类型",allowableValues = "1:图片，2：文件")
	private Short fileType;
	@ApiModelProperty(value = "文件状态",allowableValues = "0:正常，1：禁用")
	private Short isDel;
	@ApiModelProperty(value = "上传时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;


}
