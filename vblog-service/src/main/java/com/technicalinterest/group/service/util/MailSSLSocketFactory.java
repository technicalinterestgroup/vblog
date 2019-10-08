package com.technicalinterest.group.service.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/**
 * @package: com.technicalinterest.group.service.util
 * @className: MailSSLSocketFactory
 * @description: 邮件ssl
 * @author: Shuyu.Wang
 * @date: 2019-10-08 12:55
 * @since: 0.1
 **/

public class MailSSLSocketFactory  extends SSLSocketFactory {
	private SSLSocketFactory factory;

	public MailSSLSocketFactory() {
		try {
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, new TrustManager[] { new MailTrustManager() }, null);
			factory = sslcontext.getSocketFactory();
		} catch (Exception ex) {
			// ignore
		}
	}

	public static SocketFactory getDefault() {
		return new MailSSLSocketFactory();
	}

	@Override
	public Socket createSocket() throws IOException {
		return factory.createSocket();
	}

	@Override
	public Socket createSocket(Socket socket, String s, int i, boolean flag) throws IOException {
		return factory.createSocket(socket, s, i, flag);
	}

	@Override
	public Socket createSocket(InetAddress inaddr, int i, InetAddress inaddr1, int j) throws IOException {
		return factory.createSocket(inaddr, i, inaddr1, j);
	}

	@Override
	public Socket createSocket(InetAddress inaddr, int i) throws IOException {
		return factory.createSocket(inaddr, i);
	}

	@Override
	public Socket createSocket(String s, int i, InetAddress inaddr, int j) throws IOException {
		return factory.createSocket(s, i, inaddr, j);
	}

	@Override
	public Socket createSocket(String s, int i) throws IOException {
		return factory.createSocket(s, i);
	}

	@Override
	public String[] getDefaultCipherSuites() {
		return factory.getDefaultCipherSuites();
	}

	@Override
	public String[] getSupportedCipherSuites() {
		return factory.getSupportedCipherSuites();
	}

}
