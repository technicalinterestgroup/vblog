package com.technicalinterest.group.mapper;

import com.technicalinterest.group.dto.FileDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.technicalinterest.group.dao.FileUpload;

@Mapper
public interface FileUploadMpper {
	Integer insert(@Param("pojo") FileUpload pojo);

	Integer insertSelective(@Param("pojo") FileUpload pojo);

	Integer insertList(@Param("pojos") List<FileUpload> pojo);

	Integer update(@Param("pojo") FileUpload pojo);

	List<FileDTO> queryListFile(@Param("userName") String userName, @Param("fileType") Short fileType);

	Integer queryCountFile(@Param("userName") String userName, @Param("fileType") Short fileType);

	FileUpload queryFileById(@Param("id") Long id);

	Integer del(@Param("id") Long id);

}
