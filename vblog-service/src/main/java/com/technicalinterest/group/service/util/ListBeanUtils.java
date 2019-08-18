package com.technicalinterest.group.service.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
	 * @param source
	 * @return java.util.List
	 */
	public static <T> List copyProperties(String source, Class<T> clazz) {
		if (Objects.isNull(source)) {
			return new ArrayList();
		}
		return JSON.parseArray(source, clazz);
	}
}
