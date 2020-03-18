package com.technicalinterest.group.service.impl;

import com.github.pagehelper.PageHelper;
import com.technicalinterest.group.dao.FileUpload;
import com.technicalinterest.group.dto.FileDTO;
import com.technicalinterest.group.dto.QueryFileDTO;
import com.technicalinterest.group.mapper.FileUploadMpper;
import com.technicalinterest.group.service.FileUploadService;
import com.technicalinterest.group.service.UserService;
import com.technicalinterest.group.service.constant.FileConstant;
import com.technicalinterest.group.service.Enum.ResultEnum;
import com.technicalinterest.group.service.dto.FileUploadDTO;
import com.technicalinterest.group.service.dto.PageBean;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.exception.VLogException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class FileUploadServiceImpl implements FileUploadService {

	@Autowired
	private FileUploadMpper fileUploadMpper;
	@Autowired
	private UserService userService;

	@Value("${file_path}")
	private String ROOT_PATH;

	@Override
	@Async
	public ReturnClass insert(FileUploadDTO pojo) {
		FileUpload fileUpload = new FileUpload();
		BeanUtils.copyProperties(pojo, fileUpload);
		fileUploadMpper.insertSelective(fileUpload);
		return ReturnClass.success();
	}

	@Override
	public ReturnClass del(Long id) {
		FileUpload fileUpload = fileUploadMpper.queryFileById(id);
		if (Objects.isNull(fileUpload)){
			throw new VLogException(ResultEnum.NO_URL);
		}
		ReturnClass returnClass = userService.userNameIsLoginUser(fileUpload.getUserName());
		if (!returnClass.isSuccess()){
			throw new VLogException(ResultEnum.NO_AUTH);
		}

		String path=ROOT_PATH+ File.separator+fileUpload.getFilePath();
		Integer del = fileUploadMpper.del(id);
		if (del<1){
			return ReturnClass.fail(FileConstant.DEL_FAIL);
		}
		File file = new File(path);
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				log.info("文件=【{}】删除成功！！",path);
			} else {
				log.info("文件=【{}】删除失败！！",path);
			}
		} else {
			log.info("文件=【{}】不存在！",path);
		}

		return ReturnClass.fail(FileConstant.DEL_SUS);
	}

	@Override
	public ReturnClass queryFileList(QueryFileDTO queryFileDTO) {
		ReturnClass returnClass = userService.getUserByuserName(true, queryFileDTO.getUserName());
		if (!returnClass.isSuccess()) {
			throw new VLogException(ResultEnum.NO_URL);
		}
		Integer integer = fileUploadMpper.queryCountFile(queryFileDTO);
		if (integer < 1) {
			return ReturnClass.fail(FileConstant.NO_FILE);
		}
		PageHelper.startPage(queryFileDTO.getCurrentPage(), queryFileDTO.getPageSize());
		List<FileDTO> fileDTOS = fileUploadMpper.queryListFile(queryFileDTO);
		PageBean<FileDTO> pageBean = new PageBean<>(fileDTOS, queryFileDTO.getCurrentPage(), queryFileDTO.getPageSize(), integer);
		return ReturnClass.success(pageBean);
	}
}
