package com.technicalinterest.group.mapper;

import com.technicalinterest.group.dto.CategoryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.technicalinterest.group.dao.Category;

@Mapper
public interface CategoryMapper {

    Integer insertSelective(@Param("pojo") Category pojo);

    Integer update(@Param("pojo") Category pojo);

    List<CategoryDTO> queryCategoryListByUser(@Param("userName")String userName,@Param("name")String name);

    Category queryCategory(Category pojo);

    Integer delCategory(@Param("id")Long id);

    /**
     * @Description: 该分类下的博客数量
     * @author: shuyu.wang
     * @date: 2019/8/20 22:17
     * @param id
     * @return null
    */
    Integer getArticleCountCategory(@Param("id")Long id);

    /**
     * 查询用户的标签分类
     * @param userName
     * @return
     */
    List<CategoryDTO> queryCategorysByUser(String userName);
}
