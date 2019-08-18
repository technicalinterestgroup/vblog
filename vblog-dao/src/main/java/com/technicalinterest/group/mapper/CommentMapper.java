package com.technicalinterest.group.mapper;

import com.technicalinterest.group.dto.CommentDTO;
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
}
