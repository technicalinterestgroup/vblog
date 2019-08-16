package com.technicalinterest.group.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.technicalinterest.group.dao.Content;

@Mapper
public interface ContentMapper {
    Integer insert(@Param("pojo") Content pojo);

    Integer insertSelective(@Param("pojo") Content pojo);

    Integer insertList(@Param("pojos") List<Content> pojo);

    Integer update(@Param("pojo") Content pojo);

    Content getContent(@Param("articleId")Long articleId);
}
