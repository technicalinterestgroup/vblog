package com.technicalinterest.group.mapper;

import com.technicalinterest.group.dto.TreeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.technicalinterest.group.dao.Auth;

@Mapper
public interface AuthMapper {
    int insert(@Param("pojo") Auth pojo);

    int insertSelective(@Param("pojo") Auth pojo);

    int insertList(@Param("pojos") List<Auth> pojo);

    int update(@Param("pojo") Auth pojo);

    /**
     * 查询树
     * @param type
     * @return
     */
    List<TreeDTO>  getTree(@Param("type")Short type,@Param("parentId")Long parentId);

    /**
     * 查询列表
     * @param pojo
     * @return
     */
    List<Auth> getAuthList(@Param("pojo") Auth pojo);
    /**
     * 查询一个权限
     * @param pojo
     * @return
     */
    Auth getAuth(@Param("pojo") Auth pojo);
}
