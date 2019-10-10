package com.technicalinterest.group.mapper;

import com.technicalinterest.group.dto.DicDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.technicalinterest.group.dao.Dic;

@Mapper
public interface DicMapper {

    Integer insert(@Param("pojo") Dic pojo);

    Integer insertSelective(@Param("pojo") Dic pojo);

    Integer insertList(@Param("pojos") List<Dic> pojo);

    Integer update(@Param("pojo") Dic pojo);

    List<DicDTO> dicList(@Param("pojo") Dic pojo);
}
