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
import com.technicalinterest.group.service.dto.ArticleContentDTO;
import com.technicalinterest.group.service.dto.PageBean;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.dto.UserDTO;
import com.technicalinterest.group.service.exception.VLogException;
import com.technicalinterest.group.service.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
@Slf4j
public class ArticleServiceImpl implements ArticleService {
	@Autowired
	private ArticleMapper articleMapper;
	@Autowired
	private ContentMapper contentMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private RedisUtil redisUtil;

	public static final Integer ARTICLE_LENGTH = 50;


	/**
	 * @Description: 新增文章
	 * @author: shuyu.wang
	 * @date: 2019-08-04 15:12
	 * @param articleContentDTO
	 * @return ReturnClass
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
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
		article.setSubmit(
				articleContentDTO.getContent().length() > ARTICLE_LENGTH ? articleContentDTO.getContent().substring(0, ARTICLE_LENGTH - 1) : articleContentDTO.getContent());
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
		//数据是否存在
		ArticlesDTO articleInfo = articleMapper.getArticleInfo(articleContentDTO.getId());
		if (Objects.isNull(articleInfo)) {
			throw new VLogException(ResultEnum.NO_DATA);
		}
		//判断是否是本人操作
		UserDTO userDTO = (UserDTO) userByToken.getData();
		if (!StringUtils.equals(articleInfo.getUserName(), userDTO.getUserName())) {
			throw new VLogException(ResultEnum.NO_AUTH);
		}

		article.setSubmit(
				articleContentDTO.getContent().length() > ARTICLE_LENGTH ? articleContentDTO.getContent().substring(0, ARTICLE_LENGTH - 1) : articleContentDTO.getContent());
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
	public ReturnClass listArticle(Boolean authCheck, String userName, QueryArticleDTO queryArticleDTO) {
		ReturnClass returnClass = userService.getUserByuserName(authCheck, userName);
		if (!returnClass.isSuccess()) {
			throw new VLogException(ResultEnum.NO_URL);
		}
		Integer integer = articleMapper.queryArticleListCount(queryArticleDTO);
		if (integer < 1) {
			return ReturnClass.fail(ResultEnum.NO_DATA.getMsg());
		}
		PageHelper.startPage(queryArticleDTO.getPageNum(), queryArticleDTO.getPageSize());
		List<ArticlesDTO> articlesDTOS = articleMapper.queryArticleList(queryArticleDTO);
		PageBean<ArticlesDTO> pageBean = new PageBean<>(articlesDTOS, queryArticleDTO.getPageNum(), queryArticleDTO.getPageSize(), integer);
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
	public ReturnClass articleDetail(Boolean authCheck, Long id) {
		ArticleContentDTO articleContentDTO = new ArticleContentDTO();
		ArticlesDTO articleInfo = articleMapper.getArticleInfo(id);
		if (Objects.isNull(articleInfo)) {
			throw new VLogException(ResultEnum.NO_URL);
		}
		//获取数据是否是当前用户校验
		if (authCheck) {
			ReturnClass returnClass = userService.userNameIsLoginUser(articleInfo.getUserName());
			if (!returnClass.isSuccess()) {
				throw new VLogException(ResultEnum.NO_AUTH);
			}
		}
		BeanUtils.copyProperties(articleInfo, articleContentDTO);
		Content content = contentMapper.getContent(articleInfo.getId());
		if (Objects.nonNull(content)) {
			articleContentDTO.setContent(content.getContent());
		}
		return ReturnClass.success(articleContentDTO);
	}

	/**
	 * @Description: 全站文章列表
	 * @author: shuyu.wang
	 * @date: 2019-08-04 15:12
	 * @param queryArticleDTO
	 * @return ReturnClass
	 */
	@Override
	public ReturnClass allListArticle(QueryArticleDTO queryArticleDTO) {

		queryArticleDTO.setState((short) 1);
		Integer integer = articleMapper.queryArticleListCount(queryArticleDTO);
		if (integer < 1) {
			return ReturnClass.fail(ResultEnum.NO_DATA.getMsg());
		}
		PageHelper.startPage(queryArticleDTO.getPageNum(), queryArticleDTO.getPageSize());
		List<ArticlesDTO> articlesDTOS = articleMapper.queryArticleList(queryArticleDTO);
		PageBean<ArticlesDTO> pageBean = new PageBean<>(articlesDTOS, queryArticleDTO.getPageNum(), queryArticleDTO.getPageSize(), integer);
		return ReturnClass.success(pageBean);
	}

