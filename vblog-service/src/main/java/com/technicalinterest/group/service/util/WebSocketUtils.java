package com.technicalinterest.group.service.util;

import com.alibaba.fastjson.JSONObject;
import com.technicalinterest.group.service.dto.WebSocketMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @package: com.technicalinterest.group.service.util
 * @className: WebSocketUtils
 * @description: websocket工具类
 * @author: Shuyu.Wang
 * @date: 2019-10-17 17:42
 * @since: 0.1
 **/
@Slf4j
public class WebSocketUtils {
	public static Map<String, Session> clients = new ConcurrentHashMap<String, Session>();
	//	private static ConcurrentHashMap<String, WebSocketServer> clients = new ConcurrentHashMap<>();

	/**
	 *  Add Session
	 */
	public static void add(String userName, Session session) {
		clients.put(userName, session);
		log.info("当前连接数 = " + clients.size());

	}

	/**
	 *Receive Message
	 */
	public static void receive(String userName, String message) {
		log.info("收到消息 : userName = " + userName + " , Message = " + message);
		log.info("当前连接数 = " + clients.size());
	}

	/**
	 *Remove Session
	 */
	public static void remove(String userName) {
		clients.remove(userName);
		log.info("当前连接数 = " + clients.size());

	}

	/**
	 *web端消息发送
	 */
	public static boolean sendMessage(String fromUser, String toUser, String message) {
		log.info("当前连接数 = " + clients.size());
		if (clients.size() == 0) {
			log.info("当前没有用户在线！");
			return false;
		}
		WebSocketMessage build = WebSocketMessage.builder().fromUser(fromUser).message(message).type(1).build();
		if (StringUtils.isEmpty(toUser)) {
			sendALL(fromUser,build);
		}
		if (clients.get(toUser) == null) {
			log.info("用户不在线");
			return false;
		} else {
			log.info("发送消息");
			clients.get(toUser).getAsyncRemote().sendText(JSONObject.toJSONString(build));
			return true;
		}

	}

	/**
	 *服务器发送消息
	 */
	public static boolean sendToUser(String toUser, WebSocketMessage webSocketMessage) {
		log.info("当前连接数 = " + clients.size());
		if (clients.size() == 0) {
			log.info("当前没有用户在线！");
			return false;
		}
		if (clients.get(toUser) == null) {
			log.info("用户不在线");
			return false;
		} else {
			log.info("发送消息给={}", toUser);
			clients.get(toUser).getAsyncRemote().sendText(JSONObject.toJSONString(webSocketMessage));
			return true;
		}

	}

	/**
	 *服务消息群发
	 */
	public static boolean sendALL(String fromUser,WebSocketMessage webSocketMessage) {
		log.info("当前连接数 = " + clients.size());
		log.info("群发消息");
		if (clients.size() == 0) {
			log.info("当前没有用户在线！");
			return false;
		}
		for (String key : clients.keySet()) {
			if (StringUtils.equals(fromUser,key)){
				continue;
			}
			log.info("发送给用户={}", key);
			clients.get(key).getAsyncRemote().sendText(JSONObject.toJSONString(webSocketMessage));
		}
		return true;

	}

}
