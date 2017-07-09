package com.jz.crm.base.httprequest;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class RequestProperty implements Serializable {
	public static final String SET_COOKIE = "set_cookie";//设置请求cookie
	public static final String RES_COOKIE_INDEX = "res_cookie_index";//获取返回第几个set_cookie
	public static final String AGAIN_REQUEST = "againRequest";//设置需要再次请求的字符串

	private String key;

	private String value;

	public String getKey() {
		return key;
	}

	@XmlAttribute
	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	@XmlAttribute
	public void setValue(String value) {
		this.value = value;
	}

	

	
}
