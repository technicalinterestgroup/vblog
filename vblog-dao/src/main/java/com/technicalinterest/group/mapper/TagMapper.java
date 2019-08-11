package com.technicalinterest.group.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.technicalinterest.group.dao.Tag;

@Mapper
public interface TagMapper {
    int insert(@Param("pojo") Tag pojo);

    int insertSelective(@Param("pojo") Tag pojo);

    int insertList(@Param("pojos") List<Tag> pojo);

    int update(@Param("pojo") Tag pojo);
}
