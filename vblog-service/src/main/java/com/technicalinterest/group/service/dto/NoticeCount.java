package com.technicalinterest.group.service.dto;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @package: com.technicalinterest.group.service.dto
 * @className: NoticeCount
 * @description: 未读通知数量
 * @author: Shuyu.Wang
 * @date: 2019-10-17 17:17
 * @since: 0.1
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeCount {

	private Integer comentCount;

	private Integer likeCount;
}
