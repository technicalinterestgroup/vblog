package com.technicalinterest.group.service.impl;

import com.technicalinterest.group.dao.Log;
import com.technicalinterest.group.mapper.LogMapper;
import com.technicalinterest.group.service.LogService;
import com.technicalinterest.group.service.dto.ReturnClass;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LogServiceImpl implements LogService {

    @Resource
    private LogMapper logMapper;

    @Override
    @Async
    public ReturnClass insert(Log pojo) {
        logMapper.insertSelective(pojo);
        return null;
    }
}
