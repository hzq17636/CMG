package com.jz.crm.controllers.mg;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jz.crm.base.config.ConstantContext;
import com.jz.crm.base.config.JZMushroom;
import com.jz.crm.base.config.SystemCode;
import com.jz.crm.base.controller.BaseWebController;
import com.jz.crm.base.email.SimpleMailSender;
import com.jz.crm.base.exception.ApplicationException;
import com.jz.crm.base.manager.SessionManager;
import com.jz.crm.base.util.DateTimeUtil;
import com.jz.crm.base.util.MD5;
import com.jz.crm.base.util.SecUtil;
import com.jz.crm.models.mg.UserInfo;
import com.jz.crm.services.mg.UserInfoService;

/**
 * 
 *  用户 
 * @author hzq
 * 
 */
@Controller
@RequestMapping(value = "/user")
public class UsersController  extends BaseWebController{
	public static final Logger LOG = LoggerFactory.getLogger(UsersController.class);

	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;
	
	/**
	 * 真服密码同步
	 * @param userInfo
	 * @param session
	 * @return
	 * @throws ApplicationException
	 */
	@ResponseBody
	@RequestMapping(value = "/sysnUser.action")
	public Map<String, Object> sysnUser(@RequestBody UserInfo userInfo, HttpSession session)
			throws ApplicationException {
		UserInfo dbUserInfo = userInfoService.getOneBy(UserInfo.DBStrUserName, userInfo.getUserName());
		if(dbUserInfo==null){
			throw new ApplicationException(SystemCode.LOGIN_NO_USER, "用户不存在[]");
		}
		dbUserInfo.setPasswd(userInfo.getPasswd());
		dbUserInfo.setUpdateBy(userInfo.getUserName());
		dbUserInfo.setUpdateTime(new Date());
		userInfoService.update(dbUserInfo);
		return getMap();
	}
	
