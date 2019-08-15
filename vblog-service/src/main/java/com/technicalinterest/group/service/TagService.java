package com.technicalinterest.group.service;

import com.technicalinterest.group.service.dto.EditTagDTO;
import com.technicalinterest.group.service.dto.ReturnClass;

public interface TagService{


    ReturnClass insertSelective(EditTagDTO pojo);


    ReturnClass update(EditTagDTO pojo);

    ReturnClass listTagByUser(Boolean authCheck,String userName);
}
