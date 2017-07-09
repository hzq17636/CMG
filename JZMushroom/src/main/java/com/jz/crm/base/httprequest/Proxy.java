package com.jz.crm.base.httprequest;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.jz.crm.base.config.JZMushroom;

public class Proxy implements Cloneable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String code;

	private List<String> msg;

	private String refuseInfo;

	private boolean hasLocalAdder = JZMushroom.getInstance().getBoolean("proxy.hasLocalAdder");

	private String ip;

	private int port;

	private String userName;

	private String password;

	private Integer connectTimeout = JZMushroom.getInstance().getInt("proxy.conn.time.out") == -1 ? null : JZMushroom
			.getInstance().getInt("proxy.conn.time.out");

	private Integer readTimeout = JZMushroom.getInstance().getInt("proxy.read.time.out") == -1 ? null : JZMushroom
			.getInstance().getInt("proxy.read.time.out");

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(Integer readTimeout) {
		this.readTimeout = readTimeout;
	}

	public Integer getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public boolean getHasLocalAdder() {
		return hasLocalAdder;
	}

	public void setHasLocalAdder(boolean hasLocalAdder) {
		this.hasLocalAdder = hasLocalAdder;
	}

	public String getRefuseInfo() {
		return refuseInfo;
	}

	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}

	public String getCode() {
		return code;
	}

	@XmlAttribute
	public void setCode(String code) {
		this.code = code;
	}

	public List<String> getMsg() {
		return msg;
	}

	@XmlElement
	public void setMsg(List<String> msg) {
		this.msg = msg;
	}

	public boolean isExist(String html) {
		if (refuseInfo == null || html == null || "".equals(refuseInfo)) {
			return false;
		}
		if (html.indexOf(refuseInfo) != -1) {
			return true;
		}
		return false;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

}
