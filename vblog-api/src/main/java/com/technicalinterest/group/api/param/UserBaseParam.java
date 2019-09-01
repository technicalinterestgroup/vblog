package com.technicalinterest.group.api.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @package: com.technicalinterest.group.api.param
 * @className: UserBaseParam
 * @description:
 * @author: Shuyu.Wang
 * @date: 2019-09-01 18:35
 * @since: 0.1
 **/
@Data
public class UserBaseParam extends PageBaseParam{

	@ApiModelProperty(value = "用户名")
	private String userName;
}
