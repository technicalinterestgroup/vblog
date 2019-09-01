package com.technicalinterest.group.api.param;

import com.technicalinterest.group.dao.PageBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = "文件查询参数")
public class QueryFileParam extends UserBaseParam {

	/**
	* 原始文件名称
	*/
	@ApiModelProperty(value = "文件名称")
	private String fileName;

	/**
	 * 文件类型 1：图片 2：文件
	 */
	@ApiModelProperty(value = "文件类型",allowableValues = "1：图片 2：文件")
	private Short fileType;



}
