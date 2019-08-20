package com.technicalinterest.group.service.impl;

import com.github.pagehelper.PageHelper;
import com.technicalinterest.group.dao.Comment;
import com.technicalinterest.group.dao.PageBase;
import com.technicalinterest.group.dto.ArticlesDTO;
import com.technicalinterest.group.dto.CommentNoticeDTO;
import com.technicalinterest.group.mapper.CommentMapper;
import com.technicalinterest.group.service.NoticeService;
import com.technicalinterest.group.service.UserService;
import com.technicalinterest.group.service.constant.NoticeConstant;
import com.technicalinterest.group.service.constant.ResultEnum;
import com.technicalinterest.group.service.dto.PageBean;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.exception.VLogException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
	@Autowired
	private UserService userService;
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
		ReturnClass returnClass = userService.userNameIsLoginUser(userName);
		if (!returnClass.isSuccess()){
			throw new VLogException(ResultEnum.NO_URL);
		}
		Integer integer = commentMapper.queryCountCommentNotice(userName);
		if (integer<1){
			return ReturnClass.fail(NoticeConstant.NO_COMMENT);
		}
		PageHelper.startPage(pageBase.getPageNum(), pageBase.getPageSize());
		List<CommentNoticeDTO> commentNoticeDTOS = commentMapper.queryListCommentNotice(userName);
		PageBean<CommentNoticeDTO> pageBean = new PageBean<>(commentNoticeDTOS, pageBase.getPageNum(), pageBase.getPageSize(), integer);
		return ReturnClass.success(pageBean);
	}

	/**
	 * @Description:查看评论，标记为已经查看
	 * @author: shuyu.wang
	 * @date: 2019/8/20 22:56
	 * @param id
	 * @return null
	 */
	@Override
	public ReturnClass viewComment(Long id) {
		Comment comment1 = commentMapper.queryCommentById(id);
		if (Objects.isNull(comment1)){
			throw new VLogException(ResultEnum.NO_DATA);
		}
		Comment comment=new Comment();
		comment.setId(id);
		comment.setIsView((short)1);
		Integer update = commentMapper.update(comment);
		if (update>0){
			return ReturnClass.success();
		}
		return null;
	}
}
