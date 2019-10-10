package com.technicalinterest.group.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @package: com.technicalinterest.group.service.dto
 * @className: ImagVerifi
 * @description: 验证图片
 * @author: Shuyu.Wang
 * @date: 2019-10-10 13:16
 * @since: 0.1
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImagVerifi {

	private String token;

	private String img;
}
