package com.jz.crm.base.manager;

import javax.servlet.http.HttpSession;

import org.springframework.util.Assert;

import com.jz.crm.base.config.ConstantContext;
import com.jz.crm.base.config.SystemCode;
import com.jz.crm.base.exception.ApplicationException;

public class SessionManager{
	/**
	 * 获取Session的登录用户
	 * @param session
	 * @return
	 * @throws ApplicationException 
	 */
	public static LoginUser getSession(HttpSession session) throws ApplicationException {
		LoginUser loginUser = (LoginUser) session.getAttribute(ConstantContext.LOGIN_USER);
		
		if(loginUser==null){
			throw new ApplicationException(SystemCode.LOGIN_TIME_OUT, ConstantContext.NO_LOGIN_MESSAGE);
		}
		return loginUser;
	}
	
	/**
	 * 设置Session的登录用户
	 * @param session
	 * @param t
	 */
	public static void setSession(HttpSession session,LoginUser loginUser){
		session.setAttribute(ConstantContext.LOGIN_USER, loginUser);
	}
	
	/**
	 * 移除session
	 * @param session
	 */
	public static void removeSession(HttpSession session){
		session.removeAttribute(ConstantContext.LOGIN_USER);
	}
	
	
	public static void setAttribute(HttpSession session,String key,Object obj){
		session.setAttribute(key, obj);
	}
	
	public static Object getAttribute(HttpSession session,String key){
		return session.getAttribute(key);
	}

}
