package com.technicalinterest.group.service.impl;

import com.technicalinterest.group.dao.Tag;
import com.technicalinterest.group.mapper.TagMapper;
import com.technicalinterest.group.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

	@Autowired
	private TagMapper tagMapper;

	@Override
	public Integer insertSelective(Tag pojo) {
		return tagMapper.insertSelective(pojo);
	}

	@Override
	public Integer update(Tag pojo) {
		return tagMapper.update(pojo);
	}
}
