package com.technicalinterest.group.mapper;

import com.technicalinterest.group.dto.UserFocusDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.technicalinterest.group.dao.UserFocus;

@Mapper
public interface UserFocusMapper {
    int insert(@Param("pojo") UserFocus pojo);

    int insertSelective(@Param("pojo") UserFocus pojo);

    int insertList(@Param("pojos") List<UserFocus> pojo);

    int update(@Param("pojo") UserFocus pojo);

    UserFocus selectOneUserFocus(@Param("pojo") UserFocus pojo);

    /**
     * 查询你关注的用户
     * @param userName
     * @return
     */
    List<UserFocusDTO> selectYourFocus(@Param("userName") String userName);

    /**
     * 查询你的粉丝
     * @param userName
     * @return
     */
    List<UserFocusDTO> selectFocusYou(@Param("userName") String userName);

    Integer selectFocusYouCount(@Param("userName") String userName);

    /**
     * 查询我的关注已经互粉关系
     */
    List<UserFocusDTO> selectMyFocusAndMutual(@Param("userName") String userName);

    /**
     * 查询我的关注已经互粉关系
     */
    Integer selectMyFocusAndMutualCount(@Param("userName") String userName);

}
