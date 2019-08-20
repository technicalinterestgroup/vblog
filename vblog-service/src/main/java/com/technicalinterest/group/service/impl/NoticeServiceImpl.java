package com.technicalinterest.group.service.impl;

import com.github.pagehelper.PageHelper;
import com.technicalinterest.group.dao.PageBase;
import com.technicalinterest.group.dto.ArticlesDTO;
import com.technicalinterest.group.dto.CommentNoticeDTO;
import com.technicalinterest.group.mapper.CommentMapper;
import com.technicalinterest.group.service.NoticeService;
import com.technicalinterest.group.service.constant.NoticeConstant;
import com.technicalinterest.group.service.dto.PageBean;
import com.technicalinterest.group.service.dto.ReturnClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @package: com.technicalinterest.group.service.impl
 * @className: NoticeServiceImpl
 * @description: 通知评论层
 * @author: Shuyu.Wang
 * @date: 2019-08-20 13:02
 * @since: 0.1
 **/
@Service
public class NoticeServiceImpl implements NoticeService {
	@Autowired
	private CommentMapper commentMapper;
	/**
	 * @Description:评论通知列表
	 * @author: shuyu.wang
	 * @date: 2019-08-20 13:05
	 * @param userName
	 * @param pageBase
	 * @return com.technicalinterest.group.service.dto.ReturnClass
	 */
	@Override
	public ReturnClass queryCommentNotice(String userName, PageBase pageBase) {
		Integer integer = commentMapper.queryCountCommentNotice(userName);
		if (integer<1){
			return ReturnClass.fail(NoticeConstant.NO_COMMENT);
		}
		PageHelper.startPage(pageBase.getPageNum(), pageBase.getPageSize());
		List<CommentNoticeDTO> commentNoticeDTOS = commentMapper.queryListCommentNotice(userName);
		PageBean<CommentNoticeDTO> pageBean = new PageBean<>(commentNoticeDTOS, pageBase.getPageNum(), pageBase.getPageSize(), integer);
		return ReturnClass.success(pageBean);
	}
}
