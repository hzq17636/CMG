package com.jz.crm.controllers.rb;

import org.junit.Test;

import com.jz.crm.base.BaseControllerTest;
import com.jz.crm.base.httprequest.HttpRequest;

public class RedBookControllerTester extends BaseControllerTest {

	//红宝书-获取公司详情
	@Test
	public void queryCompanyDetail() {
		String url = hostName + "/redbook/queryCompanyDetail.action";
		String parameter = "{'companyId':'11692'}";
		String html = HttpRequest.sendHttpRequestProxy(false, cookieKey, "POST", url, replaceChar(parameter), null,
				null, requestProperty);
		System.out.println(html);
	}


	//红宝书-列表
	@Test
	public void collectCompanyByName() {
		String url = hostName + "/redbook/queryCompanyListBy.action";
		String parameter = "{'companyType':'','pageComm':{'currentPageNo':1,'pageSize':20}}";
		String html = HttpRequest.sendHttpRequestProxy(false, cookieKey, "POST", url, replaceChar(parameter), null,
				null, requestProperty);
		System.out.println(html);
	}

	//菜单栏
	@Test
	public void cancelCollect() {
		String url = hostName + "/redbook/queryRedBookMenu.action";
		//String parameter = "{'id':'111'}";
		String html = HttpRequest.sendHttpRequestProxy(false, cookieKey, "GET", url, null, null,
				null, requestProperty);
		System.out.println(html);
	}

}
