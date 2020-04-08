package com.technicalinterest.group.service;

import com.technicalinterest.group.dao.Reply;
import com.technicalinterest.group.dto.ReplyDTO;
import com.technicalinterest.group.service.dto.ReturnClass;

import java.util.List;

/**
 * @ClassName: ReplayService
 * @Author: shuyu.wang
 * @Description:
 * @Date: 2020/4/1 12:54
 * @Version: 1.0
 */
public interface ReplayService {
    /**
     * 新增回答
     * @param reply
     * @return
     */
    ReturnClass<String> saveReply(Reply reply);

    /**
     * 查询回答列表
     * @param askId
     * @return
     */
    ReturnClass<List<ReplyDTO>> getReplyList(Long askId, String userName);

    /**
     * 采纳答案
     * @param id
     * @return
     */
    ReturnClass<String> acceptionReply(Long id);
}
