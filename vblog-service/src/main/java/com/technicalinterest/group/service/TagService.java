package com.technicalinterest.group.service;

import com.technicalinterest.group.dto.TagDTO;
import com.technicalinterest.group.service.dto.EditTagDTO;
import com.technicalinterest.group.service.dto.ReturnClass;

import java.util.List;

public interface TagService{


    ReturnClass insertSelective(EditTagDTO pojo);


    ReturnClass update(EditTagDTO pojo);

    ReturnClass listTagByUser(Boolean authCheck,String userName);

    ReturnClass delTag(Long id);

    /**
     * 查询所以标签
     * @return
     */
    ReturnClass allTagList(String userName);

}
