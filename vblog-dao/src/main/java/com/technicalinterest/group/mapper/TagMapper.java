package com.technicalinterest.group.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.technicalinterest.group.dao.Tag;

@Mapper
public interface TagMapper {
    Integer insert(@Param("pojo") Tag pojo);

    Integer insertSelective(@Param("pojo") Tag pojo);

    Integer insertList(@Param("pojos") List<Tag> pojo);

    Integer update(@Param("pojo") Tag pojo);
}
