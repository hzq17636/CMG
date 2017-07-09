package com.jz.crm.base.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextManager implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {

	}

	public void contextInitialized(ServletContextEvent context) {
		BeanFactory beanFactory = WebApplicationContextUtils.getWebApplicationContext(context.getServletContext());

		JZMushroom creditBank = JZMushroom.getInstance();
		creditBank.setBeanFactory(beanFactory);
	}

}
