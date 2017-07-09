package com.jz.crm.controllers.rb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jz.crm.base.config.ConstantContext;
import com.jz.crm.base.config.JZMushroom;
import com.jz.crm.base.config.SystemCode;
import com.jz.crm.base.controller.BaseWebController;
import com.jz.crm.base.exception.ApplicationException;
import com.jz.crm.base.httprequest.HttpRequest;
import com.jz.crm.base.manager.SessionManager;
import com.jz.crm.base.sms.SimplieSMSSender;
import com.jz.crm.base.util.DateTimeUtil;
import com.jz.crm.base.util.DbUntils;
import com.jz.crm.base.util.JsonUtil;
import com.jz.crm.models.rb.RbuserInfo;
import com.jz.crm.services.mg.CompanyInfoService;
import com.jz.crm.services.rb.RbCollectRecordService;
import com.jz.crm.services.rb.RbuserInfoService;

/**
 * 
 * 用户
 * @author hzq
 * 
 */
@Controller
@RequestMapping(value = "/rbUser")
public class RbUserController  extends BaseWebController{
	public static final Logger LOG = LoggerFactory.getLogger(RbUserController.class);

	@Autowired
	@Qualifier("rbuserInfoService")
	private RbuserInfoService rbuserInfoService;

	@Autowired
	@Qualifier("companyInfoService")
	private CompanyInfoService companyInfoService;

	@Autowired
	@Qualifier("rbCollectRecordService")
	private RbCollectRecordService rbCollectRecordService;


	/**
	 * 微信授权
	 * @param params
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/authorization.action")
	public Map<String, Object> authorization(@RequestBody Map<String, String> params, HttpSession session)
			throws Exception {
		Assert.notNull(params, "请求参数不能为空");
		Assert.hasLength(params.get("openId"), "openId不能为空");
		Map<String,Object> map = new HashMap<String,Object>();
		String openId = params.get("openId");
		if (openId == null) {
			throw new ApplicationException(SystemCode.REQUEST_PARAMETER_FAILURE, "openId不能为空[]");
		}
		RbuserInfo dbRbuserInfo = rbuserInfoService.getOneBy(RbuserInfo.DBStrOpenId, openId);
		if (dbRbuserInfo == null) {
			dbRbuserInfo= getRbuserInfoByApp(openId);//通过openId 从帮帮用户表里查是否存在 此用户
			//rbuserInfo.setHeadimgurl(headimgurl);
			if(dbRbuserInfo==null){
				dbRbuserInfo = new RbuserInfo();
				dbRbuserInfo.setSource(RbuserInfo.SOURCE_OTHER);
			}else{
				dbRbuserInfo.setSource(RbuserInfo.SOURCE_BB);//帮帮过来的
			}
			dbRbuserInfo.setOpenId(openId);
			dbRbuserInfo.setHasReg(RbuserInfo.HASREG_NO);
			
			if (params.get("fromUserId") != null) {
				dbRbuserInfo.setFromUserId(Long.parseLong(params.get("fromUserId")));
			}
			rbuserInfoService.insertAndUpdateObj(dbRbuserInfo);
		}

		// 登录成功后保存session
		SessionManager.setSession(session, dbRbuserInfo);
		map.put("dbRbuserInfo", dbRbuserInfo);
		return getMap(map);
	}

	/**
	 * 发送短信获取验证码
	 * @param rbuserInfo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/sendVerificationCode.action")
	public Map<String, Object> sendVerificationCode(@RequestBody RbuserInfo rbuserInfo, HttpSession session)
			throws Exception {
		// check 参数
		Assert.notNull(rbuserInfo, "请求参数不能为空");
		if (rbuserInfo.getMobile() == null ) {
			throw new ApplicationException(SystemCode.REQUEST_PARAMETER_FAILURE, "手机号不能为空");
		}
		int verificationCode =(int) ((Math.random() * 9 + 1) * 100000);
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("verificationCode", "" + verificationCode);
		parameter.put("bindlingTime", "" + JZMushroom.getInstance().getInt("mail.effective.minute"));
		if (rbuserInfo.getMobile() != null && !"".equals(rbuserInfo.getMobile())) {
			parameter.put("userName", rbuserInfo.getMobile());
			SimplieSMSSender.sendTemplateSMS(rbuserInfo.getMobile(), JZMushroom.getInstance().get("SMS.login.template.id"),
					new String[] { parameter.get("verificationCode"), parameter.get("bindlingTime") });
			SessionManager.setAttribute(session, ConstantContext.BINDING_MOBILE, rbuserInfo.getMobile());
		}
		SessionManager.setAttribute(session, ConstantContext.BINDING_VERIFICATION_CODE, "" + verificationCode);
		LOG.info("红宝书verificationCode==="+SessionManager.getAttribute(session, ConstantContext.BINDING_VERIFICATION_CODE));
		SessionManager.setAttribute(session, ConstantContext.BINDING_EFFECTIVE, new Date());
		return getMap();
	}

	/**
	 * 用户登录
	 * 
	 * @param userInfo
	 * @param map
	 * @param session
	 * @throws ApplicationException
	 */
	@ResponseBody
	@RequestMapping(value = "/login.action")
	public Map<String, Object> userLogin(@RequestBody RbuserInfo rbuserInfo, HttpSession session)
			throws ApplicationException {
		// check 参数
		Assert.notNull(rbuserInfo, "请求参数不能为空");
		Assert.hasLength(rbuserInfo.getOpenId(), "openId不能为空");
		RbuserInfo dbUserInfo = null;
		if (rbuserInfo.getMobile() != null && !"".equals(rbuserInfo.getMobile().trim())) {
			String mobile = (String) SessionManager.getAttribute(session, ConstantContext.BINDING_MOBILE);
			if (mobile == null || !rbuserInfo.getMobile().equals(mobile)) {
				throw new ApplicationException(SystemCode.REQUEST_PARAMETER_FAILURE, "输入的手机号不正确");
			}
			dbUserInfo = rbuserInfoService.getOneBy(RbuserInfo.DBStrOpenId, rbuserInfo.getOpenId());
			if(dbUserInfo !=null && mobile.equals(dbUserInfo.getMobile())){
				if(dbUserInfo.getHasReg() == RbuserInfo.HASREG_YES && rbuserInfo.getHasReg()==RbuserInfo.HASREG_NO){
					throw new ApplicationException(SystemCode.REQUEST_PARAMETER_FAILURE, "此手机号已注册过");
				}
			}
		}

		LOG.info("ConstantContext.BINDING_VERIFICATION_CODE="+ConstantContext.BINDING_VERIFICATION_CODE);

		// 检验验证码
		String verifyCode = (String) SessionManager.getAttribute(session, ConstantContext.BINDING_VERIFICATION_CODE);
		LOG.info("verifyCode="+verifyCode);
		if (verifyCode == null || !rbuserInfo.getVerificationCode().equals(verifyCode)) {
			throw new ApplicationException(SystemCode.REQUEST_PARAMETER_FAILURE, "输入的验证码不正确");
		}

		// 检验有效期
		Date effectiveTime = (Date) SessionManager.getAttribute(session, ConstantContext.BINDING_EFFECTIVE);
		LOG.info("effectiveTime="+effectiveTime);
		checkVerificationCodeEffective(effectiveTime, JZMushroom.getInstance().getInt("mail.effective.minute"));

		dbUserInfo = rbuserInfo;
		//插入用户信息
		if (dbUserInfo.getHasReg() == null || (dbUserInfo.getHasReg() != RbuserInfo.HASREG_YES)) {//没注册过
			dbUserInfo.setHasReg(RbuserInfo.HASREG_YES);
			dbUserInfo.setReceiveRedBook(RbuserInfo.RECEIVEREDBOOK_NO);//没有领过红宝书
		}
		rbuserInfoService.insertAndUpdateObj(dbUserInfo);		
		// 登录成功后保存session
		SessionManager.setSession(session, dbUserInfo);
		return getMap(dbUserInfo);
	}

	
	
	
	@RequestMapping(value = "/loginOut.action")
	public Map<String, Object> loginOut(ModelMap map, HttpSession session) {
		SessionManager.removeSession(session);
		return getMap();
	}


