package com.technicalinterest.group.api.util;

import com.technicalinterest.group.api.param.QueryArticleParam;
import com.technicalinterest.group.service.Enum.ArticleOrderEnum;

import java.util.Objects;

/**
 * @ClassName: IndexOrderByUtil
 * @Author: shuyu.wang
 * @Description: 首页文章排序工具类
 * @Date: 2020/3/6 11:46
 * @Version: 1.0
 */
public class IndexOrderByUtil {

    public static Integer getOrderByFlage(QueryArticleParam queryArticleParam){
        if (Objects.nonNull(queryArticleParam.getNewTime())&&queryArticleParam.getNewTime()){
            return ArticleOrderEnum.NEW.getOrderByFlag();
        }
        if (Objects.nonNull(queryArticleParam.getHot())&&queryArticleParam.getHot()){
            return ArticleOrderEnum.HOT.getOrderByFlag();
        }
        if (Objects.nonNull(queryArticleParam.getLike())&&queryArticleParam.getLike()){
            return ArticleOrderEnum.Recommend.getOrderByFlag();
        }
        if (Objects.nonNull(queryArticleParam.getComment())&&queryArticleParam.getComment()){
            return ArticleOrderEnum.Comment.getOrderByFlag();
        }
        return ArticleOrderEnum.NEW.getOrderByFlag();
    }
}
