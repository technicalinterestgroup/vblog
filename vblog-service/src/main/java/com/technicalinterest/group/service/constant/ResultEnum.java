package com.technicalinterest.group.service.constant;

/**
 * @package: com.technicalinterest.group.api.constant
 * @className: ResultEnum
 * @description: 返回编码枚举
 * @author: Shuyu.Wang
 * @date: 2019-08-09 22:14
 * @since: 0.1
 **/

public enum ResultEnum {
	SUCCESS("000000","请求成功!"),
	ERROR("000001","请求失败！"),
	//业务异常
	ACCESTOKEN_NULL("000004","ACCESTOKEN为空!"),
	TIME_OUT("000005","登录超时!"),
	SERVICE_ERROR("000006","业务异常!"),
	USERINFO_ERROR("000007","获取用户信息异常!"),
	NO_AUTH("000008","无当前操作权限!"),
	JSON_ERROR("000009","json格式转换异常!"),
	DUPLICATE_ERROR("000010","数据重复!"),
	NO_DATA("000011","没有匹配数据!"),
	REQ_FREQUENT("000012","请求过于频繁，请稍后重试!"),
	DUPLOAD_ERROR("000013","文件上传异常!"),
	//系统级异常
	NO_URL("000101","url不存在!"),
	METHOD_NOT_ALLOWED("000102","请求方式不支持!"),
	NET_BLOCK("000103","网络阻塞!"),
	SERVER_ERROR("000104","服务器异常"),
	MEDIATYPE_ERROR("000105","媒体类型不支持!"),
	FILESIZE_ERROR("000106","文件过大!"),
	PARAM_ERROR("000107","参数获取异常！"),
	DATA_ERROR("000108","数据格式异常!");

	private String code;
	private String msg;

	ResultEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public static String getCodeByMsg(String msg) {
		for (ResultEnum valueEnum : ResultEnum.values()) {
			if (msg.equals(valueEnum.getMsg())) {
				return valueEnum.getCode();
			}
		}
		return "";
	}

	public static String getMsgByCode(Integer code) {
		for (ResultEnum valueEnum : ResultEnum.values()) {
			if (code.equals(valueEnum.getCode())) {
				return valueEnum.getMsg();
			}
		}
		return "";
	}
}
