package com.technicalinterest.group.service.impl;

import com.github.pagehelper.PageHelper;
import com.technicalinterest.group.dao.Collection;
import com.technicalinterest.group.dao.PageBase;
import com.technicalinterest.group.dto.ArticlesDTO;
import com.technicalinterest.group.dto.CollectionDTO;
import com.technicalinterest.group.mapper.ArticleMapper;
import com.technicalinterest.group.mapper.CollectionMapper;
import com.technicalinterest.group.service.CollectionService;
import com.technicalinterest.group.service.UserService;
import com.technicalinterest.group.service.constant.CollectionConstant;
import com.technicalinterest.group.service.constant.ResultEnum;
import com.technicalinterest.group.service.dto.PageBean;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.dto.UserDTO;
import com.technicalinterest.group.service.exception.VLogException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author wangshuyu
 */
@Service
public class CollectionServiceImpl implements CollectionService {
	@Autowired
	private CollectionMapper collectionMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private ArticleMapper articleMapper;

	@Override
	public ReturnClass insert(Long articleId) {
		ReturnClass userByToken = userService.getUserByToken();
		if (!userByToken.isSuccess()) {
			throw new VLogException(ResultEnum.USERINFO_ERROR);
		}
		ArticlesDTO articleInfo = articleMapper.getArticleInfo(articleId);
		if (Objects.isNull(articleInfo)){
			throw new VLogException(ResultEnum.NO_URL);
		}
		Collection collection2 = collectionMapper.queryById(null,articleId);
		UserDTO userDTO = (UserDTO) userByToken.getData();
		if (Objects.nonNull(collection2)){
			if (StringUtils.equals(collection2.getUserName(),userDTO.getUserName())){
				return ReturnClass.success(CollectionConstant.ADD_REPAT);
			}
		}
		Collection collection = Collection.builder().articleId(articleId).userName(userDTO.getUserName()).build();
		int insert = collectionMapper.insert(collection);
		if (insert > 0) {
			return ReturnClass.success(CollectionConstant.SUS_ADD);
		}
		return ReturnClass.fail(CollectionConstant.FAIL_ADD);
	}

	@Override
	public ReturnClass del(Long id) {
		Collection collection = collectionMapper.queryById(id,null);
		if (Objects.isNull(collection)){
			throw new VLogException(ResultEnum.NO_URL);
		}
		ReturnClass returnClass = userService.userNameIsLoginUser(collection.getUserName());
		if (!returnClass.isSuccess()){
			throw new VLogException(ResultEnum.NO_AUTH);
		}
		Integer integer = collectionMapper.delCollection(id);
		if (integer>0){
			return ReturnClass.success(CollectionConstant.SUS_DEL);
		}
		return ReturnClass.success(CollectionConstant.FAIL_DEL);
	}
	/**
	 * @Description:查询收藏列表
	 * @author: shuyu.wang
	 * @date: 2019/8/21 22:50
	 * @param
	 * @return com.technicalinterest.group.service.dto.ReturnClass
	 */
	@Override
	public ReturnClass queryListCollection(String userName,PageBase pageBase) {
		ReturnClass returnClass = userService.getUserByuserName(true,userName);
		if (!returnClass.isSuccess()){
			throw new VLogException(ResultEnum.NO_URL);
		}
		Integer integer = collectionMapper.queryCountCollectionByUserName(userName);
		if (integer>0){
			PageHelper.startPage(pageBase.getPageNum(), pageBase.getPageSize());
			List<CollectionDTO> collectionDTOS = collectionMapper.queryListCollectionByUserName(userName);
			PageBean<CollectionDTO> pageBean = new PageBean<>(collectionDTOS, pageBase.getPageNum(), pageBase.getPageSize(), integer);
			return ReturnClass.success(pageBean);
		}
		return ReturnClass.fail(CollectionConstant.NO_DATA);
	}
}
