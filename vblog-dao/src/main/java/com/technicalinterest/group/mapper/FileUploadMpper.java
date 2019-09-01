package com.technicalinterest.group.mapper;

import com.technicalinterest.group.dto.FileDTO;
import com.technicalinterest.group.dto.QueryFileDTO;
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

	List<FileDTO> queryListFile(QueryFileDTO queryFileDTO);

	Integer queryCountFile(QueryFileDTO queryFileDTO);

	FileUpload queryFileById(@Param("id") Long id);

	Integer del(@Param("id") Long id);


	List<FileDTO> allFileList(QueryFileDTO queryFileDTO);

	Integer allFileCount(QueryFileDTO queryFileDTO);

}
