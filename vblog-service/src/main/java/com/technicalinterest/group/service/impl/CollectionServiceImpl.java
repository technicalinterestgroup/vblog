package com.technicalinterest.group.service.impl;

import com.github.pagehelper.PageHelper;
import com.technicalinterest.group.dao.Ask;
import com.technicalinterest.group.dao.CollectionArticle;
import com.technicalinterest.group.dao.PageBase;
import com.technicalinterest.group.dao.WebsiteNotice;
import com.technicalinterest.group.dto.ArticlesDTO;
import com.technicalinterest.group.dto.CollectionDTO;
import com.technicalinterest.group.mapper.ArticleMapper;
import com.technicalinterest.group.mapper.AskMapper;
import com.technicalinterest.group.mapper.CollectionMapper;
import com.technicalinterest.group.mapper.WebsiteNoticeMapper;
import com.technicalinterest.group.service.CollectionService;
import com.technicalinterest.group.service.Enum.TypeEnum;
import com.technicalinterest.group.service.UserService;
import com.technicalinterest.group.service.constant.CollectionConstant;
import com.technicalinterest.group.service.Enum.ResultEnum;
import com.technicalinterest.group.service.dto.LikeDTO;
import com.technicalinterest.group.service.dto.PageBean;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.exception.VLogException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author wangshuyu
 */
@Service
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    private CollectionMapper collectionMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private WebsiteNoticeMapper websiteNoticeMapper;
	@Autowired
    private AskMapper askMapper;
    @Override
    public ReturnClass insert(LikeDTO likeDTO) {
        String userName = userService.getUserNameByLoginToken();
        WebsiteNotice websiteNotice = null;
        //博客
        if (likeDTO.getType() == TypeEnum.BLOG.getCode()) {
            ArticlesDTO articleInfo = articleMapper.getArticleInfo(likeDTO.getSourceId(), null);
            if (Objects.isNull(articleInfo)) {
                throw new VLogException(ResultEnum.NO_URL);
            }
            //通知
        } else if (likeDTO.getType() == TypeEnum.NOTICE.getCode()) {
            websiteNotice = websiteNoticeMapper.websiteNoticeById(likeDTO.getSourceId());
            if (Objects.isNull(websiteNotice)) {
                throw new VLogException(ResultEnum.NO_URL);
            }
            //问答
        } else if (likeDTO.getType() == TypeEnum.ASK.getCode()) {
			Ask askById = askMapper.getAskById(likeDTO.getSourceId());
			if (Objects.isNull(askById)) {
                throw new VLogException(ResultEnum.NO_URL);
            }
        } else {
            return ReturnClass.fail("类型不匹配");
        }
        CollectionArticle collectionArticlePa = CollectionArticle.builder().userName(userName).type(likeDTO.getType()).sourceId(likeDTO.getSourceId()).build();
        CollectionArticle collectionArticle2 = collectionMapper.queryCollection(collectionArticlePa);
        if (Objects.nonNull(collectionArticle2)) {
            return ReturnClass.success(CollectionConstant.ADD_REPAT);
        }
        int insert = collectionMapper.insertSelective(collectionArticlePa);
        if (insert > 0) {
            return ReturnClass.success(CollectionConstant.SUS_ADD);
        }
        return ReturnClass.fail(CollectionConstant.FAIL_ADD);
    }

    @Override
    public ReturnClass del(LikeDTO likeDTO) {
        String userName = userService.getUserNameByLoginToken();
        CollectionArticle collectionArticlePa = CollectionArticle.builder().userName(userName).type(likeDTO.getType()).sourceId(likeDTO.getSourceId()).build();
        CollectionArticle collectionArticle = collectionMapper.queryCollection(collectionArticlePa);
        if (Objects.isNull(collectionArticle)) {
            throw new VLogException(ResultEnum.NO_URL);
        }
        if (!StringUtils.equals(userName, collectionArticle.getUserName())) {
            throw new VLogException(ResultEnum.NO_AUTH);
        }
        Integer integer = collectionMapper.delCollection(collectionArticle.getId());
        if (integer > 0) {
            return ReturnClass.success(CollectionConstant.SUS_DEL);
        }
        return ReturnClass.success(CollectionConstant.FAIL_DEL);
    }

    /**
     * @param
     * @return com.technicalinterest.group.service.dto.ReturnClass
     * @Description:查询收藏列表
     * @author: shuyu.wang
     * @date: 2019/8/21 22:50
     */
    @Override
    public ReturnClass queryListCollection(Short type, PageBase pageBase) {
        String userName = userService.getUserNameByLoginToken();
        Integer integer = collectionMapper.queryCountCollectionByUserName(type, userName);
        if (integer > 0) {
            PageHelper.startPage(pageBase.getCurrentPage(), pageBase.getPageSize());
            List<CollectionDTO> collectionDTOS = collectionMapper.queryListCollectionByUserName(type, userName);
            PageBean<CollectionDTO> pageBean = new PageBean<>(collectionDTOS, pageBase.getCurrentPage(), pageBase.getPageSize(), integer);
            return ReturnClass.success(pageBean);
        }
        return ReturnClass.fail(CollectionConstant.NO_DATA);
    }
}
