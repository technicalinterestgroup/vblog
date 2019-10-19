package com.technicalinterest.group.service.dto;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @package: com.technicalinterest.group.service.dto
 * @className: WebSocketMessage
 * @description: 推送消息类
 * @author: Shuyu.Wang
 * @date: 2019-10-18 15:19
 * @since: 0.1
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketMessage {
	/**
	* 消息类型
	*/
	private Integer type;
	/**
	 * 发送用户
	 */
	private String fromUser;
	/**
	 * 消息内容
	 */
	private String message;
}
