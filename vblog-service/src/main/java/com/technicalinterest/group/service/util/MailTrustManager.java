package com.technicalinterest.group.service.util;

import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;
/**
 * @package: com.technicalinterest.group.service.util
 * @className: MailTrustManager
 * @description:
 * @author: Shuyu.Wang
 * @date: 2019-10-08 12:57
 * @since: 0.1
 **/

public class MailTrustManager implements X509TrustManager{
	@Override
	public void checkClientTrusted(X509Certificate[] cert, String authType) {
		// everything is trusted
	}
	@Override
	public void checkServerTrusted(X509Certificate[] cert, String authType) {
		// everything is trusted
	}
	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return new X509Certificate[0];
	}
}
