package com.technicalinterest.group.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.technicalinterest.group.dao.Content;

@Mapper
public interface ContentMapper {
    int insert(@Param("pojo") Content pojo);

    int insertSelective(@Param("pojo") Content pojo);

    int insertList(@Param("pojos") List<Content> pojo);

    int update(@Param("pojo") Content pojo);
}
