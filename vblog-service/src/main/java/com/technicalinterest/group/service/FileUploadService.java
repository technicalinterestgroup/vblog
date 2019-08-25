package com.technicalinterest.group.service;

import com.technicalinterest.group.dao.FileUpload;
import com.technicalinterest.group.dao.PageBase;
import com.technicalinterest.group.service.dto.FileUploadDTO;
import com.technicalinterest.group.service.dto.ReturnClass;


public interface FileUploadService {

	ReturnClass insert(FileUploadDTO pojo);

	ReturnClass del(Long id);

	ReturnClass queryFileList(String userName, PageBase pageBase);
}
