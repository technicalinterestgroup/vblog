package com.technicalinterest.group.mapper;

import com.technicalinterest.group.dto.WebNoticeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.technicalinterest.group.dao.WebsiteNotice;

@Mapper
public interface WebsiteNoticeMapper {
    int insert(@Param("pojo") WebsiteNotice pojo);

    int insertSelective(@Param("pojo") WebsiteNotice pojo);

    int insertList(@Param("pojos") List<WebsiteNotice> pojo);

    int update(@Param("pojo") WebsiteNotice pojo);

    /**
     * 列表数据
     * @param pojo
     * @return
     */
    List<WebsiteNotice> websiteNoticeList(@Param("pojo") WebsiteNotice pojo);

    /**
     * 列表数据
     * @param pojo
     * @return
     */
    Integer websiteNoticeListCount(@Param("pojo") WebsiteNotice pojo);

    /**
     * 根据id查询详情
     * @param id
     * @return
     */
    WebsiteNotice websiteNoticeById(@Param("id") Long id);

    /**
     * 根据id查询详情
     * @param id
     * @return
     */
    WebNoticeDTO getWebsiteNotice(@Param("id") Long id,@Param("userName") String userName);

    /**
     * 增加阅读数
     * @param id
     * @return
     */
    Integer addReadCount(@Param("id")Long id);
}
