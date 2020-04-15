package com.technicalinterest.group.api.controller;

import com.technicalinterest.group.api.param.PageBaseParam;
import com.technicalinterest.group.api.vo.ApiResult;
import com.technicalinterest.group.api.vo.CommentNoticeVO;
import com.technicalinterest.group.api.vo.UserFocusVO;
import com.technicalinterest.group.dao.PageBase;
import com.technicalinterest.group.dto.CommentNoticeDTO;
import com.technicalinterest.group.dto.UserFocusDTO;
import com.technicalinterest.group.service.UserFocusService;
import com.technicalinterest.group.service.annotation.BlogOperation;
import com.technicalinterest.group.service.dto.PageBean;
import com.technicalinterest.group.service.dto.ReturnClass;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @package: com.technicalinterest.group.api.controller
 * @className: UserFocusController
 * @description:
 * @author: Shuyu.Wang
 * @date: 2020-04-12 20:39
 * @since: 0.1
 **/
@Api(tags = "用户关注关系")
@RestController
@RequestMapping("focus")
@Slf4j
public class UserFocusController {
    @Autowired
	private UserFocusService userFocusService;


	@ApiOperation(value = "我的关注")
	@GetMapping(value = "/list")
	@BlogOperation(value = "我的关注")
	public ApiResult<PageBean<UserFocusVO>> myFocus(@Valid PageBaseParam pageBaseParam) {
		ApiResult apiResult = new ApiResult();
		PageBase pageBase = new PageBase();
		BeanUtils.copyProperties(pageBaseParam, pageBase);
		ReturnClass<PageBean<UserFocusDTO>> yourFocus = userFocusService.getYourFocus(pageBase);
		if (yourFocus.isSuccess()) {
			PageBean<UserFocusDTO> pageBean =yourFocus.getData();
			List<UserFocusVO> list = new ArrayList<>();
			for (UserFocusDTO entity : pageBean.getPageData()) {
				UserFocusVO userFocusVO = new UserFocusVO();
				BeanUtils.copyProperties(entity, userFocusVO);
				list.add(userFocusVO);
			}
			PageBean<UserFocusVO> pageInfo = new PageBean<UserFocusVO>();
			BeanUtils.copyProperties(yourFocus.getData(), pageInfo);
			pageInfo.setPageData(list);
			apiResult.success(pageInfo);
		} else {
			apiResult.setMsg(yourFocus.getMsg());
		}
		return apiResult;
	}

	@ApiOperation(value = "我的粉丝")
	@GetMapping(value = "/fans")
	@BlogOperation(value = "我的粉丝")
	public ApiResult<PageBean<UserFocusVO>> myFan(@Valid PageBaseParam pageBaseParam) {
		ApiResult apiResult = new ApiResult();
		PageBase pageBase = new PageBase();
		BeanUtils.copyProperties(pageBaseParam, pageBase);
		ReturnClass<PageBean<UserFocusDTO>> yourFocus = userFocusService.getFocusYou(pageBase);
		if (yourFocus.isSuccess()) {
			PageBean<UserFocusDTO> pageBean =yourFocus.getData();
			List<UserFocusVO> list = new ArrayList<>();
			for (UserFocusDTO entity : pageBean.getPageData()) {
				UserFocusVO userFocusVO = new UserFocusVO();
				BeanUtils.copyProperties(entity, userFocusVO);
				list.add(userFocusVO);
			}
			PageBean<UserFocusVO> pageInfo = new PageBean<UserFocusVO>();
			BeanUtils.copyProperties(yourFocus.getData(), pageInfo);
			pageInfo.setPageData(list);
			apiResult.success(pageInfo);
		} else {
			apiResult.setMsg(yourFocus.getMsg());
		}
		return apiResult;
	}

	@ApiOperation(value = "关注用户")
	@GetMapping(value = "/save")
	@BlogOperation(value = "关注用户")
	public ApiResult<PageBean<UserFocusVO>> save(@RequestParam(value = "userName")String userName) {
		ApiResult apiResult = new ApiResult();
		ReturnClass<String> stringReturnClass = userFocusService.addNewFocus(userName);
		if (stringReturnClass.isSuccess()) {
			apiResult.success(stringReturnClass.getMsg());
		} else {
			apiResult.setMsg(stringReturnClass.getMsg());
		}
		return apiResult;
	}

	@ApiOperation(value = "取消关注")
	@GetMapping(value = "/cancel")
	@BlogOperation(value = "取消关注")
	public ApiResult<PageBean<UserFocusVO>> cancel(@RequestParam(value = "userName")String userName) {
		ApiResult apiResult = new ApiResult();
		ReturnClass<String> stringReturnClass = userFocusService.cancelFocus(userName);
		if (stringReturnClass.isSuccess()) {
			apiResult.success(stringReturnClass.getMsg());
		} else {
			apiResult.setMsg(stringReturnClass.getMsg());
		}
		return apiResult;
	}

}
