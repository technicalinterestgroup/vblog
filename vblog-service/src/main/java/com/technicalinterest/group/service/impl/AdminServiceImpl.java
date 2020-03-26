package com.technicalinterest.group.service.impl;

import com.github.pagehelper.PageHelper;
import com.technicalinterest.group.dao.*;
import com.technicalinterest.group.dto.*;
import com.technicalinterest.group.mapper.*;
import com.technicalinterest.group.service.AdminService;
import com.technicalinterest.group.service.Enum.ResultEnum;
import com.technicalinterest.group.service.constant.ArticleConstant;
import com.technicalinterest.group.service.constant.FileConstant;
import com.technicalinterest.group.service.constant.UserConstant;
import com.technicalinterest.group.service.dto.ArticleContentDTO;
import com.technicalinterest.group.service.dto.EditUserDTO;
import com.technicalinterest.group.service.dto.PageBean;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.exception.VLogException;
import com.technicalinterest.group.service.util.HtmlUtil;
import com.technicalinterest.group.service.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @package: com.technicalinterest.group.service.impl
 * @className: AdminServiceImpl
 * @description: 管理员服务层
 * @author: Shuyu.Wang
 * @date: 2019-09-01 15:28
 * @since: 0.1
 **/
@Service
@Slf4j
public class AdminServiceImpl implements AdminService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private ArticleMapper articleMapper;
	@Autowired
	private FileUploadMpper fileUploadMpper;
	@Autowired
	private LogMapper logMapper;
	@Autowired
	private AuthMapper authMapper;
	@Autowired
	private RoleAuthMapper roleAuthMapper;

	/**
	 * @Description:查询全部博客用户
	 * @author: shuyu.wang
	 * @date: 2019-09-01 15:29
	 * @return null
	 */
	@Override
	public ReturnClass userAll(UserRoleDTO user) {
		Integer integer = userMapper.queryAllUserCount(user);
		if (integer<1){
			return ReturnClass.fail(ResultEnum.NO_DATA.getMsg());
		}
		PageHelper.startPage(user.getCurrentPage(), user.getPageSize());
		List<UserRoleDTO> userRoleDTOS = userMapper.queryAllUser(user);
		PageBean<UserRoleDTO> pageBean = new PageBean<>(userRoleDTOS, user.getCurrentPage(), user.getPageSize(), integer);
		return ReturnClass.success(pageBean);
	}

	/**
	 * @Description:查询全部文章
	 * @author: shuyu.wang
	 * @date: 2019-09-01 16:43
	 * @param queryArticleDTO
	 * @return null
	 */
	@Override
	public ReturnClass articleAll(QueryArticleDTO queryArticleDTO) {
		Integer integer = articleMapper.allArticleListCount(queryArticleDTO);
		if (integer < 1) {
			return ReturnClass.fail(ArticleConstant.NO_BLOG);
		}
		PageHelper.startPage(queryArticleDTO.getCurrentPage(), queryArticleDTO.getPageSize());
		queryArticleDTO.setState((short) 1);
		List<ArticlesDTO> articlesDTOS = articleMapper.allArticleList(queryArticleDTO);
		PageBean<ArticlesDTO> pageBean = new PageBean<>(articlesDTOS, queryArticleDTO.getCurrentPage(), queryArticleDTO.getPageSize(), integer);
		return ReturnClass.success(pageBean);
	}

	/**
	 * @Description:文章操作
	 * @author: shuyu.wang
	 * @date: 2019-09-01 16:43
	 * @param articleContentDTO
	 * @return null
	 */
	@Override
	public ReturnClass editArticle(ArticleContentDTO articleContentDTO) {
		Article article = new Article();
		BeanUtils.copyProperties(articleContentDTO, article);
		//数据是否存在
		Article param = new Article();
		param.setId(articleContentDTO.getId());
		Article articleResult = articleMapper.query(param);
		if (Objects.isNull(articleResult)) {
			throw new VLogException(ResultEnum.NO_DATA);
		}
		articleResult.setRecommend(articleContentDTO.getRecommend());
		articleResult.setIsReview(articleContentDTO.getIsReview());
		articleMapper.update(article);
		return ReturnClass.success();
	}

	/**
	 * @Description:查询全部文件
	 * @author: shuyu.wang
	 * @date: 2019-09-01 16:43
	 * @param queryFileDTO
	 * @return null
	 */
	@Override
	public ReturnClass fileAll(QueryFileDTO queryFileDTO) {
		Integer integer = fileUploadMpper.allFileCount(queryFileDTO);
		if (integer < 1) {
			return ReturnClass.fail(FileConstant.NO_FILE);
		}
		PageHelper.startPage(queryFileDTO.getCurrentPage(), queryFileDTO.getPageSize());
		List<FileDTO> fileDTOS = fileUploadMpper.allFileList(queryFileDTO);
		PageBean<FileDTO> pageBean = new PageBean<>(fileDTOS, queryFileDTO.getCurrentPage(), queryFileDTO.getPageSize(), integer);
		return ReturnClass.success(pageBean);
	}

	/**
	 * @Description:查询全部文件
	 * @author: shuyu.wang
	 * @date: 2019-09-01 16:43
	 * @param queryLogDTO
	 * @return null
	 */
	@Override
	public ReturnClass logAll(QueryLogDTO queryLogDTO) {
		Integer integer = logMapper.allLogCount(queryLogDTO);
//		if (integer < 1) {
//			return ReturnClass.fail(FileConstant.NO_FILE);
//		}
		PageHelper.startPage(queryLogDTO.getCurrentPage(), queryLogDTO.getPageSize());
		List<Log> logs = logMapper.allLogList(queryLogDTO);
		PageBean<Log> pageBean = new PageBean<>(logs, queryLogDTO.getCurrentPage(), queryLogDTO.getPageSize(), integer);
		return ReturnClass.success(pageBean);
	}
	@Override
	public ReturnClass updateUser(EditUserDTO editUserDTO) {
		User user = new User();
		BeanUtils.copyProperties(editUserDTO, user);
		int update = userMapper.updateByUserName(user);
		if (update != 1) {
			return ReturnClass.fail(UserConstant.EDIT_USER_ERROR);
		} else {
			return ReturnClass.success(UserConstant.EDIT_USER_SUS);
		}
	}
}
