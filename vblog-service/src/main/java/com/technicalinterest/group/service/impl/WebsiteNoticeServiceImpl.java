package com.technicalinterest.group.service.impl;

import com.github.pagehelper.PageHelper;
import com.technicalinterest.group.dao.WebsiteNotice;
import com.technicalinterest.group.dto.ArticlesDTO;
import com.technicalinterest.group.mapper.WebsiteNoticeMapper;
import com.technicalinterest.group.service.Enum.ResultEnum;
import com.technicalinterest.group.service.UserService;
import com.technicalinterest.group.service.WebsiteNoticeService;
import com.technicalinterest.group.service.constant.ArticleConstant;
import com.technicalinterest.group.service.constant.WebsiteNoticeConstant;
import com.technicalinterest.group.service.dto.PageBean;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.dto.WebsiteNoticeDTO;
import com.technicalinterest.group.service.exception.VLogException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName: WebsiteNoticeServiceImpl
 * @Author: shuyu.wang
 * @Description: 实现层
 * @Date: 2020/3/23 12:49
 * @Version: 1.0
 */
@Service
@Slf4j
public class WebsiteNoticeServiceImpl implements WebsiteNoticeService {
    @Autowired
    private WebsiteNoticeMapper websiteNoticeMapper;
    @Autowired
    private UserService userService;
    /**
     * 首页轮播通知
     *
     * @param type
     * @return
     */
    @Override
    public ReturnClass getIndexWebsiteNotice(Short type) {
        WebsiteNotice params=new WebsiteNotice();
        params.setType(type);
        params.setState(true);
        params.setIsIndex(true);
        List<WebsiteNotice> websiteNotices = websiteNoticeMapper.websiteNoticeList(params);
        return ReturnClass.success(websiteNotices);
    }

    /**
     * 首页轮播通知
     *
     * @param id
     * @return
     */
    @Override
    public ReturnClass<WebsiteNotice> getWebsiteNoticeDetail(Long id) {
        WebsiteNotice websiteNotice = websiteNoticeMapper.websiteNoticeById(id);
        if (Objects.isNull(websiteNotice)){
            throw new VLogException(ResultEnum.NO_URL);
        }
        return ReturnClass.success(websiteNotice);
    }

    /**
     * 通知列表
     *
     * @param websiteNoticeDTO
     * @return
     */
    @Override
    public ReturnClass getIndexWebsiteList(WebsiteNoticeDTO websiteNoticeDTO) {
        WebsiteNotice params=new WebsiteNotice();
        BeanUtils.copyProperties(websiteNoticeDTO,params);
        Integer integer = websiteNoticeMapper.websiteNoticeListCount(params);
        if (integer < 1) {
            return ReturnClass.fail(WebsiteNoticeConstant.NO_DATA);
        }
        PageHelper.startPage(websiteNoticeDTO.getCurrentPage(), websiteNoticeDTO.getPageSize());
        List<WebsiteNotice> websiteNotices = websiteNoticeMapper.websiteNoticeList(params);
        PageBean<WebsiteNotice> pageBean = new PageBean<>(websiteNotices, websiteNoticeDTO.getCurrentPage(), websiteNoticeDTO.getPageSize(), integer);
        return ReturnClass.success(pageBean);
    }

    /**
     * 新增或更新
     *
     * @param websiteNoticeDTO
     * @return
     */
    @Override
    public ReturnClass saveOrUpdate(WebsiteNoticeDTO websiteNoticeDTO) {
        String userName=userService.getUserNameByLoginToken();
        if (Objects.isNull(websiteNoticeDTO.getId())){
            WebsiteNotice params=new WebsiteNotice();
            BeanUtils.copyProperties(websiteNoticeDTO,params);
            params.setUserName(userName);
            params.setCreateTime(new Date());
            params.setUpdateTime(new Date());
            int i = websiteNoticeMapper.insertSelective(params);
            if (i>0){
                return ReturnClass.success(params.getId());
            }
        }else {
            WebsiteNotice websiteNotice = websiteNoticeMapper.websiteNoticeById(websiteNoticeDTO.getId());
            if (Objects.isNull(websiteNotice)){
                throw new VLogException(ResultEnum.NO_DATA);
            }
            if (!userName.equals(websiteNotice.getUserName())){
                throw new VLogException(ResultEnum.NO_AUTH);
            }
            BeanUtils.copyProperties(websiteNoticeDTO,websiteNotice);
            websiteNotice.setUpdateTime(new Date());
            int i =websiteNoticeMapper.update(websiteNotice);
            if (i>0){
                return ReturnClass.success(websiteNoticeDTO.getId());
            }
        }
        return ReturnClass.fail(WebsiteNoticeConstant.SAVE_FAIL);
    }

    /**
     * 增加阅读数
     * @param id
     */
    @Override
    public void addReadCount(Long id) {
        Integer integer = websiteNoticeMapper.addReadCount(id);
        if (integer>0){
            log.info("");
        }
    }
}
