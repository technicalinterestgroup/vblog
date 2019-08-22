package com.technicalinterest.group.mapper;

import com.technicalinterest.group.dao.Collection;
import com.technicalinterest.group.dto.CollectionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CollectionMapper {
    Integer insert(@Param("pojo") Collection pojo);

    Integer insertSelective(@Param("pojo") Collection pojo);

    Integer insertList(@Param("pojos") List<Collection> pojo);

    Integer update(@Param("pojo") Collection pojo);

    Collection queryCollection(Collection pojo);


    /**
     * @Description: 根据用户名查询收藏文章列表
     * @author: shuyu.wang
     * @date: 2019-08-21 13:29
     * @param userName
     * @return null
    */
    List<CollectionDTO> queryListCollectionByUserName(@Param("userName")String userName);

    /**
     * @Description: 根据用户名查询收藏文章数量
     * @author: shuyu.wang
     * @date: 2019-08-21 13:29
     * @param userName
     * @return null
     */
    Integer queryCountCollectionByUserName(@Param("userName")String userName);

    Integer delCollection(@Param("id")Long id);
}
