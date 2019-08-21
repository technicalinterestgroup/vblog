package com.technicalinterest.group.service.impl;

import com.technicalinterest.group.dao.Collection;
import com.technicalinterest.group.mapper.CollectionMapper;
import com.technicalinterest.group.service.CollectionService;
import com.technicalinterest.group.service.UserService;
import com.technicalinterest.group.service.constant.CollectionConstant;
import com.technicalinterest.group.service.constant.ResultEnum;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.dto.UserDTO;
import com.technicalinterest.group.service.exception.VLogException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangshuyu
 */
@Service
public class CollectionServiceImpl implements CollectionService {
	@Autowired
	private CollectionMapper collectionMapper;
	@Autowired
	private UserService userService;

	@Override
	public ReturnClass insert(Long articleId) {
		ReturnClass userByToken = userService.getUserByToken();
		if (!userByToken.isSuccess()) {
			throw new VLogException(ResultEnum.USERINFO_ERROR);
		}
		UserDTO userDTO = (UserDTO) userByToken.getData();
		Collection collection = Collection.builder().articleId(articleId).userName(userDTO.getUserName()).build();
		int insert = collectionMapper.insert(collection);
		if (insert > 0) {
			return ReturnClass.success(CollectionConstant.SUS_ADD);
		}
		return ReturnClass.fail(CollectionConstant.FAIL_ADD);
	}

	@Override
	public ReturnClass del(Long id) {
		return null;
	}
}