	/**
	 * 用来测试用的登录
	 * 
	 * @param userInfo
	 * @param session
	 * @return
	 * @throws ApplicationException
	 */
	@ResponseBody
	@RequestMapping(value = "/testLogin.action")
	public Map<String, Object> testLogin(@RequestBody RbuserInfo rbuserInfo, HttpSession session)
			throws ApplicationException {
		rbuserInfo = rbuserInfoService.get(rbuserInfo.getId());
		SessionManager.setSession(session, rbuserInfo);
		return this.getMap();
	}


	/**
	 * 从帮帮那边拿数据过来
	 * @param rbuserInfo
	 * @return RbuserInfo
	 */
	public static RbuserInfo getRbuserInfoByApp(String openId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("openId", openId);
		String html = HttpRequest.sendPost(JZMushroom.getInstance().get("JZManagement.url") + "/bbuser/queryBBUserInfo.action", 
				JsonUtil.objectToJson(map), null);
		LOG.info("返回的html="+html);
		RbuserInfo rbuserInfo = null;
		if (html != null) {
			Map<String, Object> result = (Map<String, Object>) JsonUtil.jsonToMap(html);
			Map<String, String> body = (Map<String, String>) result.get("body");
			if(body!=null){			
				rbuserInfo = new RbuserInfo();
				rbuserInfo.setUserName(body.get("userName"));
				rbuserInfo.setPresentCompany(body.get("presentCompany"));
				rbuserInfo.setPosition(body.get("position"));
				rbuserInfo.setMobile(body.get("mobile"));
				rbuserInfo.setEmail(body.get("email"));			
			}
		}

		return rbuserInfo;
	}

	private Map<String, Object> checkVerificationCodeEffective(Date startTime, int effectiveValue)
			throws ApplicationException {
		Assert.notNull(startTime, "请先发认证请求");
		Calendar ca = Calendar.getInstance();
		ca.setTime(startTime);
		ca.add(Calendar.MINUTE, effectiveValue);
		int value = DateTimeUtil.compareDate(new Date(), ca.getTime());
		if (value == 1) {
			throw new ApplicationException(SystemCode.REQUEST_PARAMETER_FAILURE, "验证码已过期");
		}
		return getMap();
	}
}
