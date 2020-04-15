package com.technicalinterest.group.service.impl;

import com.github.pagehelper.PageHelper;
import com.technicalinterest.group.dao.PageBase;
import com.technicalinterest.group.dao.UserFocus;
import com.technicalinterest.group.dto.UserFocusDTO;
import com.technicalinterest.group.mapper.UserFocusMapper;
import com.technicalinterest.group.service.Enum.ResultEnum;
import com.technicalinterest.group.service.UserFocusService;
import com.technicalinterest.group.service.UserService;
import com.technicalinterest.group.service.dto.PageBean;
import com.technicalinterest.group.service.dto.ReturnClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @package: com.technicalinterest.group.service.impl
 * @className: UserFocusServiceImpl
 * @description:
 * @author: Shuyu.Wang
 * @date: 2020-04-12 19:17
 * @since: 0.1
 **/
@Service
public class UserFocusServiceImpl implements UserFocusService{
	@Autowired
	private UserFocusMapper userFocusMapper;
	@Autowired
	private UserService userService;

	/**
	 * 关注
	 * @param focusUserName
	 * @return
	 */
	@Override
	public ReturnClass<String> addNewFocus(String focusUserName) {
		UserFocus query=new UserFocus();
		query.setUserName(userService.getUserNameByLoginToken());
		query.setFocusUserName(focusUserName);
		UserFocus userFocus = userFocusMapper.selectOneUserFocus(query);
		if (Objects.isNull(userFocus)){
			query.setCreateTime(new Date());
			query.setState(true);
			query.setIsDel((short) 0);
			int i = userFocusMapper.insertSelective(query);
			if (i>0){
				return ReturnClass.success("关注成功");
			}
		}else {
			if(userFocus.getState()){
				return ReturnClass.success("已关注过该用户");
			}else {
				userFocus.setState(true);
				userFocus.setCreateTime(new Date());
				userFocus.setUpdateTime(new Date());
				int update = userFocusMapper.update(userFocus);
				if (update>0){
					return ReturnClass.success("关注成功");
				}
			}
		}
		return ReturnClass.fail("关注失败");
	}

	/**
	 * 取消关注
	 * @param focusUserName
	 * @return
	 */
	@Override
	public ReturnClass<String> cancelFocus(String focusUserName) {
		UserFocus query=new UserFocus();
		query.setUserName(userService.getUserNameByLoginToken());
		query.setFocusUserName(focusUserName);
		UserFocus userFocus = userFocusMapper.selectOneUserFocus(query);
		if (Objects.nonNull(userFocus)){
			if (userFocus.getState()){
				userFocus.setState(false);
				userFocus.setUpdateTime(new Date());
				int update = userFocusMapper.update(userFocus);
				if (update>0){
					return ReturnClass.success("取消关注");
				}
			}else {
				return ReturnClass.success("已取消过关注");
			}

		}
		return ReturnClass.fail("未关注过该用户");
	}

	/**
	 * 查询你关注的
	 * @return
	 */
	@Override
	public ReturnClass<PageBean<UserFocusDTO>> getYourFocus(PageBase pageBase) {
		String userName=userService.getUserNameByLoginToken();
		Integer integer = userFocusMapper.selectMyFocusAndMutualCount(userName);
		if (integer<1){
			return ReturnClass.fail(ResultEnum.NO_DATA.getMsg());
		}
		PageHelper.startPage(pageBase.getCurrentPage(), pageBase.getPageSize());
		List<UserFocusDTO> userFocusDTOS = userFocusMapper.selectMyFocusAndMutual(userName);;
		PageBean<UserFocusDTO> pageBa=new PageBean<>(userFocusDTOS,pageBase.getCurrentPage(),pageBase.getPageSize(),integer);
		return ReturnClass.success(pageBa);
	}

	/**
	 * 查询你的粉丝
	 * @return
	 */
	@Override
	public ReturnClass<PageBean<UserFocusDTO>> getFocusYou(PageBase pageBase) {
		String userName=userService.getUserNameByLoginToken();
		Integer integer = userFocusMapper.selectFocusYouCount(userName);
		if (integer<1){
			return ReturnClass.fail(ResultEnum.NO_DATA.getMsg());
		}
		PageHelper.startPage(pageBase.getCurrentPage(), pageBase.getPageSize());
		List<UserFocusDTO> userFocusDTOS = userFocusMapper.selectFocusYou(userName);;
		PageBean<UserFocusDTO> pageBa=new PageBean<>(userFocusDTOS,pageBase.getCurrentPage(),pageBase.getPageSize(),integer);
		return ReturnClass.success(pageBa);
	}

	/**
	 * 查询是否关注过
	 *
	 * @param userName
	 * @param focusUserName
	 * @return
	 */
	@Override
	public ReturnClass<String> isFocus(String userName, String focusUserName) {
		UserFocus query=new UserFocus();
		if (Objects.isNull(userName)||Objects.isNull(focusUserName)){
			return ReturnClass.fail();
		}
		query.setUserName(userName);
		query.setFocusUserName(focusUserName);
		UserFocus userFocus = userFocusMapper.selectOneUserFocus(query);
		if (Objects.nonNull(userFocus)){
			if(userFocus.getState()){
				return ReturnClass.success();
			}
		}
		return ReturnClass.fail();
	}
}
