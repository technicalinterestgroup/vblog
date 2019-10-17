package com.technicalinterest.group.api.server;

import com.technicalinterest.group.service.util.WebSocketUtils;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * @package: com.technicalinterest.group.api.server
 * @className: WebSocketServer
 * @description:
 * @author: Shuyu.Wang
 * @date: 2019-10-17 17:47
 * @since: 0.1
 **/
@Slf4j
@ServerEndpoint("/connect/{userName}")
public class WebSocketServer {
	/**
	 *New Connected
	 */
	@OnOpen
	public void onOpen(@PathParam("userName") String userName, Session session) {
		log.info("[WebSocketServer] Connected : userName = " + userName);
		WebSocketUtils.add(userName, session);
	}

	/**
	 *Send Message
	 */
	@OnMessage
	public String onMessage(@PathParam("userName") String userName, String message) {
		log.info("[WebSocketServer] Received Message : userName = " + userName + " , message = " + message);
		if (message.equals("&")) {
			return "&";
		} else {
			WebSocketUtils.sendMessage(userName, message);
			return "Got your message (" + message + ").";
		}
	}

	/**
	 *Errot
	 */
	@OnError
	public void onError(@PathParam("userName") String userName, Throwable throwable, Session session) {
		log.error("[WebSocketServer] Connection Exception : userName = " + userName + " , throwable = " + throwable.getMessage());
		WebSocketUtils.remove(userName);
	}

	/**
	 *Close Connection
	 */
	@OnClose
	public void onClose(@PathParam("userName") String userName, Session session) {
		log.info("[WebSocketServer] Close Connection : userName = " + userName);
		WebSocketUtils.remove(userName);
	}

}
