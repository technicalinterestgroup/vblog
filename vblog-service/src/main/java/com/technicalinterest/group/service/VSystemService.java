package com.technicalinterest.group.service;

import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.dto.VSystemDTO;

public interface VSystemService {

	ReturnClass insertSelective(VSystemDTO pojo);

	ReturnClass update(VSystemDTO pojo);

	ReturnClass getSystemByUser(String userName);

	ReturnClass getSystemByLogin();
}
