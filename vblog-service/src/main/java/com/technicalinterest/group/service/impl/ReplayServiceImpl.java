package com.technicalinterest.group.service.impl;

import com.technicalinterest.group.dao.Ask;
import com.technicalinterest.group.dao.Reply;
import com.technicalinterest.group.dto.ReplyDTO;
import com.technicalinterest.group.mapper.AskMapper;
import com.technicalinterest.group.mapper.ReplyMapper;
import com.technicalinterest.group.service.AskService;
import com.technicalinterest.group.service.Enum.ResultEnum;
import com.technicalinterest.group.service.ReplayService;
import com.technicalinterest.group.service.UserService;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.exception.VLogException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName: ReplayServiceImpl
 * @Author: shuyu.wang
 * @Description:
 * @Date: 2020/4/1 12:55
 * @Version: 1.0
 */
@Service
public class ReplayServiceImpl implements ReplayService {
    @Autowired
    private ReplyMapper replyMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private AskService askService;
    @Autowired
    private AskMapper askMapper;

    /**
     * 新增回答
     *
     * @param reply
     * @return
     */
    @Override
    public ReturnClass<String> saveReply(Reply reply) {
        reply.setUserName(userService.getUserNameByLoginToken());
        Ask askById = askMapper.getAskById(reply.getAskId());
        if (Objects.isNull(askById)){
            return ReturnClass.fail("问题不存在！");
        }
        reply.setCreateTime(new Date());
        reply.setUpdateTime(new Date());
        int i = replyMapper.insertSelective(reply);
        if (i>0){
            askById.setReplyCount(1);
            askMapper.update(askById);
            return ReturnClass.success();
        }
        return ReturnClass.fail();
    }

    /**
     * 查询回答列表
     *
     * @param askId
     * @return
     */
    @Override
    public ReturnClass<List<ReplyDTO>> getReplyList(Long askId,String userName) {
        List<ReplyDTO> replysByAsk = replyMapper.getReplysByAsk(askId,userName);
        return ReturnClass.success(replysByAsk);
    }

    /**
     * 采纳答案
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public ReturnClass<String> acceptionReply(Long id) {
        Reply oneReply = replyMapper.getOneReply(id);
        if (Objects.isNull(oneReply)){
            throw new VLogException(ResultEnum.NO_DATA);
        }
        oneReply.setState((short)1);
        oneReply.setUpdateTime(new Date());
        int update = replyMapper.update(oneReply);
        if (update>0){
            Ask ask=new Ask();
            ask.setId(oneReply.getAskId());
            ask.setState((short)1);
            ask.setUserName(userService.getUserNameByLoginToken());
            ReturnClass<String> stringReturnClass = askService.updateState(ask);
            return stringReturnClass;
        }
        return ReturnClass.fail();
    }
}
