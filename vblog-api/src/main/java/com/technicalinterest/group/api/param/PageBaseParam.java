package com.technicalinterest.group.api.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @package: com.technicalinterest.group.service.dto
 * @className: PageDTO
 * @description:
 * @author: Shuyu.Wang
 * @date: 2019-08-08 12:45
 * @since: 0.1
 **/
@Data
public class PageBaseParam {
	/**
	* 页数
	*/
	@ApiModelProperty(value = "页数")
	private Integer currentPage=1;
	/**
	 * 每页条数
	 */
	@ApiModelProperty(value = "每页条数")
	private Integer pageSize=10;
}
