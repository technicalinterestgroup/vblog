package com.technicalinterest.group.service;

import com.technicalinterest.group.dao.Like;
import com.technicalinterest.group.service.dto.LikeDTO;
import com.technicalinterest.group.service.dto.ReturnClass;

public interface LikeService{


    /**
     * @Description: 添加点赞
     * @author: shuyu.wang
     * @date: 2019/8/22 22:36
     * @param pojo
     * @return null
    */
    ReturnClass insert(LikeDTO pojo);

    /**
     * @Description: 取消点赞
     * @author: shuyu.wang
     * @date: 2019/8/22 22:36
     * @param pojo
     * @return null
     */
    ReturnClass del(LikeDTO pojo);
}
