package com.technicalinterest.group.service;

import com.technicalinterest.group.dao.PageBase;
import com.technicalinterest.group.dao.UserFocus;
import com.technicalinterest.group.dto.UserFocusDTO;
import com.technicalinterest.group.service.dto.PageBean;
import com.technicalinterest.group.service.dto.ReturnClass;

import java.util.List;

/**
 * @package: com.technicalinterest.group.service
 * @className: UserFocus
 * @description: 用戶关注关系服务
 * @author: Shuyu.Wang
 * @date: 2020-04-12 19:14
 * @since: 0.1
 **/
public interface UserFocusService {

	/**
	 * 关注
	 * @param focusUserName
	 * @return
	 */
	ReturnClass<String> addNewFocus(String focusUserName);
	/**
	 * 取消关注
	 * @param focusUserName
	 * @return
	 */
	ReturnClass<String> cancelFocus(String focusUserName);

	/**
	 * 查询你关注的
	 * @return
	 */
	ReturnClass<PageBean<UserFocusDTO>> getYourFocus(PageBase pageBase);

	/**
	 * 查询你的粉丝
	 * @return
	 */
	ReturnClass<PageBean<UserFocusDTO>> getFocusYou(PageBase pageBase);

	/**
	 * 查询是否关注过
	 * @param userName
	 * @param focusUserName
	 * @return
	 */
	ReturnClass<String> isFocus(String userName,String focusUserName);

}
