package com.technicalinterest.group.service.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @package: com.technicalinterest.group.service.util
 * @className: ListBeanUtils
 * @description: list bean深度复制
 * @author: Shuyu.Wang
 * @date: 2019-08-18 20:11
 * @since: 0.1
 **/

public class ListBeanUtils {
	/**
	 * @Description:集合深度copay
	 * @author: shuyu.wang
	 * @date: 2019-08-18 20:12
	 * @param list
	 * @return java.util.List
	 */
	public static <T> List copyProperties(List<T> list) {
		if (CollectionUtils.isEmpty(list)) {
			return new ArrayList();
		}
		return JSON.parseArray(JSON.toJSONString(list), list.get(0).getClass());
	}
}