	/**
	 * 真服用户直接登录接口
	 * @param userInfo
	 * @param session
	 * @return
	 * @throws ApplicationException
	 */
	@ResponseBody
	@RequestMapping(value = "/login1.action")
	public Map<String, Object> usersInfoLogin1(@RequestBody UserInfo userInfo, HttpSession session)
			throws ApplicationException {
		Map<String,Object> map = new HashMap<String,Object>();
		// check 参数
		Assert.notNull(userInfo, "请求参数不能为空");
		Assert.hasLength(userInfo.getUserName(), "用户名不能空");
		Assert.hasLength(userInfo.getPasswd(), "密码不能空");
		UserInfo dbUserInfo = userInfoService.getOneBy(UserInfo.DBStrUserName, userInfo.getUserName());
		if(dbUserInfo == null){
			throw new ApplicationException(SystemCode.REQUEST_PARAMETER_FAILURE, "用户名不存在[]");
		}
		//String passwd = SecUtil.encrypt(userInfo.getPasswd());//加密
		if(!dbUserInfo.getPasswd().equals(userInfo.getPasswd())){
			throw new ApplicationException(SystemCode.REQUEST_PARAMETER_FAILURE, "密码有误[]");
		}
		LOG.info("用户名[{}]-------------登录成功",dbUserInfo.getUserName());

		// 登录成功后保存session
		SessionManager.setSession(session, dbUserInfo);
		return getMap(dbUserInfo);
	}

	
	/**
	 * 用户登录
	 * @param userInfo
	 * @param session
	 * @return
	 * @throws ApplicationException
	 */
	@ResponseBody
	@RequestMapping(value = "/login.action")
	public Map<String, Object> usersInfoLogin(@RequestBody UserInfo userInfo, HttpSession session)
			throws ApplicationException {
		Map<String,Object> map = new HashMap<String,Object>();
		// check 参数
		Assert.notNull(userInfo, "请求参数不能为空");
		Assert.hasLength(userInfo.getUserName(), "用户名不能空");
		Assert.hasLength(userInfo.getPasswd(), "密码不能空");
		UserInfo dbUserInfo = userInfoService.getOneBy(UserInfo.DBStrUserName, userInfo.getUserName());
		if(dbUserInfo == null){
			throw new ApplicationException(SystemCode.REQUEST_PARAMETER_FAILURE, "用户名不存在[]");
		}
		String passwd = MD5.toMD5(userInfo.getPasswd());//SecUtil.encrypt(userInfo.getPasswd());//加密
		if(!dbUserInfo.getPasswd().equals(passwd)){
			throw new ApplicationException(SystemCode.REQUEST_PARAMETER_FAILURE, "密码有误[]");
		}
		LOG.info("用户名[{}]-------------登录成功",dbUserInfo.getUserName());

		// 登录成功后保存session
		SessionManager.setSession(session, dbUserInfo);
		return getMap(dbUserInfo);
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
	@RequestMapping(value = "/loginOld.action")
	public Map<String, Object> usersInfoLoginO(@RequestBody UserInfo userInfo, HttpSession session)
			throws ApplicationException {
		Map<String,Object> map = new HashMap<String,Object>();
		// check 参数
		Assert.notNull(userInfo, "请求参数不能为空");
		UserInfo dbUserInfo = null;
		Assert.hasLength(userInfo.getVerificationCode(), "邮箱验证码不能空");
		if (userInfo.getUserName() != null && !"".equals(userInfo.getUserName())) {
			String userName = (String) SessionManager.getAttribute(session, ConstantContext.BINDING_EMAIL);

			if (userName == null || !userInfo.getUserName().equals(userName)) {
				throw new ApplicationException(SystemCode.SYSTEM_EXCEPTION, "邮箱不正确[]");
			}

			dbUserInfo = userInfoService.getOneBy(UserInfo.DBStrUserName, userInfo.getUserName());
		}
		
		String verifyCode = (String) SessionManager.getAttribute(session, ConstantContext.BINDING_VERIFICATION_CODE);
		 LOG.info("verifyCode="+verifyCode);
		if (verifyCode == null || !userInfo.getVerificationCode().equals(verifyCode)) {
			throw new ApplicationException(SystemCode.REQUEST_PARAMETER_FAILURE, "验证码不正确");
		}

		// 检验有效期
		Date effectiveTime = (Date) SessionManager.getAttribute(session, ConstantContext.BINDING_EFFECTIVE);
		LOG.info("effectiveTime="+effectiveTime);
		checkVerificationCodeEffective(effectiveTime, JZMushroom.getInstance().getInt("mail.effective.minute"));
		
		// 更新或插入用户信息
		if (dbUserInfo == null) {
			dbUserInfo = userInfo;
			dbUserInfo.setId(null);
		}
		dbUserInfo.setCreateTime(new Date());
		userInfoService.addOrupdateUser(dbUserInfo);//新增或修改
		// 登录成功后保存session
		SessionManager.setSession(session, dbUserInfo);
		return getMap(dbUserInfo);
	}


	/**
	 * 发送邮件
	 * @param usersInfo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/sendVerificationCode.action")
	public Map<String, Object> sendVerificationCode(@RequestBody UserInfo userInfo, HttpSession session)
			throws ApplicationException {
		Assert.notNull(userInfo, "请求参数不能为空");
		if (userInfo.getUserName() == null && "".equals(userInfo.getUserName())) {
			throw new ApplicationException(SystemCode.SYSTEM_EXCEPTION, "企业邮箱不能为空[]");
		}
		int verificationCode =((int) ((Math.random() * 9 + 1) * 100000));//随机数
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("verificationCode", "" + verificationCode);
		try {
			SimpleMailSender.sendMail(userInfo.getUserName(), ConstantContext.CANDIDDATE_FILE, parameter);
			SessionManager.setAttribute(session, ConstantContext.BINDING_EMAIL, userInfo.getUserName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		SessionManager.setAttribute(session, ConstantContext.BINDING_VERIFICATION_CODE, "" + verificationCode);
		SessionManager.setAttribute(session, ConstantContext.BINDING_EFFECTIVE, new Date());
		return getMap();
	}

	/**
	 * 退出登录 
	 * @param usersInfo
	 * @param map
	 * @param session
	 * @throws ApplicationException
	 */
	@ResponseBody
	@RequestMapping(value = "/loginOut.action")
	public Map<String, Object> loginOut(HttpSession session)throws ApplicationException {
		Map<String,Object> map = new HashMap<String,Object>();
		UserInfo usersInfo=(UserInfo)SessionManager.getSession(session);
		this.LOG.info("用户："+usersInfo.getUserName()+"----------------------------退出登录");
		SessionManager.removeSession(session);
		return getMap(map);
	}

//	@ResponseBody
//	@RequestMapping(value = "/getVerificationCode.action")
//	public Map<String, Object> getVerificationCode(@RequestBody UserInfo userInfo, HttpSession session) throws ApplicationException {
//		Assert.notNull(userInfo, "请求参数不能为空");
//		Assert.notNull(userInfo.getUserName(), "邮箱账号不能空");
//		if(pattern(userInfo.getUserName())){
//			UserInfo usersInfo2= userInfoService.getOneBy(UserInfo.DBStrUserName, userInfo.getUserName());
//			String random = nextInt();//随机生成验证码
//			if(usersInfo2 != null){
//				usersInfo2.setPasswd(random);
//				usersInfo2.setLastLoginTime(new Date());
//				userInfoService.update(usersInfo2);//把验证码存到数据库已有账户中
//			}else{
//				UserInfo usersInfo3 = (UserInfo)SessionManager.getSession(session);
//				userInfo.setPasswd(random);
//				userInfo.setCreateBy(usersInfo3.getUserName());
//				userInfo.setLastLoginTime(new Date());
//				userInfoService.insert(userInfo);//新增的
//			}
//			Map<String, String> parameter = new HashMap<String, String>();
//			parameter.put("random", random);
//			try {
//				SimpleMailSender.sendMail(userInfo.getUserName(), ConstantContext.CANDIDDATE_FILE, parameter);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			LOG.info("----------------邮箱名[{}],验证码为[{}]-----邮件发送成功",userInfo.getUserName(),random);
//		}						
//		return getMap();
//	}

	@ResponseBody
	@RequestMapping(value = "/testLogin.action")
	public Map<String, Object> testlogin(@RequestBody UserInfo usersInfo, HttpSession session) throws ApplicationException {
		UserInfo usersInfo2= userInfoService.getOneBy(UserInfo.DBStrUserName, usersInfo.getUserName());
		if(usersInfo2 != null){
			if(usersInfo.getPasswd().equals(usersInfo2.getPasswd())){
				SessionManager.setSession(session, usersInfo2);
			}
		}else{
			throw new ApplicationException(SystemCode.LOGIN_NO_USER, "数据库中无此用户[]");
		}				
		return getMap(usersInfo2);
	}

	/**
	 * 正在匹配邮箱号
	 * @param email
	 * @return boolean
	 */
	private static boolean pattern(String email){
		//电子邮件
		String check = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(email);
		boolean isMatched = matcher.matches();
		return isMatched;
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
