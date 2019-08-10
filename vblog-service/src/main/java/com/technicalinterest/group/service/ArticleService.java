package com.technicalinterest.group.service;

import com.technicalinterest.group.dao.QueryArticleDTO;
import com.technicalinterest.group.service.dto.ArticleContentDTO;
import com.technicalinterest.group.service.dto.ReturnClass;

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
	 * @Description: 更新文章
	 * @author: shuyu.wang
	 * @date: 2019-08-04 15:12
	 * @param queryArticleDTO
	 * @return ReturnClass
	 */
	ReturnClass listArticle(String userName,QueryArticleDTO queryArticleDTO);

    /**
     * @Description:获取文章详情
     * @author: shuyu.wang
     * @date: 2019-08-09 16:37
     * @param id
     * @return null
    */
	ReturnClass articleDetail(Long id);
}
