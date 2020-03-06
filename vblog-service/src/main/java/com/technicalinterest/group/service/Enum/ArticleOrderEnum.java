package com.technicalinterest.group.service.Enum;

/**
 * @ClassName: ArticleOrderEnum
 * @Author: shuyu.wang
 * @Description: 文章排序类型枚举
 * @Date: 2020/3/6 10:08
 * @Version: 1.0
 */
public enum ArticleOrderEnum {

    NEW(1,"最新!"),
    HOT(2,"阅读量!"),
    Recommend(3,"点赞量!"),
    Comment(4,"评论量!");

    private Integer orderByFlag;
    private String orderByDesc;

    ArticleOrderEnum(Integer orderByFlag, String orderByDesc) {
        this.orderByFlag = orderByFlag;
        this.orderByDesc = orderByDesc;
    }

    public Integer getOrderByFlag() {
        return orderByFlag;
    }

    public String getOrderByDesc() {
        return orderByDesc;
    }
}
