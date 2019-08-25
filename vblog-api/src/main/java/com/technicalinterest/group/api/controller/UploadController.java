package com.technicalinterest.group.api.controller;

import com.technicalinterest.group.api.param.PageBaseParam;
import com.technicalinterest.group.api.vo.ApiResult;
import com.technicalinterest.group.api.vo.FileVO;
import com.technicalinterest.group.api.vo.ImgVO;
import com.technicalinterest.group.dao.PageBase;
import com.technicalinterest.group.dto.FileDTO;
import com.technicalinterest.group.service.FileUploadService;
import com.technicalinterest.group.service.UserService;
import com.technicalinterest.group.service.annotation.BlogOperation;
import com.technicalinterest.group.service.dto.FileUploadDTO;
import com.technicalinterest.group.service.dto.PageBean;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @package: com.technicalinterest.group.api.controller
 * @className: UploadController
 * @description: 文件上传
 * @author: Shuyu.Wang
 * @date: 2019-08-08 19:25
 * @since: 0.1
 **/
@Api(tags = "文件管理")
@RestController
@RequestMapping("file")
@Slf4j
public class UploadController {
	@Value("${file_path}")
	private String ROOT_PATH;
	@Autowired
	private UserService userService;
	@Autowired
	private FileUploadService fileUploadService;

	private static String IMG_TYPE = "png,jpg,jpeg,bmp,gif,";

	private static String DOC_TYPE_GIF = "doc,docx,xls,xlsx,pdf,zip,";

	private static final String IMG_TYPE_ERROR = "文件格式不支持！只允许上传jpg,jng,jpeg,bmp,gif格式的图片！";

	private static final String DOC_TYPE_ERROR = "文件格式不支持！只允许上传doc,docx,xls,xlsx,pdf,zip格式的文件！";

	private static final String SIZE_ERROR = "文件过大！";

	private static final Integer IMG_FILE_SIZE = 300;

	private static final Integer DOC_FILE_SIZE = 600;

	private static final String IMG_PATH = "img";

	private static final String FILE_PATH = "file";

	private static final String PREFIX_PATH = "vblog";

	private static final String FILE_EMPTY = "文件不能为空！";

	private static final String UNIT = "K";

	@ApiOperation(value = "图片上传", notes = "图片上传")
	@PostMapping(value = "/img/upload")
	@BlogOperation(value = "图片上传")
	public ApiResult<String> uploadImg(@RequestParam(value = "file") MultipartFile file) {
		ReturnClass userByToken = userService.getUserByToken();
		String userName = null;
		if (userByToken.isSuccess()) {
			UserDTO userDTO = (UserDTO) userByToken.getData();
			userName = userDTO.getUserName();
		}
		ApiResult<String> apiResult = new ApiResult<>();
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
		if (!IMG_TYPE.contains(suffix)) {
			apiResult.fail(IMG_TYPE_ERROR);
			return apiResult;
		}
		if (file.isEmpty()) {
			apiResult.fail(FILE_EMPTY);
			return apiResult;
		}
		if (!checkFileSize(file.getSize(), IMG_FILE_SIZE, UNIT)) {
			apiResult.fail(SIZE_ERROR);
			return apiResult;
		}
		try {
			String s = saveFiles(file, userName, null, IMG_PATH);
			apiResult.success(s, "文件上传成功");
			log.info("------>>>>>>图片共上传成功!<<<<<<------");
		} catch (Exception e) {
			log.error("文件上传异常", e);
			apiResult.fail("文件上传异常");
		}

		return apiResult;
	}

