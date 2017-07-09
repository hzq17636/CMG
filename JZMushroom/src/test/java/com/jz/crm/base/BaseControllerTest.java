package com.jz.crm.base;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.jz.crm.base.httprequest.HttpRequest;

public class BaseControllerTest {
	public Map<String, String> requestProperty = new HashMap();
	public static final String hostName = "http://localhost:8080/JZMushroom/";
	public static final String cookieKey = "cookieKey";
	

	@Before
	public void setUp() {
		requestProperty.put("X-Requested-With", "XMLHttpRequest");
		requestProperty.put("Content-Type", "application/json");
		//采蘑菇
//		String url = hostName + "user/testLogin.action";
//		String parameter = "{'userName':'test','passwd':'123'}";
		//红宝书
		String url = hostName + "/rbUser/testLogin.action";
		String parameter = "{'id':'1424'}";
		HttpRequest.sendHttpRequestProxy(false, cookieKey, "POST", url, replaceChar(parameter), null, null,
				requestProperty);
	}

	public String replaceChar(String str) {
		return str.replaceAll("'", "\"");
	}
	
	public static void main(String[] args) {
		Map<String, String> requestProperty = new HashMap();
		requestProperty.put("X-Requested-With", "XMLHttpRequest");
		requestProperty.put("Content-Type", "application/json");
		String url = hostName + "/user/testlogin.action";
		String parameter = "{'userName':'test','passwd':'123'}";
		String html = HttpRequest.sendHttpRequestProxy(false, cookieKey, "POST", url,parameter.replaceAll("'", "\""), null, null,
				requestProperty);
		System.out.println(html);
	}
}
