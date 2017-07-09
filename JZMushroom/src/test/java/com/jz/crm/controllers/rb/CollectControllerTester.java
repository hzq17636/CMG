package com.jz.crm.controllers.rb;

import org.junit.Test;

import com.jz.crm.base.BaseControllerTest;
import com.jz.crm.base.httprequest.HttpRequest;

public class CollectControllerTester extends BaseControllerTest {

	//点击收藏
	@Test
	public void collect() {
		String url = hostName + "/collect/collect.action";
		String parameter = "{'companyId':'11671'}";
		String html = HttpRequest.sendHttpRequestProxy(false, cookieKey, "POST", url, replaceChar(parameter), null,
				null, requestProperty);
		System.out.println(html);
	}


	//我的收藏
	@Test
	public void collectCompanyByName() {
		String url = hostName + "/collect/queryCollectCompany.action";
		String parameter = "{'companyName':'较真','pageComm':{'currentPageNo':1,'pageSize':20}}";
		String html = HttpRequest.sendHttpRequestProxy(false, cookieKey, "POST", url, replaceChar(parameter), null,
				null, requestProperty);
		System.out.println(html);
	}

	//取消收藏
	@Test
	public void cancelCollect() {
		String url = hostName + "/collect/cancelCollect.action";
		String parameter = "{'id':'108'}";
		String html = HttpRequest.sendHttpRequestProxy(false, cookieKey, "POST", url, replaceChar(parameter), null,
				null, requestProperty);
		System.out.println(html);
	}

}
