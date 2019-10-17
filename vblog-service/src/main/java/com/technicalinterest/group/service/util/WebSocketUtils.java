package com.technicalinterest.group.service.util;

import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
import java.util.Map;
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
	 *Get Session
	 */
	public static boolean sendMessage(String userName, String message) {
		log.info("当前连接数 = " + clients.size());
		if (clients.get(userName) == null) {
			return false;
		} else {
			clients.get(userName).getAsyncRemote().sendText(message);
			return true;
		}

	}

}
