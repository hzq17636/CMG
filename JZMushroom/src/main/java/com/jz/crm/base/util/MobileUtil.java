package com.jz.crm.base.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import com.jz.crm.base.httprequest.HttpRequest;

public class MobileUtil {

	/**
	 * 手机归属地查询
	 */
	public static String getBelongAdress(String mobile) throws Exception {
		Map<String, String> requestProperty = new HashMap();
		requestProperty.put("Cache-Control", "max-age=0");
		requestProperty.put("Upgrade-Insecure-Requests", "1");
		requestProperty.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		String html = HttpRequest.sendHttpRequestProxy(false, null, "GET",
				"http://www.hiphop8.com/nub/"+mobile+".html", null, null, "gb2312", null);
		 //System.out.println(html);
		return XpathUtil.getContentInfo(html, ".//*[@class='mU']/div[3]/span[2]");
	}

	public static void main(String[] args) throws Exception {
		System.out.println(getBelongAdress("18516586533"));
	}

}
