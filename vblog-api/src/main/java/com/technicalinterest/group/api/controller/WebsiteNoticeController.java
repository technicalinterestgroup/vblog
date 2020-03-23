package com.technicalinterest.group.api.controller;

import com.technicalinterest.group.api.param.NewWebsiteNoticeParam;
import com.technicalinterest.group.api.vo.ApiResult;
import com.technicalinterest.group.api.vo.websitenotice.WebsiteNoticeVO;
import com.technicalinterest.group.service.WebsiteNoticeService;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.dto.WebsiteNoticeDTO;
import com.technicalinterest.group.service.util.ListBeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: WebsiteNoticeController
 * @Author: shuyu.wang
 * @Description: 通知
 * @Date: 2020/3/23 13:17
 * @Version: 1.0
 */
@Api(tags = "網站通知")
@RestController
@RequestMapping("website")
public class WebsiteNoticeController {

    @Autowired
    private WebsiteNoticeService websiteNoticeService;

    @ApiOperation(value = "发布通告")
    @PostMapping(value = "/new")
    public ApiResult<String> newNotice(@RequestBody NewWebsiteNoticeParam newWebsiteNoticeParam ) {
        ApiResult apiResult = new ApiResult();
        WebsiteNoticeDTO websiteNoticeDTO=new WebsiteNoticeDTO();
        BeanUtils.copyProperties(newWebsiteNoticeParam,websiteNoticeDTO);
        ReturnClass carousels = websiteNoticeService.saveOrUpdate(websiteNoticeDTO);
        if (carousels.isSuccess()) {
            apiResult.success(carousels.getMsg());
        } else {
            apiResult.setMsg(carousels.getMsg());
        }
        return apiResult;
    }


}
