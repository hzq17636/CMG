package com.jz.crm.base.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jz.crm.base.config.JZMushroom;
import com.jz.crm.base.config.ConstantContext;
import com.jz.crm.base.httprequest.HttpRequest;
import com.jz.crm.base.httprequest.Proxy;

public class ProxyUtil {
	public static final Logger LOG = LoggerFactory.getLogger(ProxyUtil.class);
	public static List<com.jz.crm.base.httprequest.Proxy> onLineProxys = new ArrayList();
	static {
		(new Thread(new RefreshOnlineProxy())).start();
	}

	public synchronized static Proxy getOnlineProxy(boolean isCache) {
		try {
			if (isCache && onLineProxys.size() > 0) {
				int randomNumberint = getRandomNumber(onLineProxys.size());
				return onLineProxys.get(randomNumberint);
			} else {
				onLineProxys.clear();
				String html = HttpRequest.sendHttpRequestProxy(true, DateTimeUtil.dateToString(new Date(),
						ConstantContext.DATE_FORMAT), "GET", JZMushroom.getInstance().get("proxy.online.url"), null, null,
						null, null);
				String[] ips = html.split("\n");
				for (int i = 0; i < ips.length; i++) {
					if (ips[i].indexOf(":") != -1) {
						Proxy proxy = new Proxy();
						String[] ipAndPort = ips[i].split(":");
						proxy.setIp(ipAndPort[0]);
						proxy.setPort(Integer.parseInt(ipAndPort[1]));
						proxy.setUserName(null);
						proxy.setPassword(null);
						onLineProxys.add(proxy);
					}
				}
			}
			if (onLineProxys.size() > 0) {
				int randomNumberint = getRandomNumber(onLineProxys.size());
				return onLineProxys.get(randomNumberint);
			}
		} catch (Exception e) {
			LOG.error("getOnlineProxy 报错",e);
		}
		return null;
	}

	/**
	 * 随机数
	 * 
	 * @param length
	 * @return
	 */
	private static int getRandomNumber(int length) {
		int a[] = new int[length];
		for (int i = 0; i < length; i++) {
			a[i] = (int) (Math.random() * length);
		}
		return a[0];
	}

	static class RefreshOnlineProxy implements Runnable {
		private int proxyRefreshIntervalTime = JZMushroom.getInstance().getInt("proxy.refresh.interval.time");

		@Override
		public void run() {
			boolean hasOnline = JZMushroom.getInstance().getBoolean("proxy.hasOnline");
			while (hasOnline) {
				try {
					ProxyUtil.getOnlineProxy(false);
					Thread.sleep(proxyRefreshIntervalTime);
				} catch (Exception e) {
					LOG.error("RefreshOnlineProxy 报错", e);
				}
			}
		}
	}
}
