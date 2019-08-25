package com.technicalinterest.group.mapper;

import com.technicalinterest.group.dto.CommentDTO;
import com.technicalinterest.group.dto.CommentNoticeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.technicalinterest.group.dao.Comment;

@Mapper
public interface CommentMapper {

    Integer insert(@Param("pojo") Comment pojo);

    Integer insertSelective(@Param("pojo") Comment pojo);

    Integer insertList(@Param("pojos") List<Comment> pojo);

    Integer update(@Param("pojo") Comment pojo);

    Comment queryCommentById(@Param("id")Long id);

    Integer delComment(@Param("id")Long id);

    List<CommentDTO> queryListComment(@Param("userName")String userName,@Param("articleId")Long articleId,@Param("parentId")Long parentId);

    
    /**
     * @Description: 查询评论消息通知
     * @author: shuyu.wang
     * @date: 2019-08-20 12:43
     * @param userName
     * @return null
    */
    List<CommentNoticeDTO> queryListCommentNotice(@Param("userName")String userName);

    /**
     * @Description: 查询评论消息通知
     * @author: shuyu.wang
     * @date: 2019-08-20 12:43
     * @param userName
     * @return null
     */
    Integer queryCountCommentNotice(@Param("userName")String userName);

}
