package com.technicalinterest.group.dao;

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
public class PageBase {
	/**
	* 页数
	*/
	private Integer pageNum=1;
	/**
	 * 每页条数
	 */
	private Integer pageSize=10;
}
