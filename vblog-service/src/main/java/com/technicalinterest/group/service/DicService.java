package com.technicalinterest.group.service;

import com.technicalinterest.group.dao.Dic;
import com.technicalinterest.group.service.dto.ReturnClass;


public interface DicService{


    ReturnClass insert(Dic pojo);

    ReturnClass dicList(Dic pojo);
}
