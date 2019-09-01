package com.technicalinterest.group.mapper;

import com.technicalinterest.group.dto.QueryLogDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.technicalinterest.group.dao.Log;

@Mapper
public interface LogMapper {
    int insert(@Param("pojo") Log pojo);

    int insertSelective(@Param("pojo") Log pojo);

    int insertList(@Param("pojos") List<Log> pojo);

    int update(@Param("pojo") Log pojo);

    List<Log> allLogList(@Param("pojo") QueryLogDTO pojo);

    Integer allLogCount(@Param("pojo") QueryLogDTO pojo);
}
