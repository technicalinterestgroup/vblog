package com.technicalinterest.group.mapper;

import com.technicalinterest.group.dto.ArticlesDTO;
import com.technicalinterest.group.dto.QueryArticleDTO;
import com.technicalinterest.group.dto.UserBlogDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.technicalinterest.group.dao.Article;

@Mapper
public interface ArticleMapper {
    Integer insert(@Param("pojo") Article pojo);

    Integer insertSelective(@Param("pojo") Article pojo);

    Integer insertList(@Param("pojos") List<Article> pojo);

    Integer update(@Param("pojo") Article pojo);

    Article query(@Param("pojo") Article pojo);
    
    /**
     * @Description: 列表+摘要
     * @author: shuyu.wang
     * @date: 2019-08-05 13:25
     * @param queryArticleDTO
     * @return null
    */
    List<ArticlesDTO> queryArticleList(@Param("pojo")QueryArticleDTO queryArticleDTO);


    /**
     * @Description: 条数
     * @author: shuyu.wang
     * @date: 2019-08-05 13:25
     * @param queryArticleDTO
     * @return null
     */
    Integer queryArticleListCount(@Param("pojo")QueryArticleDTO queryArticleDTO);


    /**
     * @Description:
     * @author: shuyu.wang
     * @date: 2019-08-08 13:24
     * @param id
     * @return null
    */
    ArticlesDTO getArticleInfo(@Param("id")Long id,@Param("userName")String userName);

    /**
     * @Description: 文章名列表
     * @author: shuyu.wang
     * @date: 2019-08-05 13:25
     * @param flag 1:时间排序 2：阅读量排序  3：点赞数  4：评论数
     * @param userName
     * @return null
     */
    List<ArticlesDTO> queryArticleListOrderBy(@Param("flag")Integer flag,@Param("userName")String userName,@Param("total")Integer total);


    /**
     * @Description: 文章归档
     * @author: shuyu.wang
     * @date: 2019-08-13 13:25
     * @param userName
     * @return null
    */
    List<ArticlesDTO> queryArticleListArchive(@Param("userName")String userName);


    /**
     * @Description:删除文章
     * @author: shuyu.wang
     * @date: 2019-08-16 18:41
     * @param id
     * @return null
    */
    Integer delArticle(@Param("id")Long id);
    /**
     * @Description:更新文章阅读数量
     * @author: shuyu.wang
     * @date: 2019-08-16 18:41
     * @param id
     * @return null
     */
    Integer updateReadCount(@Param("id")Long id);


    /**
     * @Description: 管理员查询列表+摘要
     * @author: shuyu.wang
     * @date: 2019-08-05 13:25
     * @param queryArticleDTO
     * @return null
     */
    List<ArticlesDTO> allArticleList(@Param("pojo")QueryArticleDTO queryArticleDTO);


    /**
     * @Description: 管理员查询条数
     * @author: shuyu.wang
     * @date: 2019-08-05 13:25
     * @param queryArticleDTO
     * @return null
     */
    Integer allArticleListCount(@Param("pojo")QueryArticleDTO queryArticleDTO);

    /**
     * 获取用户博客数据
     * @param userName
     * @return
     */
    UserBlogDTO getBlogInfoByUser(@Param("userName")String userName);



}
