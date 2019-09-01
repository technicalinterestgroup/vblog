package com.technicalinterest.group.service.impl;

import com.github.pagehelper.PageHelper;
import com.technicalinterest.group.dao.FileUpload;
import com.technicalinterest.group.dao.Log;
import com.technicalinterest.group.dto.*;
import com.technicalinterest.group.mapper.ArticleMapper;
import com.technicalinterest.group.mapper.FileUploadMpper;
import com.technicalinterest.group.mapper.LogMapper;
import com.technicalinterest.group.mapper.UserMapper;
import com.technicalinterest.group.service.AdminService;
import com.technicalinterest.group.service.constant.ArticleConstant;
import com.technicalinterest.group.service.constant.FileConstant;
import com.technicalinterest.group.service.constant.UserConstant;
import com.technicalinterest.group.service.dto.PageBean;
import com.technicalinterest.group.service.dto.ReturnClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @package: com.technicalinterest.group.service.impl
 * @className: AdminServiceImpl
 * @description: 管理员服务层
 * @author: Shuyu.Wang
 * @date: 2019-09-01 15:28
 * @since: 0.1
 **/
@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private ArticleMapper articleMapper;
	@Autowired
	private FileUploadMpper fileUploadMpper;
	@Autowired
	private LogMapper logMapper;

	/**
	 * @Description:查询全部博客用户
	 * @author: shuyu.wang
	 * @date: 2019-09-01 15:29
	 * @return null
	 */
	@Override
	public ReturnClass userAll() {
		List<UserRoleDTO> userRoleDTOS = userMapper.queryAllUser(null);
		if (userRoleDTOS.isEmpty()) {
			return ReturnClass.fail(UserConstant.NO_USER_INFO);
		}
		return ReturnClass.success(userRoleDTOS);
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
		PageHelper.startPage(queryArticleDTO.getPageNum(), queryArticleDTO.getPageSize());
		queryArticleDTO.setState((short) 1);
		List<ArticlesDTO> articlesDTOS = articleMapper.allArticleList(queryArticleDTO);
		PageBean<ArticlesDTO> pageBean = new PageBean<>(articlesDTOS, queryArticleDTO.getPageNum(), queryArticleDTO.getPageSize(), integer);
		return ReturnClass.success(pageBean);
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
		PageHelper.startPage(queryFileDTO.getPageNum(), queryFileDTO.getPageSize());
		List<FileDTO> fileDTOS = fileUploadMpper.allFileList(queryFileDTO);
		PageBean<FileDTO> pageBean = new PageBean<>(fileDTOS, queryFileDTO.getPageNum(), queryFileDTO.getPageSize(), integer);
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
		PageHelper.startPage(queryLogDTO.getPageNum(), queryLogDTO.getPageSize());
		List<Log> logs = logMapper.allLogList(queryLogDTO);
		PageBean<Log> pageBean = new PageBean<>(logs, queryLogDTO.getPageNum(), queryLogDTO.getPageSize(), integer);
		return ReturnClass.success(pageBean);
	}
}
