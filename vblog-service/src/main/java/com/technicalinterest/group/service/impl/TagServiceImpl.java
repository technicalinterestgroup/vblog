package com.technicalinterest.group.service.impl;

import com.technicalinterest.group.dao.Category;
import com.technicalinterest.group.dao.Tag;
import com.technicalinterest.group.dto.TagDTO;
import com.technicalinterest.group.mapper.TagMapper;
import com.technicalinterest.group.service.TagService;
import com.technicalinterest.group.service.UserService;
import com.technicalinterest.group.service.constant.Constant;
import com.technicalinterest.group.service.constant.ResultEnum;
import com.technicalinterest.group.service.dto.EditTagDTO;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.dto.UserDTO;
import com.technicalinterest.group.service.exception.VLogException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class TagServiceImpl implements TagService {

	@Autowired
	private TagMapper tagMapper;

	@Autowired
	private UserService userService;

	@Override
	public ReturnClass insertSelective(EditTagDTO pojo) {
		Tag tag = new Tag();
		BeanUtils.copyProperties(pojo, tag);
		ReturnClass userByToken = userService.getUserByToken();
		if (userByToken.isSuccess()) {
			UserDTO userDTO = (UserDTO) userByToken.getData();
			tag.setUserName(userDTO.getUserName());
		} else {
			throw new VLogException(ResultEnum.USERINFO_ERROR);
		}
		//名称是否重复
		Tag tag1 = tagMapper.queryTag(Tag.builder().name(tag.getName()).userName(tag.getUserName()).build());
		if (Objects.nonNull(tag1)) {
			return ReturnClass.fail(Constant.TAG_REPEAT);
		}
		Integer flag = tagMapper.insertSelective(tag);
		if (flag > 0) {
			return ReturnClass.success();
		}
		return ReturnClass.fail();
	}

	@Override
	public ReturnClass update(EditTagDTO pojo) {
		Tag tag = new Tag();
		tag.setId(pojo.getId());
		//数据是否存在
		Tag tag1 = tagMapper.queryTag(tag);
		if (Objects.isNull(tag1)) {
			throw new VLogException(ResultEnum.NO_DATA);
		}
		//名称是否重复
		BeanUtils.copyProperties(pojo, tag);
		Tag tag2 = tagMapper.queryTag(Tag.builder().name(tag.getName()).userName(tag.getUserName()).build());
		if (Objects.nonNull(tag2)) {
			if (!StringUtils.equals(tag2.getName(), pojo.getName())) {
				return ReturnClass.fail(Constant.TAG_REPEAT);
			}
		}

		ReturnClass userByToken = userService.getUserByToken();
		if (userByToken.isSuccess()) {
			UserDTO userDTO = (UserDTO) userByToken.getData();
			tag.setUserName(userDTO.getUserName());
		} else {
			throw new VLogException(ResultEnum.USERINFO_ERROR);
		}

		//是否是本人操作
		if (!StringUtils.equals(tag1.getUserName(), tag.getUserName())) {
			throw new VLogException(ResultEnum.NO_AUTH);
		}
		Integer flag = tagMapper.update(tag);
		if (flag > 0) {
			return ReturnClass.success();
		}
		return ReturnClass.fail();
	}

	/**
	 * @Description:文章标签
	 * @author: shuyu.wang
	 * @date: 2019-08-15 13:00
	 * @param authCheck
	 * @param userName
	 * @return com.technicalinterest.group.service.dto.ReturnClass
	 */
	@Override
	public ReturnClass listTagByUser(Boolean authCheck, String userName) {
		ReturnClass returnClass = userService.getUserByuserName(authCheck, userName);
		if (!returnClass.isSuccess()) {
			throw new VLogException(ResultEnum.NO_URL);
		}
		List<TagDTO> tagDTOS = tagMapper.queryTagListByUser(userName);
		if (tagDTOS.isEmpty()) {
			return ReturnClass.fail(ResultEnum.NO_DATA.getMsg());
		}
		return ReturnClass.success(tagDTOS);
	}

	@Override
	public ReturnClass delTag(Long id) {
		Tag tag = new Tag();
		tag.setId(id);
		//数据是否存在
		Tag tag1 = tagMapper.queryTag(tag);
		if (Objects.isNull(tag1)) {
			throw new VLogException(ResultEnum.NO_DATA);
		}

		ReturnClass userByToken = userService.getUserByToken();
		if (userByToken.isSuccess()) {
			UserDTO userDTO = (UserDTO) userByToken.getData();
			tag.setUserName(userDTO.getUserName());
		} else {
			throw new VLogException(ResultEnum.USERINFO_ERROR);
		}

		//是否是本人操作
		if (!StringUtils.equals(tag1.getUserName(), tag.getUserName())) {
			throw new VLogException(ResultEnum.NO_AUTH);
		}
		Integer integer = tagMapper.delTag(id);
		if (integer > 0) {
			return ReturnClass.success();
		}
		return ReturnClass.fail();
	}
}