	/**
	 * @Description: 按用户名查询文章列表
	 * @author: shuyu.wang
	 * @date: 2019-08-04 15:12
	 * @param flag 1:时间排序 2：阅读量排序  3：点赞数  4：评论数
	 * @return ReturnClass
	 */
	@Override
	public ReturnClass listArticleOrderBy(Boolean authCheck, String userName, Integer flag) {
		if (StringUtils.isNoneEmpty(userName)) {
			ReturnClass returnClass = userService.getUserByuserName(authCheck, userName);
			if (!returnClass.isSuccess()) {
				throw new VLogException(ResultEnum.NO_URL);
			}
		}
		List<ArticlesDTO> articlesDTOS = articleMapper.queryArticleListOrderBy(flag, userName);
		if (articlesDTOS.isEmpty()) {
			return ReturnClass.fail(ResultEnum.NO_DATA.getMsg());
		}
		return ReturnClass.success(articlesDTOS);
	}

	/**
	 * @Description:博客文章归档
	 * @author: shuyu.wang
	 * @date: 2019/8/13 22:37
	 * @param userName
	 * @return null
	 */
	@Override
	public ReturnClass listArticleArchive(String userName) {
		ReturnClass returnClass = userService.getUserByuserName(false, userName);
		if (!returnClass.isSuccess()) {
			throw new VLogException(ResultEnum.NO_URL);
		}
		List<ArticlesDTO> articlesDTOS = articleMapper.queryArticleListArchive(userName);
		if (articlesDTOS.isEmpty()) {
			return ReturnClass.fail(ResultEnum.NO_DATA.getMsg());
		}
		return ReturnClass.success(articlesDTOS);
	}

	/**
	 * @Description:删除文章
	 * @author: shuyu.wang
	 * @date: 2019-08-16 18:45
	 * @param id
	 * @return null
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ReturnClass delArticle(Long id) {
		ReturnClass userByToken = userService.getUserByToken();
		if (!userByToken.isSuccess()) {
			throw new VLogException(ResultEnum.USERINFO_ERROR);
		}
		//数据是否存在
		ArticlesDTO articleInfo = articleMapper.getArticleInfo(id);
		if (Objects.isNull(articleInfo)) {
			throw new VLogException(ResultEnum.NO_DATA);
		}
		//判断是否是本人操作
		UserDTO userDTO = (UserDTO) userByToken.getData();
		if (!StringUtils.equals(articleInfo.getUserName(), userDTO.getUserName())) {
			throw new VLogException(ResultEnum.NO_AUTH);
		}
		Integer integer = articleMapper.delArticle(id);
		if (integer > 0) {
			Integer integer1 = contentMapper.delContent(id);
			if (integer1 < 1) {
				log.error("文章详情删除失败，ArticleId={}", id);
			}
			return ReturnClass.success();
		} else {
			return ReturnClass.fail();
		}

	}

	/**
	 * @Description:更新文章的一些状态
	 * @author: shuyu.wang
	 * @date: 2019-08-16 18:45
	 * @param articleContentDTO
	 * @return null
	 */
	@Override
	public ReturnClass updateArticleState(ArticleContentDTO articleContentDTO) {
		ReturnClass userByToken = userService.getUserByToken();
		if (!userByToken.isSuccess()) {
			throw new VLogException(ResultEnum.USERINFO_ERROR);
		}
		//数据是否存在
		ArticlesDTO articleInfo = articleMapper.getArticleInfo(articleContentDTO.getId());
		if (Objects.isNull(articleInfo)) {
			throw new VLogException(ResultEnum.NO_DATA);
		}
		//判断是否是本人操作
		UserDTO userDTO = (UserDTO) userByToken.getData();
		if (!StringUtils.equals(articleInfo.getUserName(), userDTO.getUserName())) {
			throw new VLogException(ResultEnum.NO_AUTH);
		}
		Article article = new Article();
		BeanUtils.copyProperties(articleContentDTO, article);
		Integer update = articleMapper.update(article);
		if (update > 0) {
			return ReturnClass.success();
		}
		return ReturnClass.fail();
	}

	/**
	 * @Description:文章阅读数增加
	 * @author: shuyu.wang
	 * @date: 2019-08-17 19:17
	 * @param id
	 * @return null
	 */
	@Override
	@Async
	public ReturnClass addReadCount(Long id) {

		Integer update = articleMapper.updateReadCount(id);
		if (update > 0) {
			log.info("文章阅读数累加成功！");
		}else {
			log.info("文章阅读数累加失败！");
		}
		return ReturnClass.success();
	}
}
