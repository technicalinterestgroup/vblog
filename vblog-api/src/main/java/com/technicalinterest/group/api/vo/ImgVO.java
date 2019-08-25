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
@ApiModel(description = "图片展示")
public class ImgVO {
	@ApiModelProperty(value = "记录id")
	private Long id;
	@ApiModelProperty(value = "原始文件名称")
	private String fileName;
	@ApiModelProperty(value = "文件路径")
	private String filePath;

}
