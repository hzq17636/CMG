package com.jz.crm.controllers.rb;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.jz.crm.base.config.SystemCode;
import com.jz.crm.base.controller.BaseWebController;
import com.jz.crm.base.exception.ApplicationException;
import com.jz.crm.base.manager.SessionManager;
import com.jz.crm.models.comm.Constant;
import com.jz.crm.models.rb.RbShareRecord;
import com.jz.crm.models.rb.RbuserInfo;
import com.jz.crm.models.rb.dto.ShareDto;
import com.jz.crm.services.mg.CompanyInfoService;
import com.jz.crm.services.rb.RbCollectRecordService;
import com.jz.crm.services.rb.RbShareRecordService;
import com.jz.crm.services.rb.RbuserInfoService;

/**
 * 
 * 公司
 * @author hzq
 * 
 */
@Controller
@RequestMapping(value = "/share")
public class ShareController  extends BaseWebController{
	public static final Logger LOG = LoggerFactory.getLogger(ShareController.class);

	@Autowired
	@Qualifier("rbuserInfoService")
	private RbuserInfoService rbuserInfoService;

	@Autowired
	@Qualifier("companyInfoService")
	private CompanyInfoService companyInfoService;
	
	@Autowired
	@Qualifier("rbCollectRecordService")
	private RbCollectRecordService rbCollectRecordService;
	
	@Autowired
	@Qualifier("rbShareRecordService")
	private RbShareRecordService rbShareRecordService;
	
	/**
	 * 获取分享用户信息
	 * @param bhShareRecord
	 * @return
	 * @throws ApplicationException
	 */
	@ResponseBody
	@RequestMapping(value = "/getShareListBy.action")
	public Map<String, Object> getShareListByUserId(@RequestBody ShareDto shareDto) throws ApplicationException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ShareDto> shareDtos = rbShareRecordService.getShareCount(shareDto);
		map.put("shareDtos", shareDtos);
		map.put("totalResult", shareDto.getTotalResult());
 		map.put("totalPage", shareDto.getTotalPage());		
		return getMap(map);
	}
	
	/**
	 * 分享明细
	 * @param reportFormDto
	 * @return
	 * @throws ApplicationException
	 */
	@ResponseBody
	@RequestMapping(value = "/getShareByuserId.action")
	public Map<String, Object> getShareCount(@RequestBody ShareDto shareDto) throws ApplicationException {
		Map<String, Object> map = new HashMap<String, Object>();
		Assert.notNull(shareDto, "传参不能为空");
		Assert.notNull(shareDto.getUserId(), "用户id不能为空");
		List<ShareDto> shareDtos = rbShareRecordService.getShareUserByUserId(shareDto);
		map.put("shareDtos", shareDtos);
		map.put("totalResult", shareDto.getTotalResult());
 		map.put("totalPage", shareDto.getTotalPage());
		return getMap(map);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/revice.action")
	public Map<String, Object> revice(@RequestBody RbShareRecord shareRecord, HttpSession session) 
			throws ApplicationException {
		Map<String,Object> map = new HashMap<String,Object>();
		Assert.notNull(shareRecord, "传参不能为空");
		Assert.notNull(shareRecord.getUserId(), "邀请人用户id不能为空");
		RbuserInfo rbuserInfo = (RbuserInfo)SessionManager.getSession(session);
		RbuserInfo rbuserInfo2 = rbuserInfoService.getOneBy(RbuserInfo.DBStrUserName, rbuserInfo.getUserName());
		int value = rbShareRecordService.insert(shareRecord);
		if(value ==  Constant.YES){
			LOG.info("分享记录成功");
		}
		return getMap();
	}
	
	
	/**
	 * 领取红宝书
	 * @param shareRecord
	 * @param session
	 * @return
	 * @throws ApplicationException
	 */
	@ResponseBody
	@RequestMapping(value = "/reviceRedBook.action")
	public Map<String, Object> reviceRedBook(@RequestBody RbShareRecord shareRecord, HttpSession session) 
			throws ApplicationException {
		Map<String,Object> map = new HashMap<String,Object>();
		RbuserInfo rbuserInfo=(RbuserInfo)SessionManager.getSession(session);
		RbuserInfo rbuserInfo2 = rbuserInfoService.getOneBy(RbuserInfo.DBStrUserName, rbuserInfo.getUserName());
		int shareCount = rbShareRecordService.getRbShareRecordList(rbuserInfo2.getId()).size();
		LOG.info("reviceRedBook=="+rbuserInfo2.getReceiveRedBook());
		LOG.info("判断是否为=="+(shareCount<2));
		if(shareCount<2){
			throw new ApplicationException(SystemCode.SYSTEM_EXCEPTION, "邀请的人数不足[]");
		}
		rbuserInfo2.setReceiveRedBook(RbuserInfo.RECEIVEREDBOOK_YES);
		rbuserInfo2.setUpdateTime(new Date());
		rbuserInfoService.update(rbuserInfo2);
		map.put("rbuserInfo", rbuserInfo2);
		return getMap(map);
	}
	
	
	/**
	 * 我的邀请 列表
	 * @param shareRecord
	 * @param session
	 * @return
	 * @throws ApplicationException
	 */
	@ResponseBody
	@RequestMapping(value = "/queryShareList.action")
	public Map<String, Object> queryCompanyListBy(@RequestBody RbShareRecord shareRecord, HttpSession session) 
			throws ApplicationException {
		Map<String,Object> map = new HashMap<String,Object>();
		RbuserInfo rbuserInfo=(RbuserInfo)SessionManager.getSession(session);
		RbuserInfo rbuserInfo2 = rbuserInfoService.getOneBy(RbuserInfo.DBStrUserName, rbuserInfo.getUserName());
		List<RbShareRecord> shareRecords = rbShareRecordService.getRbShareRecordList(rbuserInfo2.getId());
		map.put("shareRecords", shareRecords);
		return getMap(map);
	}
	
	
	
}
