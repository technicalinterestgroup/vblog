package com.technicalinterest.group.api.controller.admin;

import com.technicalinterest.group.api.param.NewWebsiteNoticeParam;
import com.technicalinterest.group.api.param.QueryWebsiteNoticeParam;
import com.technicalinterest.group.api.vo.ApiResult;
import com.technicalinterest.group.api.vo.websitenotice.WebsiteNoticeDetailVO;
import com.technicalinterest.group.api.vo.websitenotice.WebsiteNoticeVO;
import com.technicalinterest.group.dao.WebsiteNotice;
import com.technicalinterest.group.service.WebsiteNoticeService;
import com.technicalinterest.group.service.dto.PageBean;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.dto.WebsiteNoticeDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @ClassName: WebsiteNoticeController
 * @Author: shuyu.wang
 * @Description: 通知
 * @Date: 2020/3/23 13:17
 * @Version: 1.0
 */
@Api(tags = "網站通知")
@RestController
@RequestMapping("admin/website")
public class AdminWebsiteNoticeController {

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
            apiResult.success(carousels.getMsg(),carousels.getData());
        } else {
            apiResult.setMsg(carousels.getMsg());
        }
        return apiResult;
    }

    @ApiOperation(value = "发布通告")
    @GetMapping(value = "/list")
    public ApiResult<PageBean<WebsiteNotice>> getNoticeList(QueryWebsiteNoticeParam queryWebsiteNoticeParam ) {
        ApiResult apiResult = new ApiResult();
        WebsiteNoticeDTO websiteNoticeDTO=new WebsiteNoticeDTO();
        BeanUtils.copyProperties(queryWebsiteNoticeParam,websiteNoticeDTO);
        ReturnClass carousels = websiteNoticeService.getIndexWebsiteList(websiteNoticeDTO);
        if (carousels.isSuccess()) {
            apiResult.success(carousels.getData());
        } else {
            apiResult.setMsg(carousels.getMsg());
        }
        return apiResult;
    }

    @ApiOperation(value = "通告详情查询")
    @GetMapping(value = "/notice/{id}")
    public ApiResult<WebsiteNoticeDetailVO> getNoticeDetail(@PathVariable long id) {
        ApiResult apiResult = new ApiResult();
        ReturnClass carousels = websiteNoticeService.getWebsiteNoticeDetail(id);
        if (carousels.isSuccess()) {
            WebsiteNoticeDetailVO websiteNoticeDetailVO=new WebsiteNoticeDetailVO();
            BeanUtils.copyProperties(carousels.getData(),websiteNoticeDetailVO);
            apiResult.success(websiteNoticeDetailVO);
        } else {
            apiResult.setMsg(carousels.getMsg());
        }
        return apiResult;
    }


}
