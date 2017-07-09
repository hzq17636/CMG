package com.jz.crm.controllers.mg;

import java.io.File;
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
import com.jz.crm.base.util.DateTimeUtil;
import com.jz.crm.base.util.EnterpriseWebsiteUtil;
import com.jz.crm.base.util.ImageCompressUtil;
import com.jz.crm.base.util.ImageUtil;
import com.jz.crm.models.comm.Constant;
import com.jz.crm.models.dto.EnterpriseInfoDto;
import com.jz.crm.models.mg.CompanyInfo;
import com.jz.crm.models.mg.UserInfo;
import com.jz.crm.services.mg.CompanyInfoService;
import com.jz.crm.services.mg.UserInfoService;

/**
 * 
 * 公司
 * @author hzq
 * 
 */
@Controller
@RequestMapping(value = "/company")
public class CompanyController  extends BaseWebController{
	public static final Logger LOG = LoggerFactory.getLogger(CompanyController.class);

	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;

	@Autowired
	@Qualifier("companyInfoService")
	private CompanyInfoService companyInfoService;
	
	/**
	 * 根据行业或者录入人查询企业信息
	 * @param companyInfo
	 * @param session
	 * @return
	 * @throws ApplicationException
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCompanysBy.action")
	public Map<String, Object> queryCompanysBy(@RequestBody CompanyInfo companyInfo, HttpSession session) throws ApplicationException {
		Assert.notNull(companyInfo, "传参不能为空");
		Map<String,Object> map = new HashMap<String,Object>();
		List<CompanyInfo> companyInfos = companyInfoService.queryCompanysBy(companyInfo);
		map.put("companyInfos", companyInfos);
		map.put("totalResult", companyInfo.getTotalResult());
		map.put("totalPage", companyInfo.getTotalPage());
		return getMap(map);
	}
	
	
	/**
	 * 企查查模糊搜索
	 * @param companyInfo
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/qichacahByName.action")
	public Map<String, Object> qichacahByName(@RequestBody CompanyInfo companyInfo, HttpSession session) throws Exception {
		Assert.notNull(companyInfo, "请求参数不能为空");
		Assert.hasLength(companyInfo.getCompanyName(), "公司名不能为空");
		Map<String,Object> map = new HashMap<String,Object>();
		List<EnterpriseInfoDto> enterpriseInfoDtos = EnterpriseWebsiteUtil.getEnterpriseInfo(companyInfo.getCompanyName());
		map.put("enterpriseInfoDtos", enterpriseInfoDtos);
		return getMap(map);
	}

	

	/**
	 * 通过公司名查询公司信息
	 * @param companyInfo
	 * @param session
	 * @return
	 * @throws ApplicationException
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCompanyByName.action")
	public Map<String, Object> queryCompanyByName(@RequestBody CompanyInfo companyInfo, HttpSession session) throws ApplicationException {
		CompanyInfo companyInfo2 = companyInfoService.getOneBy(CompanyInfo.DBStrCompanyName, companyInfo.getCompanyName());
		return getMap(companyInfo2);
	}
	

	/**
	 * 按条件查询公司
	 * @param companyInfo
	 * @param session
	 * @return 
	 * @throws ApplicationException
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCompanyListBy.action")
	public Map<String, Object> queryCompanyListBy(@RequestBody CompanyInfo companyInfo, HttpSession session) throws ApplicationException {
		Map<String,Object> map = new HashMap<String,Object>();
		List<CompanyInfo> companyInfos = companyInfoService.queryCompanyList(companyInfo);
		map.put("totalResult", companyInfo.getTotalResult());
		map.put("totalPage", companyInfo.getTotalPage());
		map.put("companyInfos", companyInfos);
		return getMap(map);
	}


	/**
	 * 修改公司
	 * @param companyInfo
	 * @param session
	 * @return
	 * @throws ApplicationException
	 */
	@ResponseBody
	@RequestMapping(value = "/updateCompany.action")
	public Map<String, Object> updateCompany(@RequestBody CompanyInfo companyInfo, HttpSession session) throws ApplicationException {
		Assert.notNull(companyInfo, "请求参数不能为空");
		Assert.notNull(companyInfo.getId(), "id不能为空");
		UserInfo usersInfo=(UserInfo)SessionManager.getSession(session);
		CompanyInfo companyInfo2 = companyInfoService.get(companyInfo.getId());
		Map<String,Object> map = new HashMap<String,Object>();
		String dateMkdir = DateTimeUtil.dateToString(new Date(), "yyyyMM");
		String logo_prefix = JZMushroom.getInstance().get("logo.dir"); //logo
		String logoCompressPrefix = JZMushroom.getInstance().get("compressLogo.dir"); //logo压缩路径
		String logoName = "logo_"+DateTimeUtil.dateToString(new Date(), "yyyyMMddHHmmsss")+"."+companyInfo.getCompanyLogType();
		LOG.info("-----------------------logo名字-----"+logoName);
		if("".equals(companyInfo.getCompanyLogType())){//删除附件
			File file =new File(logo_prefix+"/"+companyInfo.getLogo());
			File logoCompressFile =new File(logoCompressPrefix+"/"+companyInfo.getCompressLogo());
			file.delete();//删除原有的附件
			logoCompressFile.delete();
			companyInfo.setLogo("无");
			companyInfo.setCompressLogo("无");
		}
		if(companyInfo.getCompanyLogType()!=null && !"".equals(companyInfo.getCompanyLogType())){
			if((companyInfo.getLogo()!=null && !"".equals(companyInfo.getLogo())) && !"无".equals(companyInfo.getLogo())){
				File file =new File(logo_prefix+"/"+companyInfo.getLogo());
				File logoCompressFile =new File(logoCompressPrefix+"/"+companyInfo.getCompressLogo());
				file.delete();//删除原有的附件
				logoCompressFile.delete();
			}			
			File file2 = new  File(logo_prefix+"/"+dateMkdir);
			if (!file2.exists() ) {
				file2.mkdirs();			
			}
			companyInfo.setLogo(dateMkdir+"/"+generateAttach(companyInfo.getCompanyLogData(), logo_prefix+"/"+dateMkdir, logoName));
			File logoCompress = new  File(logoCompressPrefix+"/"+dateMkdir);
			if (!logoCompress.exists() ) {
				logoCompress.mkdirs();			
			}
			String logoCompressName="compress_logo_"+DateTimeUtil.dateToString(new Date(), "yyyyMMddHHmmsss")+"."+companyInfo.getCompanyLogType();
			try {
				ImageCompressUtil.saveMinPhoto(logo_prefix + "/"+ companyInfo.getLogo(), logoCompressPrefix+"/"+dateMkdir+"/"+logoCompressName, 200, 1d);
			} catch (Exception e) {
				e.printStackTrace();
			}
			companyInfo.setCompressLogo(dateMkdir+"/"+logoCompressName);
		}
		
		String qrCode_prefix = JZMushroom.getInstance().get("qrCode.dir"); //二维码
		String qrCodeCompressprefix = JZMushroom.getInstance().get("compressQrCode.dir"); //二维码压缩目录
		String qrCode_name ="qrCode_"+DateTimeUtil.dateToString(new Date(), "yyyyMMddHHmmsss")+"."+companyInfo.getCompanyQrCodeType();
		String qrCodeCompressName ="compressQrCode_"+DateTimeUtil.dateToString(new Date(), "yyyyMMddHHmmsss")+"."+companyInfo.getCompanyQrCodeType();
		LOG.info("-----------------------二维码名字-----"+qrCode_name);
		if(companyInfo.getCompanyQrCodeType()!=null && !"".equals(companyInfo.getCompanyQrCodeType())){	
			if((companyInfo.getQrCode()!=null && !"".equals(companyInfo.getQrCode())) && !"无".equals(companyInfo.getQrCode())){
				File file = new File(qrCode_prefix+"/"+companyInfo.getQrCode());
				File fileCompress = new File(qrCodeCompressprefix+"/"+companyInfo.getCompressQrCode());
				file.delete();//删除原二维码
				fileCompress.delete();
			}
			File file2 = new File(qrCode_prefix+"/"+dateMkdir);
			LOG.info("创建qrCode_prefix路径="+qrCode_prefix+"/"+dateMkdir);
			if (!file2.exists() ) {
				file2.mkdirs();			
			}
			companyInfo.setQrCode(dateMkdir+"/"+generateAttach(companyInfo.getCompanyQrCodeData(), qrCode_prefix+"/"+dateMkdir, qrCode_name));
			File compressFile = new File(qrCodeCompressprefix+"/"+dateMkdir);
			if (!compressFile.exists() ) {
				compressFile.mkdirs();			
			}
			try {
				ImageCompressUtil.saveMinPhoto(qrCode_prefix + "/"+ dateMkdir+"/"+companyInfo.getQrCode(), qrCodeCompressprefix+"/"+dateMkdir+"/"+qrCodeCompressName, 200, 1d);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//ImageCompressUtil.zipImageFile(qrCode_prefix + "/"+ qrCodeUrl, compress_qrCode+"/"+dateMkdir, 200, 200, 1f, "compress_qrCode_"+DateTimeUtil.dateToString(new Date(), "yyyyMMddHHmmsss")+"."+companyInfo.getCompanyQrCodeType());
			companyInfo.setCompressQrCode(dateMkdir+"/"+qrCodeCompressName);
		}
		if("".equals(companyInfo.getCompanyQrCodeType())){
			File file = new File(qrCode_prefix+"/"+companyInfo.getQrCode());
			File fileQrCode = new File(qrCodeCompressprefix+"/"+companyInfo.getCompressQrCode());
			file.delete();//删除原二维码
			fileQrCode.delete();
			companyInfo.setQrCode("无");
			companyInfo.setCompressQrCode("无");
		}
				
		companyInfo.setUpdateBy(usersInfo.getUserName());
		companyInfo.setUpdateTime(new Date());
		int value =companyInfoService.update(companyInfo);
		if(value == 1){
			LOG.info("--------------------修改公司成功");
		}						
		return getMap();
	}

	/**
	 * 通过id查询公司
	 * @param companyInfo
	 * @param session
	 * @return
	 * @throws ApplicationException
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCompany.action")
	public Map<String, Object> queryCompany(@RequestBody CompanyInfo companyInfo, HttpSession session) throws ApplicationException {
		Assert.notNull(companyInfo, "请求参数不能为空");
		Assert.notNull(companyInfo.getId(), "id不能为空");
		Map<String,Object> map = new HashMap<String,Object>();
		companyInfo = companyInfoService.get(companyInfo.getId());
		if((companyInfo.getLogo()!=null && !"".equals(companyInfo.getLogo())) && (!"无".equals(companyInfo.getLogo()))){
			String logo_prefix = JZMushroom.getInstance().get("logo.dir");
			try {
				LOG.info("--------logo路径："+logo_prefix+"/"+companyInfo.getLogo());
				companyInfo.setCompanyLogData(ImageUtil.getImageStr(logo_prefix+"/"+companyInfo.getLogo()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if((companyInfo.getQrCode()!=null && !"".equals(companyInfo.getQrCode())) && (!"无".equals(companyInfo.getQrCode()))){
			String qrCode_prefix = JZMushroom.getInstance().get("qrCode.dir"); //二维码
			try {
				LOG.info("--------qrCode_prefix路径："+qrCode_prefix+"/"+companyInfo.getQrCode());
				companyInfo.setCompanyQrCodeData(ImageUtil.getImageStr(qrCode_prefix+"/"+companyInfo.getQrCode()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return getMap(companyInfo);
	}


	/**
	 * 新增公司
	 * @param companyInfo
	 * @param session
	 * @return
	 * @throws ApplicationException
	 */
	@ResponseBody
	@RequestMapping(value = "/addCompany.action")
	public Map<String, Object> addCompany(@RequestBody CompanyInfo companyInfo, HttpSession session) throws ApplicationException {
		Assert.notNull(companyInfo, "请求参数不能为空");
		Assert.hasLength(companyInfo.getCompanyName(), "公司名不能为空");
		Assert.hasLength(companyInfo.getCompanyType(), "公司类型不能为空");
		Map<String,Object> map = new HashMap<String,Object>();
		CompanyInfo dbCompanyInfo = companyInfoService.getOneBy(CompanyInfo.DBStrCompanyName, companyInfo.getCompanyName());
		if(dbCompanyInfo ==null){
		UserInfo usersInfo=(UserInfo)SessionManager.getSession(session);
		String dateMkdir = DateTimeUtil.dateToString(new Date(), "yyyyMM");
		if(companyInfo.getCompanyQrCodeData()!=null && !"".equals(companyInfo.getCompanyQrCodeData()) ){
			String qrCode_prefix = JZMushroom.getInstance().get("qrCode.dir"); //二维码
			LOG.info("qrCode路径="+qrCode_prefix);
			File file1 = new File(qrCode_prefix+"/"+dateMkdir);
			if(!file1.exists()){
				LOG.info("创建qrCode路径="+qrCode_prefix+"/"+dateMkdir);
				file1.mkdirs();
			}
			String qrCodeUrl = dateMkdir+"/"+"qrCode_"+DateTimeUtil.dateToString(new Date(), "yyyyMMddHHmmsss")+"."+companyInfo.getCompanyQrCodeType();
			ImageUtil.generateImage(companyInfo.getCompanyQrCodeData(), qrCode_prefix + "/"+ qrCodeUrl);//qrCodeUrl生成附件
			companyInfo.setQrCode(qrCodeUrl);
			//压缩
			String compress_qrCode=JZMushroom.getInstance().get("compressQrCode.dir"); //二维码压缩路径
			LOG.info("qrCode压缩路径="+compress_qrCode);
			File fileCompress = new File(compress_qrCode+"/"+dateMkdir);
			if(!fileCompress.exists()){
				fileCompress.mkdirs();
			}
			String qrCodeName = dateMkdir+"/"+"compress_qrCode_"+DateTimeUtil.dateToString(new Date(), "yyyyMMddHHmmsss")+"."+companyInfo.getCompanyQrCodeType();
			try {
				ImageCompressUtil.saveMinPhoto(qrCode_prefix + "/"+ qrCodeUrl, compress_qrCode+"/"+qrCodeName, 200, 1d);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//ImageCompressUtil.zipImageFile(qrCode_prefix + "/"+ qrCodeUrl, compress_qrCode+"/"+dateMkdir, 200, 200, 1f, "compress_qrCode_"+DateTimeUtil.dateToString(new Date(), "yyyyMMddHHmmsss")+"."+companyInfo.getCompanyQrCodeType());
			companyInfo.setCompressQrCode(qrCodeName);
		}
		if(companyInfo.getCompanyLogData()!=null &&  !"".equals(companyInfo.getCompanyLogData())){
			String logo_prefix = JZMushroom.getInstance().get("logo.dir"); //logo			
			File file = new File(logo_prefix+"/"+dateMkdir);			
			if (!file.exists() ) {
				LOG.info("创建Log路径="+logo_prefix+"/"+dateMkdir);
				file.mkdirs();			
			}
			String logoUrl = dateMkdir+"/"+"logo_"+DateTimeUtil.dateToString(new Date(), "yyyyMMddHHmmsss")+"."+companyInfo.getCompanyLogType();
			ImageUtil.generateImage(companyInfo.getCompanyLogData(), logo_prefix + "/"+ logoUrl);//logo生成附件
			companyInfo.setLogo(logoUrl);
			//logo压缩
			String compress_logo=JZMushroom.getInstance().get("compressLogo.dir"); //logo压缩目录
			LOG.info("LOgo压缩路径="+compress_logo);
			File fileLogo = new File(compress_logo+"/"+dateMkdir);			
			if (!fileLogo.exists() ) {
				fileLogo.mkdirs();			
			}
			String logoName = dateMkdir+"/"+"compress_logo_"+DateTimeUtil.dateToString(new Date(), "yyyyMMddHHmmsss")+"."+companyInfo.getCompanyLogType();
			try {
				ImageCompressUtil.saveMinPhoto(logo_prefix + "/"+ logoUrl, compress_logo+"/"+logoName, 200, 1d);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//ImageCompressUtil.zipImageFile(logo_prefix + "/"+ logoUrl, compress_logo+"/"+dateMkdir, 200, 200, 1f, "compress_logo_"+DateTimeUtil.dateToString(new Date(), "yyyyMMddHHmmsss")+"."+companyInfo.getCompanyLogType());
			companyInfo.setCompressLogo(logoName);
		}
		companyInfo.setCreateBy(usersInfo.getUserName());
		companyInfo.setCreateTime(new Date());
		int value =companyInfoService.insert(companyInfo);
		if(value == Constant.YES){
			LOG.info("--------------------新增公司成功");
		}						
		map.put("companyInfo", companyInfo);
		}else{
			throw new ApplicationException(SystemCode.SYSTEM_EXCEPTION, "公司名已存在[]");
		}
		return getMap(map);
	}	
	
	/**
	 * 生成附件
	 * @param imgStr (base64去前缀)
	 * @param prefixPath 文件目录
	 * @param fileName 文件名
	 * @return  String
	 * @throws ApplicationException
	 */
	public static String generateAttach(String imgStr, String prefixPath, String fileName) throws ApplicationException{
		File file = new File(prefixPath);
		if(!file.exists()){
			file.mkdirs();
		}
		ImageUtil.generateImage(imgStr, prefixPath + "/"+ fileName);//logo生成附件
		return fileName;
	}

}
