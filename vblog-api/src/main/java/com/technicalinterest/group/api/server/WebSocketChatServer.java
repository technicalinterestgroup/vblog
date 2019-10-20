package com.technicalinterest.group.api.server;

import com.technicalinterest.group.service.util.WebSocketUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
@ServerEndpoint("/chat/{userName}")
@Component
public class WebSocketChatServer {
	/**
	 *New Connected
	 */
	@OnOpen
	public void onOpen(@PathParam("userName") String userName, Session session) {
		log.info("[WebSocketServer] Connected : userName = " + userName);
		WebSocketUtils.add(userName, session);
		WebSocketUtils.sendUserList(userName);

	}

	/**
	 *Send Message
	 */
	@OnMessage
	public void onMessage(@PathParam("userName") String userName, String message) {
		log.info("[WebSocketServer] Received Message : userName = " + userName + " , message = " + message);
		if (message.equals("&")) {
//			WebSocketUtils.sendMessage(userName, userName, JSONObject.toJSONString(WebSocketUtils.getUserList(userName)));
		} else {
			if (message.contains("@")) {
				String[] split = message.split("@");
				WebSocketUtils.sendMessage(userName, split[1], split[0]);
			} else {
				WebSocketUtils.sendMessage(userName, null, message);
			}
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
