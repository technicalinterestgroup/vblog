package com.technicalinterest.group.service.impl;

import com.technicalinterest.group.dao.Tag;
import com.technicalinterest.group.dto.TagDTO;
import com.technicalinterest.group.mapper.TagMapper;
import com.technicalinterest.group.service.TagService;
import com.technicalinterest.group.service.UserService;
import com.technicalinterest.group.service.Enum.ResultEnum;
import com.technicalinterest.group.service.constant.TagConstant;
import com.technicalinterest.group.service.dto.EditTagDTO;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.exception.VLogException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		tag.setUserName(userService.getUserNameByLoginToken());
		//名称是否重复
		Tag tag1 = tagMapper.queryTag(Tag.builder().name(tag.getName()).userName(tag.getUserName()).build());
		if (Objects.nonNull(tag1)) {
			return ReturnClass.fail(TagConstant.TAG_REPEAT);
		}
		Integer flag = tagMapper.insertSelective(tag);
		if (flag > 0) {
			return ReturnClass.success(TagConstant.SUS_ADD);
		}
		return ReturnClass.fail(TagConstant.FAIL_ADD);
	}

	@Override
	public ReturnClass update(EditTagDTO pojo) {
		Tag tag = new Tag();
		tag.setId(pojo.getId());
		tag.setUserName(userService.getUserNameByLoginToken());
		//数据是否存在
		Tag tag1 = tagMapper.queryTag(tag);
		if (Objects.isNull(tag1)) {
			throw new VLogException(ResultEnum.NO_DATA);
		}
		//是否是本人操作
		if (!StringUtils.equals(tag1.getUserName(), tag.getUserName())) {
			throw new VLogException(ResultEnum.NO_AUTH);
		}
		//名称是否重复
		BeanUtils.copyProperties(pojo, tag);
		Tag tag2 = tagMapper.queryTag(Tag.builder().name(tag.getName()).userName(tag.getUserName()).build());
		if (Objects.nonNull(tag2)) {
			if (!StringUtils.equals(tag2.getName(), pojo.getName())) {
				return ReturnClass.fail(TagConstant.TAG_REPEAT);
			}
		}

		Integer flag = tagMapper.update(tag);
		if (flag > 0) {
			return ReturnClass.success(TagConstant.SUS_EDITE);
		}
		return ReturnClass.fail(TagConstant.FAIL_EDITE);
	}

	/**
	 * @Description:文章标签
	 * @author: shuyu.wang
	 * @date: 2019-08-15 13:00
	 * @param name
	 * @return com.technicalinterest.group.service.dto.ReturnClass
	 */
	@Override
	public ReturnClass listTagByAdmin(String name) {
		List<TagDTO> tagDTOS = tagMapper.queryTagListByUser(null,name);
		if (tagDTOS.isEmpty()) {
			return ReturnClass.fail(TagConstant.NO_DATA);
		}
		return ReturnClass.success(tagDTOS);
	}

	@Override
	public ReturnClass delTag(Long id) {
		Tag tag = new Tag();
		tag.setId(id);
		tag.setUserName(userService.getUserNameByLoginToken());
		//数据是否存在
		Tag tag1 = tagMapper.queryTag(tag);
		if (Objects.isNull(tag1)) {
			throw new VLogException(ResultEnum.NO_DATA);
		}
		//是否是本人操作
		if (!StringUtils.equals(tag1.getUserName(), tag.getUserName())) {
			throw new VLogException(ResultEnum.NO_AUTH);
		}
		Integer articleCountTag = tagMapper.getArticleCountTag(id);
		if (articleCountTag>0){
			return ReturnClass.fail(TagConstant.HAVA_ARTICLE);
		}
		Integer integer = tagMapper.delTag(id);
		if (integer > 0) {
			return ReturnClass.success(TagConstant.SUS_DEL);
		}
		return ReturnClass.fail(TagConstant.FAIL_DEL);
	}

	/**
	 * 查询所以标签
	 *
	 * @return
	 */
	@Override
	public ReturnClass allTagList(String userName) {
		List<TagDTO> tagDTOS = tagMapper.allTagList(userName);
		return ReturnClass.success(tagDTOS);
	}

	@Override
	public ReturnClass allTagListDic() {
		List<TagDTO> tagDTOS = tagMapper.allTagListDic();
		return ReturnClass.success(tagDTOS);
	}
}
