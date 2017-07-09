package com.jz.crm.base.interceptors;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jz.crm.base.config.ConstantContext;
import com.jz.crm.base.manager.LoginUser;
import com.jz.crm.base.manager.SessionManager;

public class SessionTimeoutInterceptor extends HandlerInterceptorAdapter {
	public List<String> allowUrls;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		/**
		 * 处理非登录标识符，
		 */
		// String token = request.getHeader(ConstantContext.TOKEN);
		// if(token!=null && !"".equals(token)){
		// return true;
		// }

		/**
		 * 如果是所配置的URL，就不需要检验是否登录
		 */
		String requestUrl = request.getRequestURI();
		for (String url : allowUrls) {
			if (requestUrl.contains(url)) {
				return true;
			}
		}

		/**
		 * 剩下所有URL都要检验是否登录
		 */
		LoginUser loginUser = SessionManager.getSession(request.getSession());
		if (null == loginUser) { // 未登录
			if (request.getHeader("x-requested-with") != null
					&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) { // 如果是ajax请求响应头会有，x-requested-with
				response.setHeader("sessionstatus", "timeout");// 在响应头设置session状态
			}
			return false;
		}
		return super.preHandle(request, response, handler);
	}

	public List<String> getAllowUrls() {
		return allowUrls;
	}

	public void setAllowUrls(List<String> allowUrls) {
		this.allowUrls = allowUrls;
	}
}
