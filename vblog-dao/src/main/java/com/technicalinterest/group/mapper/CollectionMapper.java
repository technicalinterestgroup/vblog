package com.technicalinterest.group.mapper;

import com.technicalinterest.group.dao.Collection;
import com.technicalinterest.group.dto.CollectionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CollectionMapper {
    int insert(@Param("pojo") Collection pojo);

    int insertSelective(@Param("pojo") Collection pojo);

    int insertList(@Param("pojos") List<Collection> pojo);

    int update(@Param("pojo") Collection pojo);

    Collection queryById(@Param("id")Long id);


    /**
     * @Description: 根据用户名查询收藏文章列表
     * @author: shuyu.wang
     * @date: 2019-08-21 13:29
     * @param userName
     * @return null
    */
    List<CollectionDTO> queryListCollectionByUserName(@Param("userName")Long userName);

    /**
     * @Description: 根据用户名查询收藏文章数量
     * @author: shuyu.wang
     * @date: 2019-08-21 13:29
     * @param userName
     * @return null
     */
    Integer queryCountCollectionByUserName(@Param("userName")Long userName);
}
