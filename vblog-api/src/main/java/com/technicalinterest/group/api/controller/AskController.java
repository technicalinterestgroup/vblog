package com.technicalinterest.group.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.blackshadowwalker.spring.distributelock.annotation.DistributeLock;
import com.technicalinterest.group.api.param.AskParam;
import com.technicalinterest.group.api.param.NewArticleContentParam;
import com.technicalinterest.group.api.param.QueryAskParam;
import com.technicalinterest.group.api.vo.ApiResult;
import com.technicalinterest.group.api.vo.AskListVO;
import com.technicalinterest.group.api.vo.AskVO;
import com.technicalinterest.group.dao.Ask;
import com.technicalinterest.group.service.AskService;
import com.technicalinterest.group.service.annotation.BlogOperation;
import com.technicalinterest.group.service.dto.ArticleContentDTO;
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
 * @ClassName: AskController
 * @Author: shuyu.wang
 * @Description: 问答控制层
 * @Date: 2020/3/31 17:24
 * @Version: 1.0
 */
@Api(tags = "问答管理")
@RestController
@RequestMapping("ask")
@Slf4j
public class AskController {
    @Autowired
    private AskService askService;

    @ApiOperation(value = "问题发布")
    @PostMapping(value = "/new")
    @BlogOperation(value = "问题发布")
    public ApiResult<Long> saveAsk(@Valid @RequestBody AskParam askParam) {
        log.info("问题发布 参数{}", JSONObject.toJSON(askParam));
        ApiResult apiResult = new ApiResult();
        Ask ask=new Ask();
        BeanUtils.copyProperties(askParam, ask);
        ReturnClass saveArticle = askService.saveOrUpdateAsk(ask);
        if (saveArticle.isSuccess()) {
            apiResult.success(saveArticle.getData());
        } else {
            apiResult.fail(saveArticle.getMsg());
        }
        return apiResult;
    }

    @ApiOperation(value = "问题列表")
    @PostMapping(value = "/list")
    @BlogOperation(value = "问题列表")
    public ApiResult<PageBean<AskListVO>> getAskList(QueryAskParam queryAskParam) {
        log.info("问题发布 参数{}", JSONObject.toJSON(queryAskParam));
        ApiResult apiResult = new ApiResult();
        AskDTO ask=new AskDTO();
        BeanUtils.copyProperties(queryAskParam, ask);
        ReturnClass<PageBean<com.technicalinterest.group.dto.AskDTO>> saveArticle = askService.getAskPageByToken(ask);
        if (saveArticle.isSuccess()) {
            PageBean<AskListVO> result=new  PageBean<AskListVO>();
            PageBean<com.technicalinterest.group.dto.AskDTO> pageBean = saveArticle.getData();
            List list = ListBeanUtils.copyProperties(pageBean.getPageData(), AskListVO.class);
            BeanUtils.copyProperties(pageBean,result);
            result.setPageData(list);
            apiResult.success(result);
        } else {
            apiResult.fail(saveArticle.getMsg());
        }
        return apiResult;
    }

    @ApiOperation(value = "问题详情")
    @PostMapping(value = "/detail/{id}")
    @BlogOperation(value = "问题详情")
    public ApiResult<AskVO> getAskDetail(@PathVariable Long id) {
        ApiResult apiResult = new ApiResult();
        ReturnClass<Ask> saveArticle = askService.getAskDetailByToken(id);
        if (saveArticle.isSuccess()) {
            AskVO askVO=new AskVO();
            BeanUtils.copyProperties(saveArticle.getData(),askVO);
            apiResult.success(askVO);
        } else {
            apiResult.fail(saveArticle.getMsg());
        }
        return apiResult;
    }
}
