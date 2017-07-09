package com.jz.crm.base.config;

public final class ConstantContext {
	/**
	 * 日期格式
	 */
	public final static String DATE_FORMAT = "yyyyMMddHHmmss";
	public final static String DAY_DATE_FORMAT = "yyyy-MM-dd";
	public final static String MS_DATE_FORMAT = "yyyyMMddHHmmssSS";
	public final static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public final static String POINT_TIME_FORMAT = "yyyy.MM.dd HH:mm:ss";

	/**
	 * 文件路径
	 */
	public static final String RESOURCE_CLASSPATH = "classpath";
	public static final String DEFAULT_COMPANY = "深圳市较真技术有限公司";
	public static final Long DEFAULT_JZ_ID = 1L;
	public static String OPERATOR_LOGIN_FILE = RESOURCE_CLASSPATH + ":file/operator_login.txt";
	public static String OPERATOR_BINDINGMOBILE_FILE = RESOURCE_CLASSPATH + ":file/operator_binding.txt";
	public static String CANDIDDATE_FILE = RESOURCE_CLASSPATH + ":file/candiddate.txt";
	public static String TRANSMIT_OTHER_FILE = RESOURCE_CLASSPATH + ":file/transmit_other.txt";
	public static String FEEDBACK_FILE = RESOURCE_CLASSPATH + ":file/feedback.txt";
	public static String AUTHORIZATION_FILE = RESOURCE_CLASSPATH + ":file/authorization.txt";
	public static String AUTHORIZATION_ATTACH_FILE = RESOURCE_CLASSPATH + ":file/授权书（中英文）.doc";
	public static String REPORT_FILE = RESOURCE_CLASSPATH + ":file/report.txt";
	/**
	 * 如果设置些值 就不需要登录
	 */
	public static final String TOKEN = "token";
	public static final String SYSTEM_ID = "systemId";
	public final static String LOGIN_USER = "userLogin";

	public static final int WARN_TYPE_WECHAT_MESSAGE = 1;// 微信消息提醒
	public static final int WARN_TYPE_EMAIL = 2;// email发送
	public static final int WARN_TYPE_MOBILE = 3;// 手机号发送

	public static final int HAS_OPEN_NO = 0;// 公开
	public static final int HAS_OPEN_YES = 1;// 不公开

	public static final String RESPONSE_CODE = "responseCode";
	public static final String MESSAGE = "message";
	public static final String RESPONSE_Body = "body";
	public static final String NO_LOGIN_MESSAGE = "未登录或登录超时";

	/**
	 * 绑定常量定义
	 */
	public static final String BINDING_MOBILE = "bingingMobile";
	public static final String BINDING_EMAIL = "bingingEmail";
	public static final String BINDING_VERIFICATION_CODE = "bingingVerificationCode";
	public static final String BINDING_EFFECTIVE = "bingingEffective";

	/**
	 * 分页常量设置
	 */
	public static final String PAGE_CURRENT_NO = "currentPageNo";
	public static final String PAGE_SIZE = "pageSize";
	public static final int PAGE_SIZE_VALUE = 50;

	public static final int RES_CODE_NO_DATA = 0;// 查询无记录
	public static final int RES_CODE_SUCCESS = 1;// 成功
	public static final int RES_CODE_FAILURE = 2;// 查询失败
	public static final int RES_CODE_NO_QUERY = 3;// 未查询
	public static final int RES_CODE_REFUSED_QUERY = 4;// 拒绝查询

	/**
	 * 设置各系统ID
	 */
	public static final String SYSTEM_ID_BANBAN = "8";// 帮帮

}
