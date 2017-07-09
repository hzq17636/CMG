package com.jz.crm.models.mg;
import java.io.Serializable;

import com.jz.crm.models.comm.PageDto;

public class CompanyInfo extends PageDto implements Serializable, Cloneable {
	/**
	 *   1：已经收藏
	 */
	public  static final int ISCOLLECT_YES = 1;
	/**
	 *0 ：没有收藏
	 */
	public  static final int ISCOLLECT_NO=0;
	
	private Long id;
	private String companyKeyNo;
	private String companyName;
	private String companyShortName;
	private String companyType;
	private String companyAdress;
	private String phone;
	private String webSite;
	private String introduction;
	private String logo;
	private String compressLogo;
	private String qrCode;
	private String compressQrCode;
	private String remark;
	private String createBy;
	private java.util.Date createTime;
	private String updateBy;
	private java.util.Date updateTime;

	//自定义
	private String companyLogData;
	private String companyLogType;
	private String companyQrCodeData;
	private String companyQrCodeType;
	
	private int isCollect;//自定义 显示是否收藏

	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setCompanyKeyNo(String companyKeyNo) {
		this.companyKeyNo = companyKeyNo;
	}
	public String getCompanyKeyNo() {
		return companyKeyNo;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyShortName(String companyShortName) {
		this.companyShortName = companyShortName;
	}
	public String getCompanyShortName() {
		return companyShortName;
	}
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
	public String getCompanyType() {
		return companyType;
	}
	public void setCompanyAdress(String companyAdress) {
		this.companyAdress = companyAdress;
	}
	public String getCompanyAdress() {
		return companyAdress;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPhone() {
		return phone;
	}
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}
	public String getWebSite() {
		return webSite;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getLogo() {
		return logo;
	}
	public void setCompressLogo(String compressLogo) {
		this.compressLogo = compressLogo;
	}
	public String getCompressLogo() {
		return compressLogo;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	public String getQrCode() {
		return qrCode;
	}
	public void setCompressQrCode(String compressQrCode) {
		this.compressQrCode = compressQrCode;
	}
	public String getCompressQrCode() {
		return compressQrCode;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRemark() {
		return remark;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	public java.util.Date getUpdateTime() {
		return updateTime;
	}
	
	public String getCompanyLogData() {
		return companyLogData;
	}
	public void setCompanyLogData(String companyLogData) {
		this.companyLogData = companyLogData;
	}
	public String getCompanyLogType() {
		return companyLogType;
	}
	public void setCompanyLogType(String companyLogType) {
		this.companyLogType = companyLogType;
	}
	public String getCompanyQrCodeData() {
		return companyQrCodeData;
	}
	public void setCompanyQrCodeData(String companyQrCodeData) {
		this.companyQrCodeData = companyQrCodeData;
	}
	public String getCompanyQrCodeType() {
		return companyQrCodeType;
	}
	public void setCompanyQrCodeType(String companyQrCodeType) {
		this.companyQrCodeType = companyQrCodeType;
	}
	public int getIsCollect() {
		return isCollect;
	}
	public void setIsCollect(int isCollect) {
		this.isCollect = isCollect;
	}


	private static final String Table = "mg_company_info";
	private static final String Alias = "as_mg_company_info";
	private static final long serialVersionUID = 16454654984465L;
 
	public static final String DBStrId = Alias+".`id`";
	public static final String DBStrCompanyKeyNo = Alias+".`company_key_no`";
	public static final String DBStrCompanyName = Alias+".`company_name`";
	public static final String DBStrCompanyShortName = Alias+".`company_short_name`";
	public static final String DBStrCompanyType = Alias+".`company_type`";
	public static final String DBStrCompanyAdress = Alias+".`company_adress`";
	public static final String DBStrPhone = Alias+".`phone`";
	public static final String DBStrWebSite = Alias+".`web_site`";
	public static final String DBStrIntroduction = Alias+".`introduction`";
	public static final String DBStrLogo = Alias+".`logo`";
	public static final String DBStrCompressLogo = Alias+".`compress_logo`";
	public static final String DBStrQrCode = Alias+".`qr_code`";
	public static final String DBStrCompressQrCode = Alias+".`compress_qr_code`";
	public static final String DBStrRemark = Alias+".`remark`";
	public static final String DBStrCreateBy = Alias+".`create_by`";
	public static final String DBStrCreateTime = Alias+".`create_time`";
	public static final String DBStrUpdateBy = Alias+".`update_by`";
	public static final String DBStrUpdateTime = Alias+".`update_time`";
	
	@Override
	public CompanyInfo clone() {
		CompanyInfo o = null;
		try{
			o = (CompanyInfo)super.clone();
		}catch(CloneNotSupportedException ex){
			ex.printStackTrace();
		}
		return o;
	}
}



