package com.technicalinterest.group.service;

import com.technicalinterest.group.dto.ArticlesDTO;
import com.technicalinterest.group.dto.QueryArticleDTO;
import com.technicalinterest.group.service.dto.ArticleContentDTO;
import com.technicalinterest.group.service.dto.ReturnClass;

import java.util.List;

/**
 * @package: com.technicalinterest.group.service
 * @className: ArticleService
 * @description: 文章服务层接口
 * @author: Shuyu.Wang
 * @date: 2019-08-04 15:06
 * @since: 0.1
 **/

public interface ArticleService {


	/**
	 * @Description: 新增文章
	 * @author: shuyu.wang
	 * @date: 2019-08-04 15:12
	 * @param articleContentDTO
	 * @return ReturnClass
	*/
	ReturnClass saveArticle(ArticleContentDTO articleContentDTO);


	/**
	 * @Description: 更新文章
	 * @author: shuyu.wang
	 * @date: 2019-08-04 15:12
	 * @param articleContentDTO
	 * @return ReturnClass
	 */
	ReturnClass editArticle(ArticleContentDTO articleContentDTO);


	/**
	 * @Description: 按用户名查询文章列表
	 * @author: shuyu.wang
	 * @date: 2019-08-04 15:12
	 * @param queryArticleDTO
	 * @return ReturnClass
	 */
	ReturnClass listArticle(Boolean authCheck,String userName,QueryArticleDTO queryArticleDTO);

    /**
     * @Description:获取文章详情
     * @author: shuyu.wang
     * @date: 2019-08-09 16:37
     * @param id
     * @return null
    */
	ReturnClass articleDetail(Boolean authCheck,Long id);

	/**
	 * @Description: 全站文章列表
	 * @author: shuyu.wang
	 * @date: 2019-08-04 15:12
	 * @param queryArticleDTO
	 * @return ReturnClass
	 */
	ReturnClass allListArticle(QueryArticleDTO queryArticleDTO);

	/**
	 * @Description: 按用户名查询文章列表
	 * @author: shuyu.wang
	 * @date: 2019-08-04 15:12
	 * @param flag 1:时间排序 2：阅读量排序  3：点赞数  4：评论数
	 * @return ReturnClass
	 */
	ReturnClass listArticleOrderBy(Boolean authCheck,String userName,Integer flag);

	/**
	 * @Description:博客文章归档
	 * @author: shuyu.wang
	 * @date: 2019/8/13 22:37
	 * @param userName
	 * @return null
	*/
	ReturnClass  listArticleArchive(String userName);

    /**
     * @Description:删除文章
     * @author: shuyu.wang
     * @date: 2019-08-16 18:45
     * @param id
     * @return null
    */
	ReturnClass delArticle(Long id);

	/**
	 * @Description:更新文章的一些状态
	 * @author: shuyu.wang
	 * @date: 2019-08-16 18:45
	 * @param articleContentDTO
	 * @return null
	 */
	ReturnClass updateArticleState(ArticleContentDTO articleContentDTO);


}
