package com.technicalinterest.group.service.impl;

import com.github.pagehelper.PageHelper;
import com.technicalinterest.group.dao.Article;
import com.technicalinterest.group.dao.User;
import com.technicalinterest.group.dto.ArticlesDTO;
import com.technicalinterest.group.dao.Content;
import com.technicalinterest.group.dto.QueryArticleDTO;
import com.technicalinterest.group.dto.UserBlogDTO;
import com.technicalinterest.group.mapper.ArticleMapper;
import com.technicalinterest.group.mapper.ContentMapper;
import com.technicalinterest.group.mapper.UserMapper;
import com.technicalinterest.group.service.ArticleService;
import com.technicalinterest.group.service.UserService;
import com.technicalinterest.group.service.constant.ArticleConstant;
import com.technicalinterest.group.service.Enum.ResultEnum;
import com.technicalinterest.group.service.dto.ArticleContentDTO;
import com.technicalinterest.group.service.dto.PageBean;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.exception.VLogException;
import com.technicalinterest.group.service.util.HtmlUtil;
import com.technicalinterest.group.service.util.RedisUtil;
import com.technicalinterest.group.service.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @package: com.technicalinterest.group.service.impl
 * @className: ArticleServiceImpl
 * @description: 文章服务层接口实现
 * @author: Shuyu.Wang
 * @date: 2019-08-04 15:06
 * @since: 0.1
 **/
