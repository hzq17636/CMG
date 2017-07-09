package com.jz.crm.controllers.rb;

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
import com.jz.crm.base.controller.BaseWebController;
import com.jz.crm.base.exception.ApplicationException;
import com.jz.crm.base.manager.SessionManager;
import com.jz.crm.base.util.ImageUtil;
import com.jz.crm.models.mg.CompanyInfo;
import com.jz.crm.models.mg.UserInfo;
import com.jz.crm.models.rb.RbCollectRecord;
import com.jz.crm.models.rb.RbuserInfo;
import com.jz.crm.models.rb.dto.CollectCompany;
import com.jz.crm.models.rb.dto.RedBookMenu;
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
@RequestMapping(value = "/redbook")
public class RedBookController  extends BaseWebController{
	public static final Logger LOG = LoggerFactory.getLogger(RedBookController.class);

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
	 * 红包书 详情页
	 * @param collectRecord
	 * @param session
	 * @return
	 * @throws ApplicationException
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCompanyDetail.action")
	public Map<String, Object> queryCompanyDetail(@RequestBody RbCollectRecord collectRecord, HttpSession session)
			throws ApplicationException {
		Map<String,Object> map = new HashMap<String,Object>();
		Assert.notNull(collectRecord, "传参不能为空");
		Assert.notNull(collectRecord.getCompanyId(), "公司id不能为空");
		RbuserInfo rbuserInfo = (RbuserInfo)SessionManager.getSession(session);
		RbuserInfo rbuserInfo2 = rbuserInfoService.getOneBy(RbuserInfo.DBStrUserName, rbuserInfo.getUserName());
		CompanyInfo companyInfo = companyInfoService.get(collectRecord.getCompanyId());
		if((companyInfo.getCompressLogo()!=null && !"".equals(companyInfo.getCompressLogo())) 
				&& (!"无".equals(companyInfo.getCompressLogo()))){
			String logo_prefix = JZMushroom.getInstance().get("compressLogo.dir");
			try {
				LOG.info("logo图片压缩--------路径："+logo_prefix+"/"+companyInfo.getCompressLogo());
				companyInfo.setCompanyLogData(ImageUtil.getImageStr(logo_prefix+"/"+companyInfo.getCompressLogo()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if((companyInfo.getCompressQrCode()!=null && !"".equals(companyInfo.getCompressQrCode())) && (!"无".equals(companyInfo.getCompressQrCode()))){
			String qrCode_prefix = JZMushroom.getInstance().get("compressQrCode.dir"); //二维码
			try {
				LOG.info("二维码图片压缩--------路径："+qrCode_prefix+"/"+companyInfo.getCompressQrCode());
				companyInfo.setCompanyQrCodeData(ImageUtil.getImageStr(qrCode_prefix+"/"+companyInfo.getCompressQrCode()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		RbCollectRecord collectRecord2 = rbCollectRecordService.queryRbCollectRecord(collectRecord.getCompanyId(), rbuserInfo2.getId());
		if(collectRecord2 != null){
			companyInfo.setIsCollect(CompanyInfo.ISCOLLECT_YES);
		}
		map.put("companyInfo", companyInfo);
		//map.put("collectRecord", collectRecord2);
		return getMap(map);
	}

	/**
	 * hr 红宝书列表
	 * @param collectRecord
	 * @param session
	 * @return
	 * @throws ApplicationException
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCompanyListBy.action")
	public Map<String, Object> queryCompanyListBy(@RequestBody RbCollectRecord collectRecord, HttpSession session) 
			throws ApplicationException {
		Map<String,Object> map = new HashMap<String,Object>();
		RbuserInfo rbuserInfo=(RbuserInfo)SessionManager.getSession(session);
		RbuserInfo rbuserInfo2 = rbuserInfoService.getOneBy(RbuserInfo.DBStrUserName, rbuserInfo.getUserName());
		List<CollectCompany> collectCompanys = rbCollectRecordService.queryRedBookList(collectRecord);
		if(collectCompanys.size()>0){
			for(CollectCompany collectCompany:collectCompanys){ 
				RbCollectRecord collectRecord2 = rbCollectRecordService.
						queryRbCollectRecord(collectCompany.getCompanyId(), rbuserInfo2.getId());
				if(collectRecord2!=null){
					collectCompany.setId(collectRecord2.getId());
					collectCompany.setUserId(collectRecord2.getUserId());
					collectCompany.setIsCollect(CollectCompany.ISCOLLECT_YES);//
				}
				
				//logo
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
				
				//二维码
				if((collectCompany.getCompressQrCode()!=null && !"".equals(collectCompany.getCompressQrCode())) 
						&& (!"无".equals(collectCompany.getCompressQrCode()))){
					String qrCode_prefix = JZMushroom.getInstance().get("compressQrCode.dir"); //二维码
					try {
						LOG.info("二维码图片压缩--------路径："+qrCode_prefix+"/"+collectCompany.getCompressQrCode());
						collectCompany.setCompanyQrCodeData(ImageUtil.getImageStr(qrCode_prefix+"/"+collectCompany.getCompressQrCode()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		//map.put("rbuserInfo", rbuserInfo2);
		map.put("collectCompanys", collectCompanys);
		return getMap(map);
	}
	
	
	/**
	 * hr红宝书-菜单栏
	 * @return
	 * @throws ApplicationException
	 */
	@ResponseBody
	@RequestMapping(value = "/queryRedBookMenu.action")
	public Map<String, Object> queryRedBookMenu() throws ApplicationException {
		Map<String,Object> map = new HashMap<String,Object>();
		List<RedBookMenu> redBookMenus = companyInfoService.RedBookMenu();
		map.put("redBookMenus", redBookMenus);
		return getMap(map);
	}
}
