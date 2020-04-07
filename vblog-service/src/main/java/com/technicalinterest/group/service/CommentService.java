package com.technicalinterest.group.service;

import com.technicalinterest.group.dao.Reply;
import com.technicalinterest.group.service.dto.EditCommentDTO;
import com.technicalinterest.group.service.dto.ReturnClass;

import java.util.List;

public interface CommentService {

	ReturnClass insert(EditCommentDTO pojo);

	ReturnClass del(Long id);

    /**
     * @Description: 获取文章的一级评论
     * @author: shuyu.wang
     * @date: 2019-08-18 20:10
     * @param id
     * @return null
    */
	ReturnClass getArticleComment(Long id,Short type);

}
