package com.technicalinterest.group.api.controller;

import com.github.blackshadowwalker.spring.distributelock.annotation.DistributeLock;
import com.technicalinterest.group.api.param.PageBaseParam;
import com.technicalinterest.group.api.param.QueryFileParam;
import com.technicalinterest.group.api.vo.ApiResult;
import com.technicalinterest.group.api.vo.FileVO;
import com.technicalinterest.group.api.vo.ImgVO;
import com.technicalinterest.group.dao.PageBase;
import com.technicalinterest.group.dto.FileDTO;
import com.technicalinterest.group.dto.QueryFileDTO;
import com.technicalinterest.group.dto.UserRoleDTO;
import com.technicalinterest.group.service.AliyunOSSService;
import com.technicalinterest.group.service.FileUploadService;
import com.technicalinterest.group.service.UserService;
import com.technicalinterest.group.service.annotation.BlogOperation;
import com.technicalinterest.group.service.constant.RedisKeyConstant;
import com.technicalinterest.group.service.dto.FileUploadDTO;
import com.technicalinterest.group.service.dto.PageBean;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.dto.UserDTO;
import com.technicalinterest.group.service.exception.VLogException;
import com.technicalinterest.group.service.util.FileUtil;
import com.technicalinterest.group.service.util.RedisUtil;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private UserService userService;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private RedisUtil redisUtil;

    private static String IMG_TYPE = "png,jpg,jpeg,bmp,gif,";

    private static String DOC_TYPE_GIF = "doc,docx,xls,xlsx,pdf,zip,";

    private static final String IMG_TYPE_ERROR = "文件格式不支持！只允许上传jpg,jng,jpeg,bmp,gif格式的图片！";

    private static final String DOC_TYPE_ERROR = "文件格式不支持！只允许上传doc,docx,xls,xlsx,pdf,zip格式的文件！";

    private static final String SIZE_ERROR = "文件过大！";

    private static final Integer IMG_FILE_SIZE = 1024;

    private static final Integer DOC_FILE_SIZE = 600;

    private static final String FILE_EMPTY = "文件不能为空！";

    private static final String UNIT = "K";

    @Autowired
    private AliyunOSSService aliyunOSSService;

    @ApiOperation(value = "图片上传", notes = "图片上传")
    @PostMapping(value = "/img/upload")
    public ApiResult<String> uploadImg(@RequestParam(value = "file") MultipartFile file) {
        //次数校验
        String userName= checkeUploadTime();
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
        if (!FileUtil.checkFileSize(file.getSize(), IMG_FILE_SIZE, UNIT)) {
            apiResult.fail(SIZE_ERROR);
            return apiResult;
        }
        try {
            String s = aliyunOSSService.uploadImg2Oss(file, userName);
            apiResult.success(s);
            redisUtil.decr(RedisKeyConstant.uploadTimeKey(userName),1);
        } catch (Exception e) {
            log.error("图片上传异常", e);
            apiResult.fail("图片上传异常");
        }
        return apiResult;
    }

    @RequestMapping(value = "/avatar/upload", method = RequestMethod.POST)
    public ApiResult<String> uploadUserAvatar(MultipartHttpServletRequest request, HttpServletResponse response) {
        //次数校验
        String userName=  checkeUploadTime();
        ApiResult<String> apiResult = new ApiResult<>();
        //得到文件map对象
        Map<String, MultipartFile> files = request.getFileMap();
        for (MultipartFile pic : files.values()) {
            try {
                String s = aliyunOSSService.uploadImg2Oss(pic, userName);
                redisUtil.decr(RedisKeyConstant.uploadTimeKey(userName),1);
                apiResult.success(s);
            } catch (Exception e) {
                log.error("头像上传异常", e);
                apiResult.fail("头像上传异常");
            }
        }
        return apiResult;
    }

    @ApiOperation(value = "文件上传", notes = "文件上传")
    @PostMapping(value = "/file/upload")
    @BlogOperation(value = "文件上传")
    public ApiResult<String> uploadFile(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "filePaht", required = false) String filePaht) {
        String userName = userService.getUserNameByLoginToken();
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
            if (!FileUtil.checkFileSize(file.getSize(), DOC_FILE_SIZE, UNIT)) {
                apiResult.fail(SIZE_ERROR);
                return apiResult;
            }
//            String s = saveFiles(file, userName, filePaht, FILE_PATH);
//            apiResult.success("文件上传成功", s);
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
    public ApiResult<PageBean<FileVO>> listFile(@PathVariable("userName") String userName, QueryFileParam queryFileParam) {
        ApiResult apiResult = new ApiResult();
        QueryFileDTO queryFileDTO = new QueryFileDTO();
        BeanUtils.copyProperties(queryFileParam, queryFileDTO);
        queryFileDTO.setUserName(userName);
        ReturnClass listArticle = fileUploadService.queryFileList(queryFileDTO);
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
        QueryFileDTO queryFileDTO = new QueryFileDTO();
        BeanUtils.copyProperties(pageBaseParam, queryFileDTO);
        queryFileDTO.setUserName(userName);
        queryFileDTO.setFileType((short) 1);
        ReturnClass listArticle = fileUploadService.queryFileList(queryFileDTO);
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


    private String checkeUploadTime(){
        String username= userService.getUserNameByLoginToken();
        int o = (Integer)redisUtil.get(RedisKeyConstant.uploadTimeKey(username));
        if (o<=0){
            throw new VLogException("上传次数达到上限，请联系管理员充值!");
        }
        return username;
    }


}
