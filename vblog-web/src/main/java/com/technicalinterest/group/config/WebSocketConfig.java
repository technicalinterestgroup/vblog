package com.technicalinterest.group.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @package: com.technicalinterest.group.config
 * @className: WebSocketConfig
 * @description: webscoket
 * @author: Shuyu.Wang
 * @date: 2019-10-17 17:39
 * @since: 0.1
 **/
@Configuration
public class WebSocketConfig {

	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}
}
