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

import com.jz.crm.base.config.JZMushroom;
import com.jz.crm.base.config.SystemCode;
import com.jz.crm.base.controller.BaseWebController;
import com.jz.crm.base.exception.ApplicationException;
import com.jz.crm.base.manager.SessionManager;
import com.jz.crm.base.util.ImageUtil;
import com.jz.crm.models.comm.Constant;
import com.jz.crm.models.rb.RbCollectRecord;
import com.jz.crm.models.rb.RbuserInfo;
import com.jz.crm.models.rb.dto.CollectCompany;
import com.jz.crm.services.mg.CompanyInfoService;
import com.jz.crm.services.rb.RbCollectRecordService;
import com.jz.crm.services.rb.RbuserInfoService;

/**
 * 
 * 公司
 * @author hzq
 * 
 */
@Controller
@RequestMapping(value = "/collect")
public class CollectController  extends BaseWebController{
	public static final Logger LOG = LoggerFactory.getLogger(CollectController.class);

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
	 * 收藏公司
	 * @param collectRecord
	 * @param session
	 * @return 
	 * @throws ApplicationException
	 */
	@ResponseBody
	@RequestMapping(value = "/collect.action")
	public Map<String, Object> collect(@RequestBody RbCollectRecord collectRecord,HttpSession session) throws ApplicationException {
		Assert.notNull(collectRecord, "传参不能为空");
		Assert.notNull(collectRecord.getCompanyId(), "公司id不能为空");
		RbuserInfo rbuserInfo=(RbuserInfo)SessionManager.getSession(session);
		RbuserInfo rbuserInfo2 = rbuserInfoService.getOneBy(RbuserInfo.DBStrUserName, rbuserInfo.getUserName());
		//判断此公司是否已经收藏过，存在说明已经收藏过
		if(rbCollectRecordService.queryRbCollectRecord(collectRecord.getCompanyId(), rbuserInfo2.getId())!=null){
			throw new ApplicationException(SystemCode.SYSTEM_EXCEPTION, "已收藏[]");
		}
		collectRecord.setUserId(rbuserInfo2.getId());
		collectRecord.setCreateBy(rbuserInfo.getUserName());
		collectRecord.setCreateTime(new Date());
		int value = rbCollectRecordService.insert(collectRecord);
		if(value==Constant.NO){
			throw new ApplicationException(SystemCode.SYSTEM_EXCEPTION, "收藏失败[]");
		}
		LOG.info("公司id[{}],用户id[{}] 收藏成功",collectRecord.getCompanyId(),collectRecord.getUserId());
		return getMap();
	}
	
	/**
	 * 取消收藏
	 * @param collectRecord
	 * @return
	 * @throws ApplicationException
	 */
	@ResponseBody
	@RequestMapping(value = "/cancelCollect.action")
	public Map<String, Object> cancelCollect(@RequestBody RbCollectRecord collectRecord,HttpSession session) 
			throws ApplicationException {
		Assert.notNull(collectRecord, "传参不能为空");
		Assert.notNull(collectRecord.getCompanyId(), "公司id不能为空");
		RbuserInfo rbuserInfo=(RbuserInfo)SessionManager.getSession(session);
		RbuserInfo rbuserInfo2 = rbuserInfoService.getOneBy(RbuserInfo.DBStrUserName, rbuserInfo.getUserName());
		int value = rbCollectRecordService.deleteCollectRecord(collectRecord.getCompanyId(), rbuserInfo2.getId());
		if(value==Constant.YES){
			LOG.info("companyId[{}]取消收藏成功",collectRecord.getCompanyId());
		}
		return getMap();
	}
	

	/**
	 * 我的收藏（查询）
	 * @param collectRecord
	 * @return
	 * @throws ApplicationException
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCollectCompany.action")
	public Map<String, Object> queryCompanyListBy(@RequestBody RbCollectRecord collectRecord,HttpSession session) throws ApplicationException {
		Map<String,Object> map = new HashMap<String,Object>();
		RbuserInfo rbuserInfo=(RbuserInfo)SessionManager.getSession(session);
		List<CollectCompany> collectCompanies = rbCollectRecordService.queryCollectCompany(collectRecord,rbuserInfo);
		if(collectCompanies.size()>0){
			for(CollectCompany collectCompany:collectCompanies){
				collectCompany.setIsCollect(CollectCompany.ISCOLLECT_YES);
				//logo压缩图片
				if((collectCompany.getCompressLogo()!=null && !"".equals(collectCompany.getCompressLogo()))
						&& !"无".equals(collectCompany.getCompressLogo())){
					String logo_prefix = JZMushroom.getInstance().get("compressLogo.dir");
					try {
						LOG.info("logo图片压缩--------路径："+logo_prefix+"/"+collectCompany.getCompressLogo());
						collectCompany.setCompanyLogData(ImageUtil.getImageStr(logo_prefix+"/"+collectCompany.getCompressLogo()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				//二维码压缩图片
				if((collectCompany.getCompressQrCode()!=null && !"".equals(collectCompany.getCompressQrCode()))
						&& (!"无".equals(collectCompany.getCompressQrCode()))){
					String qrCode_prefix = JZMushroom.getInstance().get("compressQrCode.dir"); //二维码
					try {
						LOG.info("二维码压缩图片--------路径："+qrCode_prefix+"/"+collectCompany.getCompressQrCode());
						collectCompany.setCompanyQrCodeData(ImageUtil.getImageStr(qrCode_prefix+"/"+collectCompany.getCompressQrCode()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		map.put("collectCompanies", collectCompanies);
		return getMap(map);
	}
	
}
