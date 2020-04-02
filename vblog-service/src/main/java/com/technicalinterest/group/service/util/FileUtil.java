package com.technicalinterest.group.service.util;

/**
 * @package: com.technicalinterest.group.api.util
 * @className: FileUtil
 * @description:
 * @author: Shuyu.Wang
 * @date: 2020-03-17 22:23
 * @since: 0.1
 **/

public class FileUtil {
	/**
	 * 判断文件大小
	 * @param len 文件长度
	 * @param unit 限制单位（B,K,M,G）
	 * @return
	 */
	public static double getFileSize(Long len, String unit) {
		double fileSize = 0;
		if ("B".equals(unit.toUpperCase())) {
			fileSize = (double) len;
		} else if ("K".equals(unit.toUpperCase())) {
			fileSize = (double) len / 1024;
		} else if ("M".equals(unit.toUpperCase())) {
			fileSize = (double) len / 1048576;
		} else if ("G".equals(unit.toUpperCase())) {
			fileSize = (double) len / 1073741824;
		}

		return fileSize;
	}

	/**
	 * 判断文件大小
	 * @param len 文件长度
	 * @param size 限制大小
	 * @param unit 限制单位（B,K,M,G）
	 * @return
	 */
	public static boolean checkFileSize(Long len, int size, String unit) {
		double fileSize = 0;
		if ("B".equals(unit.toUpperCase())) {
			fileSize = (double) len;
		} else if ("K".equals(unit.toUpperCase())) {
			fileSize = (double) len / 1024;
		} else if ("M".equals(unit.toUpperCase())) {
			fileSize = (double) len / 1048576;
		} else if ("G".equals(unit.toUpperCase())) {
			fileSize = (double) len / 1073741824;
		}
		if (fileSize > size) {
			return false;
		}
		return true;
	}
}
