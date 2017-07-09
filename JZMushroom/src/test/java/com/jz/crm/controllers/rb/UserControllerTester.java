package com.jz.crm.controllers.rb;

import org.junit.Test;

import com.jz.crm.base.BaseControllerTest;
import com.jz.crm.base.httprequest.HttpRequest;

public class UserControllerTester extends BaseControllerTest {
	
	
	//微信授权
	@Test
	public void testAuthorization() {
		String url =  hostName + "/rbUser/authorization.action";
		//String url = "http://localhost:8080/JZMushroom/rbUser/sendVerificationCode.action";
		String parameter = "{}";
		String html = HttpRequest.sendHttpRequestProxy(false, cookieKey, "POST", url, replaceChar(parameter), null,
				null, requestProperty);
		System.out.println(html);
	}
	
	//获取验证码
	@Test
	public void testSendVerificationCodeByMobile() {
		String url =  hostName + "/rbUser/sendVerificationCode.action";
		//String url = "http://localhost:8080/JZMushroom/rbUser/sendVerificationCode.action";
		String parameter = "{'mobile':'15220228290'}";
		String html = HttpRequest.sendHttpRequestProxy(false, cookieKey, "POST", url, replaceChar(parameter), null,
				null, requestProperty);
		System.out.println(html);
	}

	//登录
	@Test
	public void testLogin() {
		testSendVerificationCodeByMobile();
		String url = hostName + "/rbUser/login.action";
		String parameter = "{'userName':'ss','verificationCode':'582966','email':'176369783@qq.com','mobile':'15220228290','presentCompany':'深圳较真技术有限公司','fuctionDep':'技术部','position':'java','hasReg':''}";
		String html = HttpRequest.sendHttpRequestProxy(false, cookieKey, "POST", url, replaceChar(parameter), null,
				null, requestProperty);
		System.out.println(html);
	}

}
