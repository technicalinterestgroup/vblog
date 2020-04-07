package com.technicalinterest.group.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.technicalinterest.group.dao.Reply;

@Mapper
public interface ReplyMapper {
    int insert(@Param("pojo") Reply pojo);

    int insertSelective(@Param("pojo") Reply pojo);

    int insertList(@Param("pojos") List<Reply> pojo);

    int update(@Param("pojo") Reply pojo);

    Reply getOneReply(@Param("id") Long id);

    List<Reply> getReplysByAsk(@Param("askId") Long askId);
}