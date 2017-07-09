package com.jz.crm.base.interceptors;

import org.apache.log4j.NDC;
import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jz.crm.base.config.ConstantContext;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Log4jNdcFilter implements Filter {
	public static final Logger LOG = LoggerFactory.getLogger(Log4jNdcFilter.class);

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		// String address = req.getRemoteAddr();
		String sessionID = req.getSession().getId();
		MDC.put("sessionID", sessionID);
		// NDC.push(sessionID);
		res.setHeader("Access-Control-Allow-Headers", "Origin,Content-Type,Accept,X-Sso-Token,X-Loan-Media-Type");
		res.setHeader("Access-Control-Allow-Origin", "*");
		chain.doFilter(request, response);
		NDC.pop();
	}

	public void destroy() {

	}

	public void init(FilterConfig arg0) throws ServletException {

	}
}
