package com.technicalinterest.group.service.impl;

import com.github.pagehelper.PageHelper;
import com.technicalinterest.group.dao.Ask;
import com.technicalinterest.group.dto.ArticlesDTO;
import com.technicalinterest.group.mapper.AskMapper;
import com.technicalinterest.group.service.AskService;
import com.technicalinterest.group.service.Enum.ResultEnum;
import com.technicalinterest.group.service.UserService;
import com.technicalinterest.group.service.dto.AskDTO;
import com.technicalinterest.group.service.dto.PageBean;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.exception.VLogException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName: AskServiceImpl
 * @Author: shuyu.wang
 * @Description:
 * @Date: 2020/3/31 16:28
 * @Version: 1.0
 */
@Service
@Slf4j
public class AskServiceImpl implements AskService {
    @Autowired
    private AskMapper askMapper;
    @Autowired
    private UserService userService;

    /**
     * 新增或编辑
     *
     * @param ask
     * @return
     */
    @Override
    public ReturnClass saveOrUpdateAsk(Ask ask) {
        String userName=userService.getUserNameByLoginToken();
        int flag=0;
        if (Objects.isNull(ask.getId())){
            ask.setState((short)0);
            ask.setUserName(userName);
            ask.setCreateTime(new Date());
            ask.setUpdateTime(new Date());
            ask.setIsDel((short)0);
            flag=askMapper.insertSelective(ask);
        }else {
            Ask askById = askMapper.getAskById(ask.getId());
            if (Objects.isNull(askById)){
                throw new VLogException(ResultEnum.NO_DATA);
            }
            if (!askById.getUserName().equals(ask.getUserName())){
                throw new VLogException(ResultEnum.NO_AUTH);
            }
            ask.setUpdateTime(new Date());
            flag=askMapper.update(ask);
        }
        if (flag>0){
            return ReturnClass.success();
        }
        return ReturnClass.fail();
    }

    @Override
    public ReturnClass<PageBean<com.technicalinterest.group.dto.AskDTO>> getAskPage(AskDTO askDTO) {
        Ask query=new Ask();
        BeanUtils.copyProperties(askDTO,query);
        Integer askListCount = askMapper.getAskListCount(query);
        if (askListCount<1){
            return ReturnClass.fail(ResultEnum.NO_DATA.getMsg());
        }
        PageHelper.startPage(askDTO.getCurrentPage(), askDTO.getPageSize());
        List<com.technicalinterest.group.dto.AskDTO> askList = askMapper.getAskList(query);
        PageBean<com.technicalinterest.group.dto.AskDTO> pageBean = new PageBean<>(askList, askDTO.getCurrentPage(), askDTO.getPageSize(), askListCount);
        return ReturnClass.success(pageBean);
    }

    @Override
    public ReturnClass<PageBean<com.technicalinterest.group.dto.AskDTO>> getAskPageByToken(AskDTO askDTO) {
        Ask query=new Ask();
        BeanUtils.copyProperties(askDTO,query);
        query.setUserName(userService.getUserNameByLoginToken());
        Integer askListCount = askMapper.getAskListCount(query);
        if (askListCount<1){
            return ReturnClass.fail(ResultEnum.NO_DATA.getMsg());
        }
        PageHelper.startPage(askDTO.getCurrentPage(), askDTO.getPageSize());
        List<com.technicalinterest.group.dto.AskDTO> askList = askMapper.getAskList(query);
        PageBean<com.technicalinterest.group.dto.AskDTO> pageBean = new PageBean<>(askList, askDTO.getCurrentPage(), askDTO.getPageSize(), askListCount);
        return ReturnClass.success(pageBean);
    }

    @Override
    public ReturnClass<Ask> getAskDetailById(Long id) {
        Ask askById = askMapper.getAskById(id);
        if (Objects.isNull(askById)){
            return ReturnClass.fail(ResultEnum.NO_DATA.getMsg());
        }
        return ReturnClass.success(askById);
    }

    @Override
    public ReturnClass<Ask> getAskDetailByToken(Long id) {
        String userNameByLoginToken = userService.getUserNameByLoginToken();
        Ask query=new Ask();
        query.setId(id);
        query.setUserName(userNameByLoginToken);
        Ask askById = askMapper.getAsk(query);
        if (Objects.isNull(askById)){
            return ReturnClass.fail(ResultEnum.NO_DATA.getMsg());
        }
        return ReturnClass.success(askById);
    }

    @Override
    public ReturnClass<String> updateState(Ask askDTO) {
        Ask askById = askMapper.getAskById(askDTO.getId());
        if (Objects.isNull(askById)){
            throw new VLogException(ResultEnum.NO_DATA);
        }
        if (!askById.getUserName().equals(askDTO.getUserName())){
            throw new VLogException(ResultEnum.NO_AUTH);
        }
        if (askById.getState()==1){
            throw new VLogException("已经采纳过答案，不能重复操作！");
        }
        int update = askMapper.update(askDTO);
        if (update>0){
            return ReturnClass.success();
        }
        return ReturnClass.fail();
    }

    @Override
    @Async("vblog")
    public void updateReadCount(Long id) {
        int i = askMapper.updateReadCount(id);
        if (i>0){
            log.info("阅读数增加成功");
        }
        log.error("阅读数增加失败,id={}",id);
    }

    /**
     * 增加回答数
     *
     * @param id
     */
    @Override
    @Async("vblog")
    public void updateReplayCount(Long id) {
        askMapper.updateReplayCount(id);
    }
}
