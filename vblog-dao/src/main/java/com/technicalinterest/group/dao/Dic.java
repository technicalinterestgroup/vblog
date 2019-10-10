package com.technicalinterest.group.dao;

import lombok.Data;

/**
 * @package: com.technicalinterest.group.dto
 * @className: DicDTO
 * @description:
 * @author: Shuyu.Wang
 * @date: 2019-10-09 21:29
 * @since: 0.1
 **/
@Data
public class Dic extends BaseDao{
	/**
	 * 键
	 */
	private String dicKey;
	/**
	 * 键描述
	 */
	private String keyDesc;
	/**
	 * 值
	 */
	private String dicValue;
	/**
	 * 值描述
	 */
	private String valueDesc;

	/**
	 * 上级key
	 */
	private String parentKey;
}
