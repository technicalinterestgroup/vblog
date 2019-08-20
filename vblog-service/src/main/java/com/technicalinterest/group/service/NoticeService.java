package com.technicalinterest.group.service;

import com.technicalinterest.group.dao.PageBase;
import com.technicalinterest.group.service.dto.ReturnClass;

/**
 * @package: com.technicalinterest.group.service
 * @className: NoticeService
 * @description: 通知接口层
 * @author: Shuyu.Wang
 * @date: 2019-08-20 13:02
 * @since: 0.1
 **/
public interface NoticeService {

	ReturnClass queryCommentNotice(String userName, PageBase pageBase);
}
