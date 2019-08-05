package com.technicalinterest.group.service.impl;

import com.technicalinterest.group.dao.Article;
import com.technicalinterest.group.dao.Content;
import com.technicalinterest.group.dao.QueryArticleDTO;
import com.technicalinterest.group.mapper.ArticleMapper;
import com.technicalinterest.group.mapper.ContentMapper;
import com.technicalinterest.group.service.ArticleService;
import com.technicalinterest.group.service.UserService;
import com.technicalinterest.group.service.constant.ArticleConstant;
import com.technicalinterest.group.service.constant.UserConstant;
import com.technicalinterest.group.service.dto.ArticleContentDTO;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.dto.UserDTO;
import com.technicalinterest.group.service.exception.VLogException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @package: com.technicalinterest.group.service.impl
 * @className: ArticleServiceImpl
 * @description: 文章服务层接口实现
 * @author: Shuyu.Wang
 * @date: 2019-08-04 15:06
 * @since: 0.1
 **/
@Service
public class ArticleServiceImpl implements ArticleService {
	@Autowired
	private ArticleMapper articleMapper;
	@Autowired
	private ContentMapper contentMapper;
	@Autowired
	private UserService userService;

	/**
	 * @Description: 新增文章
	 * @author: shuyu.wang
	 * @date: 2019-08-04 15:12
	 * @param articleContentDTO
	 * @return ReturnClass
	 */
	@Override
	@Transactional
	public ReturnClass saveArticle(ArticleContentDTO articleContentDTO) {
		Article article = new Article();
		BeanUtils.copyProperties(articleContentDTO, article);
		ReturnClass userByToken = userService.getUserByToken();
		if (userByToken.isSuccess()) {
			UserDTO userDTO = (UserDTO) userByToken.getData();
			article.setUserName(userDTO.getUserName());
		} else {
			throw new VLogException(-1, UserConstant.FAILD_GET_USER_INFO);
		}
		article.setSubmit(articleContentDTO.getContent().length() > 50 ? articleContentDTO.getContent().substring(0, 49) : articleContentDTO.getContent());
		articleMapper.insertSelective(article);
		if (Objects.nonNull(article.getId()) && article.getId() > 0) {
			Content content = new Content();
			content.setArticleId(article.getId());
			content.setContent(articleContentDTO.getContent());
			int i = contentMapper.insertSelective(content);
			if (i < 1) {
				throw new VLogException(-1, ArticleConstant.FAIL_ADD);
			}
			return ReturnClass.success(ArticleConstant.SUS_ADD);
		}
		return ReturnClass.fail(ArticleConstant.FAIL_ADD);
	}

	/**
	 * @Description: 更新文章
	 * @author: shuyu.wang
	 * @date: 2019-08-04 15:12
	 * @param articleContentDTO
	 * @return ReturnClass
	 */
	@Override
	public ReturnClass editArticle(ArticleContentDTO articleContentDTO) {
		Article article = new Article();
		BeanUtils.copyProperties(articleContentDTO, article);
		ReturnClass userByToken = userService.getUserByToken();
		if (!userByToken.isSuccess()) {
			throw new VLogException(-1, UserConstant.FAILD_GET_USER_INFO);
		}
		article.setSubmit(articleContentDTO.getContent().length() > 50 ? articleContentDTO.getContent().substring(0, 49) : articleContentDTO.getContent());
		articleMapper.update(article);
		if (Objects.nonNull(article.getId()) && article.getId() > 0) {
			Content content = new Content();
			content.setArticleId(article.getId());
			content.setContent(articleContentDTO.getContent());
			int i = contentMapper.update(content);
			if (i < 1) {
				throw new VLogException(-1, ArticleConstant.FAIL_EDIT);
			}
			return ReturnClass.success(ArticleConstant.SUS_EDIT);
		}
		return ReturnClass.fail(ArticleConstant.FAIL_EDIT);
	}

	/**
	 * @Description: 文章列表
	 * @author: shuyu.wang
	 * @date: 2019-08-04 15:12
	 * @param queryArticleDTO
	 * @return ReturnClass
	 */
	@Override
	public ReturnClass listArticle(QueryArticleDTO queryArticleDTO) {
		return null;
	}
}
