package com.jz.kafka.config;

import java.util.HashMap;
import java.util.Map;

import com.jz.crm.base.config.JZMushroom;
import com.jz.crm.base.config.ConstantContext;
import com.jz.crm.base.httprequest.HttpRequest;
import com.jz.crm.base.util.JsonUtil;

public class KafkaConfig {
	public static final int TRANSACTION_TYPE_SEND_EMAIL_NOTIFICATION = 100;// 订单邮件通知
	public static final int TRANSACTION_TYPE_PAY_NOTIFY = 200;// 在线支付交易类型
	public static final int TRANSACTION_TYPE_SEND_EMAIL = 300;// 发邮件
	public static final int TRANSACTION_TYPE_SEND_SMS = 400;// 发消息

	public static final String EMAIL = "email";
	public static final String MOBILE = "mobile";
	public static final String FILE = "file";
	public static final String TEMPLATEID = "templateID";
	public static final String PARAMS = "params";

	/**
	 * 封装kafka请求格式参数
	 * 
	 * @param transactionType
	 *            交易类型
	 * @param obj
	 *            body参数
	 * @return
	 */
	public static String assembledRequest(int transactionType, Object body) {
		Map<String, Object> request = new HashMap();
		request.put("transactionType", transactionType);
		request.put("body", body);
		return JsonUtil.objectToJson(request);
	}

	/**
	 * 通知Kafka发送邮件通知真服人员
	 * 
	 * @param toEmail
	 * @param fileName
	 * @param parameter
	 * @throws Exception
	 */
	public static void sendMailNotify(String orderType) throws Exception {
		Map<String, String> kafkaParams = new HashMap<String, String>();
		kafkaParams.put("orderType", orderType);
		HttpRequest.sendPost(JZMushroom.getInstance().get("kafka.url"),
				KafkaConfig.assembledRequest(KafkaConfig.TRANSACTION_TYPE_SEND_EMAIL_NOTIFICATION, kafkaParams), null);
	}

	/**
	 * 通知Kafka发送邮件
	 * 
	 * @param toEmail
	 * @param fileName
	 * @param parameter
	 * @throws Exception
	 */
	public static void sendMail(String toEmail, String fileName, Map<String, String> parameter) throws Exception {
		Map<String, Object> kafkaParams = new HashMap<String, Object>();
		kafkaParams.put(KafkaConfig.EMAIL, toEmail);
		kafkaParams.put(KafkaConfig.FILE, fileName);
		kafkaParams.put(KafkaConfig.PARAMS, parameter);
		HttpRequest.sendPost(JZMushroom.getInstance().get("kafka.url"),
				KafkaConfig.assembledRequest(KafkaConfig.TRANSACTION_TYPE_SEND_EMAIL, kafkaParams), null);
	}

	/**
	 * 发送短消息
	 * 
	 * @param mobile
	 * @param templateID
	 * @param parameter
	 */
	public static void sendTemplateSMS(String mobile, String templateID, String[] parameter) {
		Map<String, Object> kafkaParams = new HashMap<String, Object>();
		kafkaParams.put(KafkaConfig.MOBILE, mobile);
		kafkaParams.put(KafkaConfig.TEMPLATEID, templateID);
		kafkaParams.put(KafkaConfig.PARAMS, parameter);
		kafkaParams.put("appId", JZMushroom.getInstance().get("SMS.AppID"));
		HttpRequest.sendPost(JZMushroom.getInstance().get("kafka.url"),
				KafkaConfig.assembledRequest(KafkaConfig.TRANSACTION_TYPE_SEND_SMS, kafkaParams), null);
	}

}
