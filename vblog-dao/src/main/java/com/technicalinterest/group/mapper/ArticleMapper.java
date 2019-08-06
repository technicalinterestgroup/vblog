package com.technicalinterest.group.mapper;

import com.technicalinterest.group.dao.ArticleContentDTO;
import com.technicalinterest.group.dao.QueryArticleDTO;
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

    
    /**
     * @Description: 列表+摘要
     * @author: shuyu.wang
     * @date: 2019-08-05 13:25
     * @param queryArticleDTO
     * @return null
    */
    List<ArticleContentDTO> listArticle(@Param("pojo")QueryArticleDTO queryArticleDTO);


    /**
     * @Description: 条数
     * @author: shuyu.wang
     * @date: 2019-08-05 13:25
     * @param queryArticleDTO
     * @return null
     */
    Integer listArticleCount(@Param("pojo")QueryArticleDTO queryArticleDTO);


}
