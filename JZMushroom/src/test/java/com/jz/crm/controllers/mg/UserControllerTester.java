package com.jz.crm.controllers.mg;

import org.junit.Test;

import com.jz.crm.base.BaseControllerTest;
import com.jz.crm.base.httprequest.HttpRequest;

public class UserControllerTester extends BaseControllerTest {
	
	@Test
	public void Login() {
		SendVerificationCodeByEmail();
		String url =  hostName + "/user/login.action";
		String parameter = "{'userName':'test','passwd':'123'}";
		String html = HttpRequest.sendHttpRequestProxy(false, cookieKey, "POST", url, replaceChar(parameter), null,
				null, requestProperty);
		System.out.println(html);
	}
	
	@Test
	public void SendVerificationCodeByEmail() {
		String url = hostName + "/user/sendVerificationCode.action";
		String parameter = "{'userName':'176369783@qq.com'}";
		String html = HttpRequest.sendHttpRequestProxy(false, cookieKey, "POST", url, replaceChar(parameter), null,
				null, requestProperty);
		System.out.println(html);
	}

	@Test
	public void testSendVerificationCodeByEmail() {
		String url = hostName + "http://localhost:8080/JZMushroom//user/getVerificationCode.action";
		String parameter = "{'userName':'176369783@qq.com'}";
		String html = HttpRequest.sendHttpRequestProxy(false, cookieKey, "POST", url, replaceChar(parameter), null,
				null, requestProperty);
		System.out.println(html);
	}

	@Test
	public void testLogin() {
		String url = hostName + "user/login.action";
		String parameter = "{'userName':'176369783@qq.com','passwd':''}";
		String html = HttpRequest.sendHttpRequestProxy(false, cookieKey, "POST", url, replaceChar(parameter), null,
				null, requestProperty);
		System.out.println(html);
	}
}
