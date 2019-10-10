package com.technicalinterest.group.service.impl;

import com.github.pagehelper.util.StringUtil;
import com.technicalinterest.group.dao.Dic;
import com.technicalinterest.group.dto.DicDTO;
import com.technicalinterest.group.mapper.DicMapper;
import com.technicalinterest.group.service.DicService;
import com.technicalinterest.group.service.dto.ReturnClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class DicServiceImpl implements DicService {

    @Autowired
    private DicMapper dicMapper;

    @Override
    public ReturnClass insert(Dic pojo) {
        return null;
    }

    @Override
    public ReturnClass dicList(Dic pojo) {
        List<DicDTO> dics = dicMapper.dicList(pojo);
        if (dics.isEmpty()){
            return ReturnClass.fail("没有该数据");
        }
        return ReturnClass.success(dics);
    }
}
