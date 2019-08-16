package com.technicalinterest.group.mapper;

import com.technicalinterest.group.dto.TagDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.technicalinterest.group.dao.Tag;

@Mapper
public interface TagMapper {

    Integer insertSelective(@Param("pojo") Tag pojo);

    Integer update(@Param("pojo") Tag pojo);

    List<TagDTO> queryTagListByUser(String userName);

    Tag queryTag(Tag pojo);

    Integer delTag(@Param("id")Long id);

}
