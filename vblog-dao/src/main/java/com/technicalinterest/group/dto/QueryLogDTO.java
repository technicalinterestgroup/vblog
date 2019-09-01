package com.technicalinterest.group.dto;

import com.technicalinterest.group.dao.BaseDao;
import com.technicalinterest.group.dao.PageBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @package: com.technicalinterest.group.dao
 * @className: Log
 * @description: 日志管理
 * @author: Shuyu.Wang
 * @date: 2019-09-01 19:20
 * @since: 0.1
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueryLogDTO extends PageBase {

	private String userName;

	private String classMethod;

	private String operation;



}
