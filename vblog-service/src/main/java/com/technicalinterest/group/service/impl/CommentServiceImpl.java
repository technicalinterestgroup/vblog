package com.technicalinterest.group.service.impl;

import com.technicalinterest.group.dao.Comment;
import com.technicalinterest.group.dto.ArticlesDTO;
import com.technicalinterest.group.dto.CommentDTO;
import com.technicalinterest.group.mapper.ArticleMapper;
import com.technicalinterest.group.mapper.CommentMapper;
import com.technicalinterest.group.service.CommentService;
import com.technicalinterest.group.service.UserService;
import com.technicalinterest.group.service.constant.CommentConstant;
import com.technicalinterest.group.service.constant.ResultEnum;
import com.technicalinterest.group.service.dto.EditCommentDTO;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.dto.UserDTO;
import com.technicalinterest.group.service.exception.VLogException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author: Shuyu.Wang
 * @date: 2019-08-04 15:06
 * @since: 0.1
 **/
@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private ArticleMapper articleMapper;

	@Override
	public ReturnClass insert(EditCommentDTO pojo) {
		Comment comment = new Comment();
		BeanUtils.copyProperties(pojo, comment);
		ReturnClass userByToken = userService.getUserByToken();
		if (userByToken.isSuccess()) {
			UserDTO userDTO = (UserDTO) userByToken.getData();
			comment.setUserName(userDTO.getUserName());
		}
		ArticlesDTO articleInfo = articleMapper.getArticleInfo(pojo.getArticleId());
		if (Objects.isNull(articleInfo)){
			throw new VLogException(CommentConstant.ARTICLE_ID_ERROR);
		}
		if (StringUtils.equals(articleInfo.getUserName(),comment.getUserName())){
			comment.setIsAuther((short)1);
		}
		if (Objects.nonNull(comment.getParentId())){
			Comment comment1 = commentMapper.queryCommentById(comment.getParentId());
			if (Objects.isNull(comment1)){
				throw new VLogException(CommentConstant.PARENT_COMMENT_ID_ERROR);
			}
		}
		Integer integer = commentMapper.insertSelective(comment);
		if (integer > 0) {
			return ReturnClass.success(CommentConstant.SAVE_SUCCESS);
		}
		return ReturnClass.fail(CommentConstant.SAVE_FAIL);
	}

	@Override
	public ReturnClass del(Long id) {
		Comment comment = commentMapper.queryCommentById(id);
		if (Objects.isNull(comment)) {
			throw new VLogException(ResultEnum.NO_DATA);
		}
		ReturnClass returnClass = userService.userNameIsLoginUser(comment.getUserName());
		if (!returnClass.isSuccess()) {
			throw new VLogException(ResultEnum.NO_AUTH);
		}
		Integer integer = commentMapper.delComment(id);
		if (integer > 0) {
			return ReturnClass.success(CommentConstant.DEL_SUCCESS);
		}
		return ReturnClass.fail(CommentConstant.DEL_FAIL);
	}

	/**
	 * @Description: 获取文章的一级评论
	 * @author: shuyu.wang
	 * @date: 2019-08-18 20:10
	 * @param id
	 * @return null
	 */
	@Override
	public ReturnClass getArticleComment(Long id) {
		//文章id是否存在
		ArticlesDTO articleInfo = articleMapper.getArticleInfo(id);
		if (Objects.isNull(articleInfo)) {
			throw new VLogException(ResultEnum.NO_DATA);
		}
		List<CommentDTO> commentDTOS = commentMapper.queryListComment(null, id, null);
		for (CommentDTO entity : commentDTOS) {
			List<CommentDTO> childParent = getChildParent(entity.getId());
			entity.setChildComment(childParent);
		}
		if (commentDTOS.isEmpty()) {
			return ReturnClass.fail(CommentConstant.NO_COMMENT);
		}

		return ReturnClass.success(commentDTOS);
	}

	/**
	 * @Description:递归循环子评论
	 * @author: shuyu.wang
	 * @date: 2019-08-18 21:08
	 * @param id
	 * @return java.util.List<com.technicalinterest.group.dto.CommentDTO>
	 */
	private List<CommentDTO> getChildParent(Long id) {

		List<CommentDTO> commentDTOS = commentMapper.queryListComment(null, null, id);
		if (commentDTOS.isEmpty()) {
			return null;
		}
		for (CommentDTO entity : commentDTOS) {
			List<CommentDTO> child = getChildParent(entity.getId());
			if (Objects.nonNull(child)) {
				entity.setChildComment(child);
			}
		}
		return commentDTOS;
	}
}
