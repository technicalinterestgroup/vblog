package com.technicalinterest.group.service.annotation;

import java.lang.annotation.*;

/**
 * @package: com.technicalinterest.group.api.annotation
 * @className: VBlogReadCount
 * @description: 文章阅读数
 * @author: Shuyu.Wang
 * @date: 2019-08-17 17:47
 * @since: 0.1
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD})
@Documented
public @interface VBlogReadCount {
	String value() default "";

	String type() default "1";
}