@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ContentMapper contentMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Value("${submit_length}")
    private Integer ARTICLE_LENGTH;

    @Value("${article_jf}")
    private Integer JF;
    @Value("${article_limit}")
    private Integer ARTICLE_LIMIT;

    /**
     * @param articleContentDTO
     * @return ReturnClass
     * @Description: 新增文章
     * @author: shuyu.wang
     * @date: 2019-08-04 15:12
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnClass saveArticle(ArticleContentDTO articleContentDTO) {
        if (articleContentDTO.getId() != null) {
            ArticleServiceImpl articleService = SpringContextUtil.getBean(ArticleServiceImpl.class);
            ReturnClass returnClass = articleService.editArticle(articleContentDTO);
            if (articleContentDTO.getState() == 1) {
                returnClass.setMsg(ArticleConstant.SUS_ADD);
            } else {
                returnClass.setMsg(ArticleConstant.SUS_DRAFT);
            }
            return returnClass;
        }
        Article article = new Article();
        BeanUtils.copyProperties(articleContentDTO, article);
        Long userId = null;
        article.setUserName(userService.getUserNameByLoginToken());
        //文章摘要
        String summaryText = HtmlUtil.cleanHtmlTag(articleContentDTO.getContentFormat());
        article.setSubmit(summaryText.length() > ARTICLE_LENGTH ? summaryText.substring(0, ARTICLE_LENGTH - 1) : summaryText);
        article.setCreateTime(new Date());
        articleMapper.insertSelective(article);
        if (Objects.nonNull(article.getId()) && article.getId() > 0) {
            Content content = new Content();
            content.setArticleId(article.getId());
            content.setContent(articleContentDTO.getContent());
            content.setContentFormat(articleContentDTO.getContentFormat());
            int i = contentMapper.insertSelective(content);
            if (i < 1) {
                throw new VLogException(ArticleConstant.FAIL_ADD);
            }
            //增加积分
            if (articleContentDTO.getState() == 1) {
                ArticleServiceImpl articleService = SpringContextUtil.getBean(ArticleServiceImpl.class);
                articleService.addJF(article.getUserName(), article.getId());
            }
            return ReturnClass.success(ArticleConstant.SUS_ADD, article.getId());
        }
        return ReturnClass.fail(ArticleConstant.FAIL_ADD);
    }

    @Async
    public void addJF(String userName, Long articleId) {
        User user = User.builder().userName(userName).integral(JF).build();
        int update = userMapper.updateByUserName(user);
        if (update < 1) {
            log.error("博客发布增加积分失败，userName={},ArticleId={}", userName, articleId);
        }

    }

    /**
     * @param articleContentDTO
     * @return ReturnClass
     * @Description: 更新文章
     * @author: shuyu.wang
     * @date: 2019-08-04 15:12
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnClass editArticle(ArticleContentDTO articleContentDTO) {
        String userName = userService.getUserNameByLoginToken();
        Article article = new Article();
        BeanUtils.copyProperties(articleContentDTO, article);
        //数据是否存在
        Article param = new Article();
        param.setId(articleContentDTO.getId());
        Article articleResult = articleMapper.query(param);
        if (Objects.isNull(articleResult)) {
            throw new VLogException(ResultEnum.NO_DATA);
        }
        if (!StringUtils.equals(articleResult.getUserName(), userName)) {
            throw new VLogException(ResultEnum.NO_AUTH);
        }
        //文章摘要
        String summaryText = HtmlUtil.cleanHtmlTag(articleContentDTO.getContent());
        article.setSubmit(summaryText.length() > ARTICLE_LENGTH ? summaryText.substring(0, ARTICLE_LENGTH - 1) : summaryText);

        if (articleContentDTO.getState() == 1 && articleResult.getState() == 0) {
            ArticleServiceImpl articleService = SpringContextUtil.getBean(ArticleServiceImpl.class);
            articleService.addJF(article.getUserName(), article.getId());
            article.setCreateTime(new Date());
        }
        articleMapper.update(article);
        if (Objects.nonNull(article.getId()) && article.getId() > 0) {
            Content content = new Content();
            content.setArticleId(article.getId());
            content.setContent(articleContentDTO.getContent());
            content.setContentFormat(articleContentDTO.getContentFormat());
            content.setUpdateTime(new Date());
            int i = contentMapper.update(content);
            if (i < 1) {
                throw new VLogException(ArticleConstant.FAIL_EDIT);
            }
            return ReturnClass.success(ArticleConstant.SUS_EDIT, article.getId());
        }
        return ReturnClass.fail(ArticleConstant.FAIL_EDIT);
    }

    /**
     * @param queryArticleDTO
     * @return ReturnClass
     * @Description: 文章列表
     * @author: shuyu.wang
     * @date: 2019-08-04 15:12
     */
    @Override
    public ReturnClass listArticle(Boolean authCheck, String userName, QueryArticleDTO queryArticleDTO) {
        ReturnClass returnClass = userService.getUserByuserName(authCheck, userName);
        if (!returnClass.isSuccess()) {
            throw new VLogException(ResultEnum.NO_URL);
        }
        Integer integer = articleMapper.queryArticleListCount(queryArticleDTO);
        if (integer < 1) {
            return ReturnClass.fail(ArticleConstant.NO_BLOG);
        }
        PageHelper.startPage(queryArticleDTO.getCurrentPage(), queryArticleDTO.getPageSize());
        List<ArticlesDTO> articlesDTOS = articleMapper.queryArticleList(queryArticleDTO);
        PageBean<ArticlesDTO> pageBean = new PageBean<>(articlesDTOS, queryArticleDTO.getCurrentPage(), queryArticleDTO.getPageSize(), integer);
        return ReturnClass.success(pageBean);
    }

    /**
     * @param queryArticleDTO
     * @return ReturnClass
     * @Description: 根据登录用户查询
     * @author: shuyu.wang
     * @date: 2019-08-04 15:12
     */
    @Override
    public ReturnClass listArticleByLogin(QueryArticleDTO queryArticleDTO) {
        queryArticleDTO.setUserName(userService.getUserNameByLoginToken());
        Integer integer = articleMapper.queryArticleListCount(queryArticleDTO);
        if (integer < 1) {
            return ReturnClass.fail(ArticleConstant.NO_BLOG);
        }
        PageHelper.startPage(queryArticleDTO.getCurrentPage(), queryArticleDTO.getPageSize());
        List<ArticlesDTO> articlesDTOS = articleMapper.queryArticleList(queryArticleDTO);
        PageBean<ArticlesDTO> pageBean = new PageBean<>(articlesDTOS, queryArticleDTO.getCurrentPage(), queryArticleDTO.getPageSize(), integer);
        return ReturnClass.success(pageBean);
    }

    /**
     * @param id
     * @return null
     * @Description:获取文章详情
     * @author: shuyu.wang
     * @date: 2019-08-09 16:37
     */
    @Override
    public ReturnClass articleDetailView(Long id, String userName) {
        ArticleContentDTO articleContentDTO = new ArticleContentDTO();
        ArticlesDTO articleInfo = articleMapper.getArticleInfo(id, userName);
        if (Objects.isNull(articleInfo)) {
            throw new VLogException(ResultEnum.NO_URL);
        }
        BeanUtils.copyProperties(articleInfo, articleContentDTO);
        Content content = contentMapper.getContent(articleInfo.getId());
        if (Objects.nonNull(content)) {
            articleContentDTO.setContent(content.getContent());
            articleContentDTO.setContentFormat(content.getContentFormat());
        }
        return ReturnClass.success(articleContentDTO);
    }

    /**
     * @param id
     * @return null
     * @Description:获取文章详情
     * @author: shuyu.wang
     * @date: 2019-08-09 16:37
     */
    @Override
    public ReturnClass articleDetailByLogin(Long id) {
        String userName = userService.getUserNameByLoginToken();
        ArticleContentDTO articleContentDTO = new ArticleContentDTO();
        Article query = new Article();
        query.setId(id);
        Article result = articleMapper.query(query);
        if (Objects.isNull(result)) {
            throw new VLogException(ResultEnum.NO_URL);
        }
        if (!StringUtils.equals(userName, result.getUserName())) {
            throw new VLogException(ResultEnum.NO_AUTH);
        }
        BeanUtils.copyProperties(result, articleContentDTO);
        Content content = contentMapper.getContent(result.getId());
        if (Objects.nonNull(content)) {
            articleContentDTO.setContent(content.getContent());
            articleContentDTO.setContentFormat(content.getContentFormat());
        }
        return ReturnClass.success(articleContentDTO);
    }

    /**
     * @param queryArticleDTO
     * @return ReturnClass
     * @Description: 全站文章列表
     * @author: shuyu.wang
     * @date: 2019-08-04 15:12
     */
    @Override
    public ReturnClass allListArticle(QueryArticleDTO queryArticleDTO) {
        queryArticleDTO.setState((short) 1);

        Integer integer = articleMapper.queryArticleListCount(queryArticleDTO);
        if (integer < 1) {
            return ReturnClass.fail(ArticleConstant.NO_BLOG);
        }
        PageHelper.startPage(queryArticleDTO.getCurrentPage(), queryArticleDTO.getPageSize());
        List<ArticlesDTO> articlesDTOS = articleMapper.queryArticleList(queryArticleDTO);
        PageBean<ArticlesDTO> pageBean = new PageBean<>(articlesDTOS, queryArticleDTO.getCurrentPage(), queryArticleDTO.getPageSize(), integer);
        return ReturnClass.success(pageBean);
    }

    /**
     * @param flag 1:时间排序 2：阅读量排序  3：点赞数  4：评论数
     * @return ReturnClass
     * @Description: 按用户名查询文章列表
     * @author: shuyu.wang
     * @date: 2019-08-04 15:12
     */
    @Override
    public ReturnClass listArticleOrderBy( String userName, Integer flag) {
//        if (StringUtils.isNoneEmpty(userName)) {
//            ReturnClass returnClass = userService.getUserByuserName(authCheck, userName);
//            if (!returnClass.isSuccess()) {
//                throw new VLogException(ResultEnum.NO_URL);
//            }
//        }
        List<ArticlesDTO> articlesDTOS = articleMapper.queryArticleListOrderBy(flag, userName, ARTICLE_LIMIT);
        if (articlesDTOS.isEmpty()) {
            return ReturnClass.fail(ArticleConstant.NO_BLOG);
        }
        return ReturnClass.success(articlesDTOS);
    }

    /**
     * @param userName
     * @return null
     * @Description:博客文章归档
     * @author: shuyu.wang
     * @date: 2019/8/13 22:37
     */
    @Override
    public ReturnClass listArticleArchive(String userName) {
        List<ArticlesDTO> articlesDTOS = articleMapper.queryArticleListArchive(userName);
        if (articlesDTOS.isEmpty()) {
            return ReturnClass.fail(ArticleConstant.NO_BLOG);
        }
        return ReturnClass.success(articlesDTOS);
    }

    /**
     * @param id
     * @return null
     * @Description:删除文章
     * @author: shuyu.wang
     * @date: 2019-08-16 18:45
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnClass delArticle(Long id) {
        String userName = userService.getUserNameByLoginToken();
        //数据是否存在
        ArticlesDTO articleInfo = articleMapper.getArticleInfo(id, null);
        if (Objects.isNull(articleInfo)) {
            throw new VLogException(ResultEnum.NO_DATA);
        }
        if (!StringUtils.equals(articleInfo.getUserName(), userName)) {
            throw new VLogException(ResultEnum.NO_AUTH);
        }
        Integer integer = articleMapper.delArticle(id);
        if (integer > 0) {
            Integer integer1 = contentMapper.delContent(id);
            if (integer1 < 1) {
                log.error("文章详情删除失败，ArticleId={}", id);
            }
            return ReturnClass.success(ArticleConstant.SUS_DEL);
        } else {
            return ReturnClass.fail(ArticleConstant.FAIL_DEL);
        }

    }

    /**
     * @param articleContentDTO
     * @return null
     * @Description:更新文章的一些状态
     * @author: shuyu.wang
     * @date: 2019-08-16 18:45
     */
    @Override
    public ReturnClass updateArticleState(ArticleContentDTO articleContentDTO) {
        String userName = userService.getUserNameByLoginToken();
        //数据是否存在
        ArticlesDTO articleInfo = articleMapper.getArticleInfo(articleContentDTO.getId(), null);
        if (Objects.isNull(articleInfo)) {
            throw new VLogException(ResultEnum.NO_DATA);
        }
        if (!StringUtils.equals(articleInfo.getUserName(), userName)) {
            throw new VLogException(ResultEnum.NO_AUTH);
        }
        Article article = new Article();
        BeanUtils.copyProperties(articleContentDTO, article);
        Integer update = articleMapper.update(article);
        if (update > 0) {
            return ReturnClass.success();
        }
        return ReturnClass.fail();
    }

    /**
     * @param id
     * @return null
     * @Description:文章阅读数增加
     * @author: shuyu.wang
     * @date: 2019-08-17 19:17
     */
    @Override
    @Async
    public ReturnClass addReadCount(Long id) {
        Integer update = articleMapper.updateReadCount(id);
        if (update > 0) {
            log.info("文章阅读数累加成功！");
        } else {
            log.info("文章阅读数累加失败！");
        }
        return ReturnClass.success();
    }

    /**
     * 查询用户博客数据
     *
     * @param userName
     * @return
     */
    @Override
    public ReturnClass getBlogInfoByUser(String userName) {
        UserBlogDTO blogInfoByUser = articleMapper.getBlogInfoByUser(userName);
        return ReturnClass.success(blogInfoByUser);
    }
}