	@ApiOperation(value = "文件上传", notes = "文件上传")
	@PostMapping(value = "/file/upload")
	@BlogOperation(value = "文件上传")
	public ApiResult<String> uploadFile(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "filePaht", required = false) String filePaht) {
		ReturnClass userByToken = userService.getUserByToken();
		String userName = null;
		if (userByToken.isSuccess()) {
			UserDTO userDTO = (UserDTO) userByToken.getData();
			userName = userDTO.getUserName();
		}
		ApiResult<String> apiResult = new ApiResult<>();
		try {
			String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
			if (!DOC_TYPE_GIF.contains(suffix + ",")) {
				apiResult.fail(DOC_TYPE_ERROR);
				return apiResult;
			}
			if (file.isEmpty()) {
				apiResult.fail(FILE_EMPTY);
				return apiResult;
			}
			if (!checkFileSize(file.getSize(), DOC_FILE_SIZE, UNIT)) {
				apiResult.fail(SIZE_ERROR);
				return apiResult;
			}
			String s = saveFiles(file, userName, filePaht, FILE_PATH);
			apiResult.success("文件上传成功", s);
			log.info("------>>>>>>文件上传成功!<<<<<<------");
		} catch (Exception e) {
			log.error("文件上传异常", e);
			apiResult.fail("文件上传异常");
		}
		return apiResult;
	}

	@ApiOperation(value = "上传文件列表", notes = "文件列表")
	@GetMapping(value = "/list/{userName}")
	@BlogOperation(value = "上传文件列表")
	public ApiResult<PageBean<FileVO>> listFile(@PathVariable("userName") String userName, @Valid PageBaseParam pageBaseParam) {
		ApiResult apiResult = new ApiResult();

		PageBase pageBase = new PageBase();
		BeanUtils.copyProperties(pageBaseParam, pageBase);
		ReturnClass listArticle = fileUploadService.queryFileList(userName, pageBase);
		if (listArticle.isSuccess()) {
			PageBean<FileDTO> pageBean = (PageBean<FileDTO>) listArticle.getData();
			List<FileVO> list = new ArrayList<>();
			for (FileDTO entity : pageBean.getPageData()) {
				FileVO fileVO = new FileVO();
				BeanUtils.copyProperties(entity, fileVO);
				list.add(fileVO);
			}
			PageBean<FileVO> pageInfo = new PageBean<FileVO>();
			BeanUtils.copyProperties(listArticle.getData(), pageInfo);
			pageInfo.setPageData(list);
			apiResult.success(pageInfo);
		} else {
			apiResult.setMsg(listArticle.getMsg());
		}
		return apiResult;
	}

	@ApiOperation(value = "图片列表", notes = "图片列表")
	@GetMapping(value = "/list/img/{userName}")
	@BlogOperation(value = "上传图片列表")
	public ApiResult<PageBean<ImgVO>> listImg(@PathVariable("userName") String userName, @Valid PageBaseParam pageBaseParam) {
		ApiResult apiResult = new ApiResult();

		PageBase pageBase = new PageBase();
		BeanUtils.copyProperties(pageBaseParam, pageBase);
		ReturnClass listArticle = fileUploadService.queryFileList(userName, pageBase);
		if (listArticle.isSuccess()) {
			PageBean<FileDTO> pageBean = (PageBean<FileDTO>) listArticle.getData();
			List<ImgVO> list = new ArrayList<>();
			for (FileDTO entity : pageBean.getPageData()) {
				ImgVO fileVO = new ImgVO();
				BeanUtils.copyProperties(entity, fileVO);
				list.add(fileVO);
			}
			PageBean<ImgVO> pageInfo = new PageBean<ImgVO>();
			BeanUtils.copyProperties(listArticle.getData(), pageInfo);
			pageInfo.setPageData(list);
			apiResult.success(pageInfo);
		} else {
			apiResult.setMsg(listArticle.getMsg());
		}
		return apiResult;
	}

	@ApiOperation(value = "文件删除", notes = "文件删除")
	@GetMapping(value = "/del/{id}")
	@BlogOperation(value = "文件删除")
	public ApiResult<PageBean<ImgVO>> delFile(@PathVariable("id") Long id) {
		ApiResult apiResult = new ApiResult();
		ReturnClass returnClass = fileUploadService.del(id);
		if (returnClass.isSuccess()) {
			apiResult.success(returnClass.getMsg(), null);
		} else {
			apiResult.setMsg(returnClass.getMsg());
		}
		return apiResult;
	}

	private String getTime() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmsssss");
		return sdf.format(d);
	}

	/**
	 * 判断文件大小
	 * @param len 文件长度
	 * @param size 限制大小
	 * @param unit 限制单位（B,K,M,G）
	 * @return
	 */
	private static boolean checkFileSize(Long len, int size, String unit) {
		double fileSize = 0;
		if ("B".equals(unit.toUpperCase())) {
			fileSize = (double) len;
		} else if ("K".equals(unit.toUpperCase())) {
			fileSize = (double) len / 1024;
		} else if ("M".equals(unit.toUpperCase())) {
			fileSize = (double) len / 1048576;
		} else if ("G".equals(unit.toUpperCase())) {
			fileSize = (double) len / 1073741824;
		}
		if (fileSize > size) {
			return false;
		}
		return true;
	}

	/**
	 * 判断文件大小
	 * @param len 文件长度
	 * @param unit 限制单位（B,K,M,G）
	 * @return
	 */
	private static double getFileSize(Long len, String unit) {
		double fileSize = 0;
		if ("B".equals(unit.toUpperCase())) {
			fileSize = (double) len;
		} else if ("K".equals(unit.toUpperCase())) {
			fileSize = (double) len / 1024;
		} else if ("M".equals(unit.toUpperCase())) {
			fileSize = (double) len / 1048576;
		} else if ("G".equals(unit.toUpperCase())) {
			fileSize = (double) len / 1073741824;
		}

		return fileSize;
	}

	/**
	 * @Description: 保存文件
	 * @author: shuyu.wang
	 * @date: 2019-08-25 15:40
	 * @param file
	 * @return null
	 */
	private String saveFiles(MultipartFile file, String userName, String filePah, String pathType) throws IOException {
		FileUploadDTO fileUploadDTO = new FileUploadDTO();
		//获取文件相关信息
		byte[] bytes = file.getBytes();
		String filename = file.getOriginalFilename();
		int r = (int) ((Math.random() * 9 + 1) * 100000);
		String attachId = getTime() + String.valueOf(r).substring(0, 4);
		String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
		String newFilename = (attachId + "." + ext).toLowerCase();
		//保存文件
		String attachPath = null;
		if (StringUtils.isEmpty(filePah)) {
			attachPath = userName + File.separator + pathType;
		} else {
			attachPath = userName + File.separator + pathType + File.separator + filePah;
		}
		String resultPath = attachPath + File.separator + newFilename;
		String savePath = getRootPath(attachPath) + File.separator + newFilename;
		File fileToSave = new File(savePath);
		FileCopyUtils.copy(bytes, fileToSave);
		//保存数据记录
		fileUploadDTO.setFileName(filename);
		fileUploadDTO.setNewFileName(newFilename);
		fileUploadDTO.setFilePath(resultPath);
		fileUploadDTO.setFileSize(getFileSize(file.getSize(), "K"));
		fileUploadDTO.setUserName(userName);
		if (StringUtils.equals(FILE_PATH, pathType)) {
			fileUploadDTO.setFileType((short) 2);
		} else {
			fileUploadDTO.setFileType((short) 1);
		}
		fileUploadService.insert(fileUploadDTO);
		return resultPath;
	}

	private String getRootPath(String filePath) {
		String rootFile = ROOT_PATH + File.separator + filePath;
		log.info("文件路径：{}", rootFile);
		File file = new File(rootFile);
		if (!file.exists()) {
			log.info("不存在新建");
			file.mkdirs();
		}
		return rootFile;
	}

}
