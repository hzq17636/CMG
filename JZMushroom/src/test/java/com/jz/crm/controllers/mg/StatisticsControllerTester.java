package com.jz.crm.controllers.mg;

import org.junit.Test;

import com.jz.crm.base.BaseControllerTest;
import com.jz.crm.base.httprequest.HttpRequest;

public class StatisticsControllerTester extends BaseControllerTest {
	
	//录入统计
	@Test
	public void inputStatics() {
		String url = hostName + "/mg/report/input.action";
		String parameter = "{}";
		String html = HttpRequest.sendHttpRequestProxy(false, cookieKey, "POST", url, replaceChar(parameter), null,
				null, requestProperty);
		System.out.println(html);
	}
	
	//行业统计
	@Test
	public void indutryStatics() {
		String url = hostName + "/mg/report/industry.action";
		String parameter = "{}";
		String html = HttpRequest.sendHttpRequestProxy(false, cookieKey, "POST", url, replaceChar(parameter), null,
				null, requestProperty);
		System.out.println(html);
	}

}
