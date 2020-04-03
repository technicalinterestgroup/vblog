package com.technicalinterest.group.service;

import com.technicalinterest.group.dao.WebsiteNotice;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.dto.VSystemDTO;
import com.technicalinterest.group.service.dto.WebsiteNoticeDTO;

/**
 * @ClassName: WebsiteNoticeService
 * @Author: shuyu.wang
 * @Description: 系统公告
 * @Date: 2020/3/23 11:55
 * @Version: 1.0
 */
public interface WebsiteNoticeService {

    /**
     * 首页轮播通知
     * @param type
     * @return
     */
    ReturnClass getIndexWebsiteNotice(Short type);

    /**
     * 首页轮播通知
     * @param id
     * @return
     */
    ReturnClass<WebsiteNotice> getWebsiteNoticeDetail(Long id);

    /**
     * 通知列表
     * @param websiteNoticeDTO
     * @return
     */
    ReturnClass getIndexWebsiteList(WebsiteNoticeDTO websiteNoticeDTO);

    /**
     * 新增或更新
     * @param websiteNoticeDTO
     * @return
     */
    ReturnClass saveOrUpdate(WebsiteNoticeDTO websiteNoticeDTO);

    /**
     * 增加阅读数
     * @param id
     */
    void addReadCount(Long id);

}
