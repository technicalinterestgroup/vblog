package com.technicalinterest.group.service.impl;

import com.technicalinterest.group.dao.VSystem;
import com.technicalinterest.group.mapper.VSystemMapper;
import com.technicalinterest.group.service.UserService;
import com.technicalinterest.group.service.VSystemService;
import com.technicalinterest.group.service.constant.ResultEnum;
import com.technicalinterest.group.service.constant.SystemConstant;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.dto.VSystemDTO;
import com.technicalinterest.group.service.exception.VLogException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class VSystemServiceImpl implements VSystemService {

	@Autowired
	private VSystemMapper vSystemMapper;
	@Autowired
	private UserService userService;

	@Override
	@Async
	public ReturnClass insertSelective(VSystemDTO pojo) {
		VSystem vSystem = new VSystem();
		BeanUtils.copyProperties(pojo, vSystem);
		Integer integer = vSystemMapper.insertSelective(vSystem);
		if (integer > 0) {
			return ReturnClass.success();
		}
		return ReturnClass.fail();
	}

	@Override
	public ReturnClass update(VSystemDTO pojo) {
		ReturnClass returnClass = userService.userNameIsLoginUser(pojo.getUserName());
		if (!returnClass.isSuccess()) {
			throw new VLogException(ResultEnum.NO_AUTH);
		}
		VSystem vSystem = new VSystem();
		BeanUtils.copyProperties(pojo, vSystem);
		Integer integer = vSystemMapper.update(vSystem);
		if (integer < 1) {
			return ReturnClass.success(SystemConstant.FAIL_EDITE);
		}
		return ReturnClass.success(SystemConstant.SUS_EDITE);
	}

	/**
	 * @Description:查询配置详情
	 * @author: shuyu.wang
	 * @date: 2019-08-14 13:27
	 * @param authCheck
	 * @param userName
	 * @return com.technicalinterest.group.service.dto.ReturnClass
	 */
	@Override
	public ReturnClass getSystemByUser(Boolean authCheck, String userName) {
		ReturnClass returnClass = userService.getUserByuserName(authCheck, userName);
		if (!returnClass.isSuccess()) {
			throw new VLogException(ResultEnum.NO_URL);
		}
		VSystem systemByUser = vSystemMapper.querySystemByUser(userName);
		VSystemDTO vSystemDTO = new VSystemDTO();
		BeanUtils.copyProperties(systemByUser, vSystemDTO);
		return ReturnClass.success(vSystemDTO);
	}
}
