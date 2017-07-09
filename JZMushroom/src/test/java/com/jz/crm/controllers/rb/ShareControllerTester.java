package com.jz.crm.controllers.rb;

import org.junit.Test;

import com.jz.crm.base.BaseControllerTest;
import com.jz.crm.base.httprequest.HttpRequest;

public class ShareControllerTester extends BaseControllerTest {
	
	//获取分享明细
	@Test
	public void getShareByuserId() {
		String url = hostName + "/share/getShareByuserId.action";
		String parameter = "{'userId':'1487'}";
		String html = HttpRequest.sendHttpRequestProxy(false, cookieKey, "POST", url, replaceChar(parameter), null,
				null, requestProperty);
		System.out.println(html);
	}
	
	//获取分享用户列表
	@Test
	public void getShareListBy() {
		String url = hostName + "/share/getShareListBy.action";
		String parameter = "{}";
		String html = HttpRequest.sendHttpRequestProxy(false, cookieKey, "POST", url, replaceChar(parameter), null,
				null, requestProperty);
		System.out.println(html);
	}

	//我的邀请
	@Test
	public void share() {
		String url = hostName + "/share/queryShareList.action";
		String parameter = "{}";
		String html = HttpRequest.sendHttpRequestProxy(false, cookieKey, "POST", url, replaceChar(parameter), null,
				null, requestProperty);
		System.out.println(html);
	}


	//领取红宝书
	@Test
	public void reviceRedBook() {
		String url = hostName + "/share/reviceRedBook.action";
		String parameter = "{}";
		String html = HttpRequest.sendHttpRequestProxy(false, cookieKey, "POST", url, replaceChar(parameter), null,
				null, requestProperty);
		System.out.println(html);
	}

	

}
