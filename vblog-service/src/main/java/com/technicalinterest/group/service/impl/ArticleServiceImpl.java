package com.technicalinterest.group.service.impl;

import com.github.pagehelper.PageHelper;
import com.technicalinterest.group.dao.Article;
import com.technicalinterest.group.dto.ArticlesDTO;
import com.technicalinterest.group.dao.Content;
import com.technicalinterest.group.dto.QueryArticleDTO;
import com.technicalinterest.group.mapper.ArticleMapper;
import com.technicalinterest.group.mapper.ContentMapper;
import com.technicalinterest.group.service.ArticleService;
import com.technicalinterest.group.service.UserService;
import com.technicalinterest.group.service.constant.ArticleConstant;
import com.technicalinterest.group.service.constant.ResultEnum;
import com.technicalinterest.group.service.context.RequestHeaderContext;
import com.technicalinterest.group.service.dto.ArticleContentDTO;
import com.technicalinterest.group.service.dto.PageBean;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.dto.UserDTO;
import com.technicalinterest.group.service.exception.VLogException;
import com.technicalinterest.group.service.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
	@Autowired
	private RedisUtil redisUtil;

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
			throw new VLogException(ResultEnum.USERINFO_ERROR);
		}
		article.setSubmit(articleContentDTO.getContent().length() > 50 ? articleContentDTO.getContent().substring(0, 49) : articleContentDTO.getContent());
		articleMapper.insertSelective(article);
		if (Objects.nonNull(article.getId()) && article.getId() > 0) {
			Content content = new Content();
			content.setArticleId(article.getId());
			content.setContent(articleContentDTO.getContent());
			int i = contentMapper.insertSelective(content);
			if (i < 1) {
				throw new VLogException(ArticleConstant.FAIL_ADD);
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
			throw new VLogException(ResultEnum.USERINFO_ERROR);
		}
		//判断是否是本人操作
		Article articleInfo = articleMapper.getArticleInfo(articleContentDTO.getId());
		UserDTO userDTO = (UserDTO) userByToken.getData();
		if (!StringUtils.equals(articleInfo.getUserName(), userDTO.getUserName())) {
			throw new VLogException(ResultEnum.NO_AUTH);
		}

		article.setSubmit(articleContentDTO.getContent().length() > 50 ? articleContentDTO.getContent().substring(0, 49) : articleContentDTO.getContent());
		articleMapper.update(article);
		if (Objects.nonNull(article.getId()) && article.getId() > 0) {
			Content content = new Content();
			content.setArticleId(article.getId());
			content.setContent(articleContentDTO.getContent());
			int i = contentMapper.update(content);
			if (i < 1) {
				throw new VLogException(ArticleConstant.FAIL_EDIT);
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
	public ReturnClass listArticle(String userName, QueryArticleDTO queryArticleDTO) {
		String accessToken = RequestHeaderContext.getInstance().getAccessToken();
		String userNameLog = (String) redisUtil.get(accessToken);
		Boolean flag = false;
		//判断请求是否是本人账号
		if (StringUtils.equals(userName, userNameLog)) {
			flag = true;
		}

		PageHelper.startPage(queryArticleDTO.getPageNum(), queryArticleDTO.getPageSize());
		Integer integer = articleMapper.listArticleCount(queryArticleDTO);
		List<ArticlesDTO> articlesDTOS = articleMapper.listArticle(queryArticleDTO);
		if (flag) {
			for (ArticlesDTO entity : articlesDTOS) {
				entity.setEditFlag((short) 1);
			}
		}
		PageBean<ArticlesDTO> pageBean=new PageBean<>(articlesDTOS,queryArticleDTO.getPageNum(),queryArticleDTO.getPageSize(),integer);
		return ReturnClass.success(pageBean);
	}

	/**
	 * @Description:获取文章详情
	 * @author: shuyu.wang
	 * @date: 2019-08-09 16:37
	 * @param id
	 * @return null
	 */
	@Override
	public ReturnClass articleDetail(Long id) {
		ArticleContentDTO articleContentDTO=new ArticleContentDTO();
		Article articleInfo = articleMapper.getArticleInfo(id);
		if (Objects.isNull(articleInfo)){
			throw new VLogException(ResultEnum.NO_URL);
		}
		BeanUtils.copyProperties(articleInfo,articleContentDTO);
		Content content = contentMapper.getContent(articleInfo.getId());
		articleContentDTO.setContent(content.getContent());
		return ReturnClass.success(articleContentDTO);
	}
}
