package com.jz.crm.base.config;

public class SystemCode {

	/**
	 * common
	 */
	public final static int SUCCESS = 200;// 正常
	public static final int REQUEST_PARAMETER_FAILURE = 210;// 参数错误
	public static final int LOGIN_TIME_OUT = 250;// 超时或未登录
	public static final int LOGIN_NO_USER = 255;// 数据库中无此用户
	
	public static final int MAIL_SEND_FAILURE = 320;
	public static final int SMS_SEND_FAILURE = 330;
	public final static int AGAIN_REQUEST_EXCEPTION = 330;
	public final static int VARIABLE_NOT_REPLACE_EXCEPTION = 350;//请求request变量未替换
	
	public final static int NO_PERMISSION = 400;//无权限回复
	
	public final static int INTEGRAL_INSUFFICIENT = 410;//积分余额不足
	public final static int USER_NO_AUTHENTICATION = 420;//用户未认证
	

	public final static int SYSTEM_EXCEPTION = 500;// 未知异常

}
