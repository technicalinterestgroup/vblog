package com.technicalinterest.group.service.constant;

/**
 * @package: com.shuyu.blog.constant
 * @className: UserConstant
 * @description:
 * @author: Shuyu.Wang
 * @date: 2019-07-14 19:04
 * @since: 0.1
 **/

public class UserConstant {

	public static final String NO_USER = "该用户不存在！";

	public static final String PASSWORD_ERROR = "密码错误！";

	public static final String NO_ACTIVATION= "该账号尚未激活，请前往注册邮箱激活！";

	public static final String USER_DISABLE= "账号被禁用，请联系管理员！";

	public static final String DUPLICATE_USER_NAME = "该用户名已被占用！";

	public static final String DUPLICATE_USER_EMAIL = "该邮箱已被注册！";

	public static final String ADD_USER_ERROR = "用户注册失败！";

	public static final String ADD_EMAIL_SEND = "激活邮件已经发送到邮箱，请在24小时内激活！";

	public static final String EDIT_USER_ERROR = "更新用户资料失败！";

	public static final String EDIT_USER_SUS = "更新用户资料成功！";

	public static final String FAILD_GET_USER_INFO = "获取用户信息失败！";

	public static final String LOG_OUT_INFO = "登录失效，请重新登录！";

	public static final String MAIL_TITLE = "用户激活邮件！";

	public static final String MAIL_OUTTIME = "邮件已经过期！";

	public static final String ACTIVATION_SUC = "账号激活成功！";

	public static final String ACTIVATION_FAIL = "账号激活失败！";

	public static final String ACTIVATION_URL = "/vblog/view/user/activation/";

	public static final String NO_USER_INFO= "暂无用户数据！";

	public static final String ADMIN_AUTH_URL= "adminAuthUrl";

	public static final String LOGIN_SUCCESS= "登录成功！";

	public static final String FORGET_PASS_MAIL_TITLE = "密码重置邮件！";

	public static final String FORGET_PASS_URL = "/vblog/view/user/reset/";

	public static final String FORGET_PASS_MAIL_SEND = "重置密码邮件已经发送，请在24小时内完成修改！";

	public static final String CHANGE_PASS_SUC = "密码重置成功！";

	public static final String KEY_ERROR = "密码重置链接与用户名不匹配！";

	public static final String CHANGE_PASS_FAIL = "密码重置失败！";

	public static final String CREAT_IMG_FAIL = "验证码生成失败！";

	public static final String IMG_TIME_OUT = "验证码已过期,请重新获取！";

	public static final String IMG_SUC = "验证通过！";

	public static final String IMG_FAIL = "验证码错误！";

	public static final String MAIL_FAIL = "用户名邮箱不匹配！";

	public static final String LOGIN_ERROR_OVER_TIMES = "登录密码错误超过6次，账户被锁定5分钟！";
}
