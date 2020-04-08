package com.technicalinterest.group.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.technicalinterest.group.api.param.AskParam;
import com.technicalinterest.group.api.param.QueryAskParam;
import com.technicalinterest.group.api.param.ReplayParam;
import com.technicalinterest.group.api.vo.ApiResult;
import com.technicalinterest.group.api.vo.AskListVO;
import com.technicalinterest.group.dao.Ask;
import com.technicalinterest.group.dao.Reply;
import com.technicalinterest.group.service.ReplayService;
import com.technicalinterest.group.service.annotation.BlogOperation;
import com.technicalinterest.group.service.dto.AskDTO;
import com.technicalinterest.group.service.dto.PageBean;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.util.ListBeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @ClassName: ReplyController
 * @Author: shuyu.wang
 * @Description: 问答回复控制层
 * @Date: 2020/4/1 13:24
 * @Version: 1.0
 */
@Api(tags = "问答回复")
@RestController
@RequestMapping("reply")
@Slf4j
public class ReplyController {
    @Autowired
    private ReplayService replayService;

    @ApiOperation(value = "问题回答")
    @PostMapping(value = "/new")
    @BlogOperation(value = "问题回答")
    public ApiResult<String> saveAsk(@Valid @RequestBody ReplayParam replayParam) {
        log.info("问题问题回答 参数{}", JSONObject.toJSON(replayParam));
        ApiResult apiResult = new ApiResult();
        Reply reply=new Reply();
        BeanUtils.copyProperties(replayParam, reply);
        ReturnClass save = replayService.saveReply(reply);
        if (save.isSuccess()) {
            apiResult.success(save.getMsg());
        } else {
            apiResult.fail(save.getMsg());
        }
        return apiResult;
    }

    @ApiOperation(value = "采纳答案")
    @GetMapping(value = "/adoption")
    @BlogOperation(value = "采纳答案")
    public ApiResult<String> adoption(@RequestParam(value = "id")Long id) {
        log.info("采纳答案 参数{}", id);
        ApiResult apiResult = new ApiResult();
        Reply reply=new Reply();
        ReturnClass save = replayService.acceptionReply(id);
        if (save.isSuccess()) {
            apiResult.success(save.getMsg());
        } else {
            apiResult.fail(save.getMsg());
        }
        return apiResult;
    }

}
