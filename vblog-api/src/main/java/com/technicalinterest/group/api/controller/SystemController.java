package com.technicalinterest.group.api.controller;

import com.technicalinterest.group.api.param.EditVSystemParam;
import com.technicalinterest.group.api.vo.ApiResult;
import com.technicalinterest.group.api.vo.UserVO;
import com.technicalinterest.group.api.vo.VSystemVO;
import com.technicalinterest.group.service.VSystemService;
import com.technicalinterest.group.service.annotation.BlogOperation;
import com.technicalinterest.group.service.constant.ResultEnum;
import com.technicalinterest.group.service.dto.ReturnClass;
import com.technicalinterest.group.service.dto.VSystemDTO;
import com.technicalinterest.group.service.exception.VLogException;
import com.technicalinterest.group.service.impl.VSystemServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @package: com.technicalinterest.group.api.controller
 * @className: SystemController
 * @description: 系统设置控制层
 * @author: Shuyu.Wang
 * @date: 2019-08-14 21:07
 * @since: 0.1
 **/
@Api(tags = "博客自定义设置")
@RestController
@RequestMapping("system")
public class SystemController {

	@Autowired
	private VSystemService vSystemService;

	private static final Boolean authCheck = true;

	/**
	 * 查询参数详情
	 * @return null
	 * @author: shuyu.wang
	 * @date: 2019-07-14 19:24
	 */
	@ApiOperation(value = "查询系统参数详情", notes = "详情")
	@GetMapping(value = "/detail/{userName}")
	@BlogOperation(value = "查询系统参数详情")
	public ApiResult<VSystemVO> detail(@PathVariable("userName") String userName) {
		ApiResult apiResult = new ApiResult();
		ReturnClass getSystemByUser = vSystemService.getSystemByUser(authCheck, userName);
		if (getSystemByUser.isSuccess()) {
			VSystemVO vSystemVO = new VSystemVO();
			BeanUtils.copyProperties(getSystemByUser.getData(), vSystemVO);
			apiResult.success(vSystemVO);

		} else {
			apiResult.fail(getSystemByUser.getMsg());
		}
		return apiResult;
	}

	/**
	 * 更新参数
	 * @return null
	 * @author: shuyu.wang
	 * @date: 2019-07-14 19:24
	 */
	@ApiOperation(value = "更新系统设置参数", notes = "详情")
	@PostMapping(value = "/edit")
	@BlogOperation(value = "更新系统设置参数")
	public ApiResult<String> detail(@Valid @RequestBody EditVSystemParam editVSystemParam) {
		ApiResult apiResult = new ApiResult();
		VSystemDTO vSystemDTO = new VSystemDTO();
		BeanUtils.copyProperties(editVSystemParam, vSystemDTO);
		ReturnClass getSystemByUser = vSystemService.update(vSystemDTO);
		if (getSystemByUser.isSuccess()) {
			apiResult.success();
		} else {
			apiResult.fail(getSystemByUser.getMsg());
		}
		return apiResult;
	}
}
