package com.technicalinterest.group.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.technicalinterest.group.dao.Article;

@Mapper
public interface ArticleMapper {
    int insert(@Param("pojo") Article pojo);

    int insertSelective(@Param("pojo") Article pojo);

    int insertList(@Param("pojos") List<Article> pojo);

    int update(@Param("pojo") Article pojo);
}
