package com.technicalinterest.group.service;

import com.technicalinterest.group.dao.FileUpload;
import com.technicalinterest.group.dto.QueryArticleDTO;
import com.technicalinterest.group.dto.QueryFileDTO;
import com.technicalinterest.group.dto.QueryLogDTO;
import com.technicalinterest.group.service.dto.ReturnClass;

/**
 * @package: com.technicalinterest.group.service
 * @className: AdminService
 * @description: 管理员service
 * @author: Shuyu.Wang
 * @date: 2019-09-01 15:28
 * @since: 0.1
 **/

public interface AdminService {

    /**
     * @Description:查询全部博客用户
     * @author: shuyu.wang
     * @date: 2019-09-01 15:29
     * @return null
    */
	ReturnClass userAll();

    /**
     * @Description:查询全部文章
     * @author: shuyu.wang
     * @date: 2019-09-01 16:43
     * @param queryArticleDTO
     * @return null
    */
	ReturnClass articleAll(QueryArticleDTO queryArticleDTO);

	/**
	 * @Description:查询全部文件
	 * @author: shuyu.wang
	 * @date: 2019-09-01 16:43
	 * @param queryFileDTO
	 * @return null
	 */
	ReturnClass fileAll(QueryFileDTO queryFileDTO);

	/**
	 * @Description:查询全部文件
	 * @author: shuyu.wang
	 * @date: 2019-09-01 16:43
	 * @param queryLogDTO
	 * @return null
	 */
	ReturnClass logAll(QueryLogDTO queryLogDTO);
}
