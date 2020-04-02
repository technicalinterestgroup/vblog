package com.technicalinterest.group.api.controller.admin;

import com.technicalinterest.group.api.param.EditArticleContentParam;
import com.technicalinterest.group.api.param.QueryArticleAdminParam;
import com.technicalinterest.group.api.vo.ApiResult;
import com.technicalinterest.group.api.vo.ArticleContentVO;
import com.technicalinterest.group.api.vo.ArticlesAdminVO;
import com.technicalinterest.group.dto.ArticlesDTO;
import com.technicalinterest.group.dto.QueryArticleDTO;
import com.technicalinterest.group.service.AdminService;
import com.technicalinterest.group.service.ArticleService;
import com.technicalinterest.group.service.annotation.BlogOperation;
import com.technicalinterest.group.service.annotation.VBlogReadCount;
import com.technicalinterest.group.service.dto.ArticleContentDTO;
import com.technicalinterest.group.service.dto.PageBean;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.util.ListBeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @ClassName: AdminAuthController
 * @Author: shuyu.wang
 * @Description: 菜单接口权限
 * @Date: 2020/3/24 12:00
 * @Version: 1.0
 */
@Api(tags = "文章")
@RestController
@RequestMapping("admin/article")
public class AdminArticleController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private ArticleService articleService;


    @ApiOperation(value = "博客列表", notes = "博客列表")
    @GetMapping(value = "/blog/list")
    public ApiResult<PageBean<ArticlesAdminVO>> listBlog(QueryArticleAdminParam queryArticleAdminParam) {
        ApiResult apiResult = new ApiResult();
        QueryArticleDTO queryArticleDTO = new QueryArticleDTO();
        BeanUtils.copyProperties(queryArticleAdminParam, queryArticleDTO);
        ReturnClass listUser = adminService.articleAll(queryArticleDTO);
        if (listUser.isSuccess()) {
            PageBean<ArticlesAdminVO> result = new PageBean<ArticlesAdminVO>();
            PageBean<ArticlesDTO> pageBean = (PageBean<ArticlesDTO>) listUser.getData();
            List list = ListBeanUtils.copyProperties(pageBean.getPageData(), ArticlesAdminVO.class);
            BeanUtils.copyProperties(pageBean, result);
            result.setPageData(list);
            apiResult.success(result);
        } else {
            apiResult.setMsg(listUser.getMsg());
        }
        return apiResult;
    }

    @ApiOperation(value = "博客编辑", notes = "博客编辑")
    @PostMapping(value = "/edit")
    @BlogOperation(value = "博客编辑")
    public ApiResult<String> editArticle(@Valid @RequestBody EditArticleContentParam editArticleContentParam) {
        ApiResult apiResult = new ApiResult();
        ArticleContentDTO articleContentDTO = new ArticleContentDTO();
        BeanUtils.copyProperties(editArticleContentParam, articleContentDTO);
        ReturnClass editArticle = adminService.editArticle(articleContentDTO);
        if (editArticle.isSuccess()) {
            apiResult.success(editArticle.getMsg(), null);
        } else {
            apiResult.fail(editArticle.getMsg());
        }
        return apiResult;
    }

    @ApiOperation(value = "文章详情", notes = "文章详情")
    @GetMapping(value = "/detail/{id}")
    @VBlogReadCount
    public ApiResult<ArticleContentVO> articleDetail(@PathVariable("id") Long id) {
        ApiResult apiResult = new ApiResult();
        ReturnClass articleDetail = articleService.articleDetailView(id,null);
        ArticleContentVO articleContentVO = new ArticleContentVO();
        if (articleDetail.isSuccess()) {
            BeanUtils.copyProperties(articleDetail.getData(), articleContentVO);
            apiResult.success(articleContentVO);

        } else {
            apiResult.setMsg(articleDetail.getMsg());
        }
        return apiResult;
    }

}
