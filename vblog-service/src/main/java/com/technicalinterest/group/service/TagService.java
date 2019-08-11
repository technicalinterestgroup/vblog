package com.technicalinterest.group.service;

import com.technicalinterest.group.dao.Tag;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

import com.technicalinterest.group.mapper.TagMapper;

public interface TagService{


    Integer insertSelective(Tag pojo);


    Integer update(Tag pojo);
}
