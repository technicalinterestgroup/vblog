package com.technicalinterest.group.mapper;

import com.technicalinterest.group.dto.AskDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.technicalinterest.group.dao.Ask;

@Mapper
public interface AskMapper {
    int insert(@Param("pojo") Ask pojo);

    int insertSelective(@Param("pojo") Ask pojo);

    int insertList(@Param("pojos") List<Ask> pojo);

    int update(@Param("pojo") Ask pojo);

    Ask getAsk(@Param("pojo") Ask pojo);

    Ask getAskById(@Param("id") Long id);

    List<AskDTO> getAskList(@Param("pojo") Ask pojo);

    Integer getAskListCount(@Param("pojo") Ask pojo);

    int updateReadCount(@Param("id") Long id);

    int updateReplayCount(@Param("id") Long id);
}
