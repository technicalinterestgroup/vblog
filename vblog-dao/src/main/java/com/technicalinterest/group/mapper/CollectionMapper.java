package com.technicalinterest.group.mapper;

import com.technicalinterest.group.dao.CollectionArticle;
import com.technicalinterest.group.dto.CollectionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CollectionMapper {
    Integer insert(@Param("pojo") CollectionArticle pojo);

    Integer insertSelective(@Param("pojo") CollectionArticle pojo);

    Integer insertList(@Param("pojos") List<CollectionArticle> pojo);

    Integer update(@Param("pojo") CollectionArticle pojo);

    CollectionArticle queryCollection(CollectionArticle pojo);


    /**
     * @Description: 根据用户名查询收藏文章列表
     * @author: shuyu.wang
     * @date: 2019-08-21 13:29
     * @param userName
     * @return null
    */
    List<CollectionDTO> queryListCollectionByUserName(@Param("type")Short type,@Param("userName")String userName);

    /**
     * @Description: 根据用户名查询收藏文章数量
     * @author: shuyu.wang
     * @date: 2019-08-21 13:29
     * @param userName
     * @return null
     */
    Integer queryCountCollectionByUserName(@Param("type")Short type,@Param("userName")String userName);

    Integer delCollection(@Param("id")Long id);
}
