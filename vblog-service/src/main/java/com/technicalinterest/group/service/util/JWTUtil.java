package com.technicalinterest.group.service.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.technicalinterest.group.service.dto.UserJWTDTO;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @package: com.technicalinterest.group.service.util
 * @className: JWTUtil
 * @description:
 * @author: Shuyu.Wang
 * @date: 2019-10-14 15:07
 * @since: 0.1
 **/
@Slf4j
public final class JWTUtil {

	/** 存放token的请求头对应的key的名字 */
	private static String headerKey = "token";
	/** 秘钥 */
	private static String SECRET = "zxyTestSecret";
	/** 过期时间，单位为秒 */
	private static long expire = 1L;

	/**
	 * 生成jwt token
	 */
	public static String generateToken(Object obj) {
		//  过期时间
		Date expireDate = new Date(System.currentTimeMillis() + expire * 1000);
		Algorithm algorithm = Algorithm.HMAC256(SECRET);
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("alg", "HS256");
		map.put("typ", "JWT");
		String data = JSON.toJSONString(obj);
		String token = JWT.create().withHeader(map)
				/* 设置 载荷 Payload */.withClaim("data", data)
				// 签名是有谁生成 例如 服务器
				.withIssuer("SERVICE")
				// 签名的主题
				.withSubject("this is test token")
				//定义在什么时间之前，该jwt都是不可用的
				// .withNotBefore(new Date())
				// 签名的观众 也可以理解谁接受签名的
				.withAudience("APP")
				// 生成签名的时间
				.withIssuedAt(new Date())
				// 签名过期的时间
				.withExpiresAt(expireDate)
				/* 签名 Signature */.sign(algorithm);
		return token;

	}

	/**
	 * 校验token并解析token
	 * @param token
	 * @return Claims：它继承了Map,而且里面存放了生成token时放入的用户信息
	 */
	public static DecodedJWT verifyToken(String token) {
		DecodedJWT jwt = null;
		try {
			JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).withIssuer("SERVICE").build();
			jwt = verifier.verify(token);
		} catch (Exception var3) {
			log.error("解析JWT异常",var3);
		}
		return jwt;
	}

	public static UserJWTDTO getUserInfo(String token) {
		DecodedJWT jwt = verifyToken(token);
		if (null != jwt) {
			String json = ((Claim)jwt.getClaims().get("data")).asString();
			ObjectMapper objectMapper = new ObjectMapper();

			try {
				return (UserJWTDTO)objectMapper.readValue(json, UserJWTDTO.class);
			} catch (IOException var5) {
				log.error("解析JWT异常",var5);
			}
		}
		return null;
	}

	public static void main(String[] args) {
		String data="{ \"userName\": \"admin\", \"userToken\": \"da64184bf94447039161407af36c8ecd\", \"nickName\": \"123\", \"photo\": \"\", \"authList\": [ { \"authName\": \"博客列表\", \"url\": \"\" }, { \"authName\": \"文件管理\", \"url\": \"\" }, { \"authName\": \"用户列表\", \"url\": \"\" }, { \"authName\": \"日志记录\", \"url\": \"\" } ] }";
		UserJWTDTO userJWTDTO = JSONObject.parseObject(data, UserJWTDTO.class);
		String s = generateToken(userJWTDTO);
		log.info(s);

		UserJWTDTO userInfo = getUserInfo(s);
		log.info(JSONObject.toJSONString(userInfo));
	}
}
