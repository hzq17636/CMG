package com.jz.crm.models.rb.dto;

/**
 * 我的收藏Dto
 * @author Administrator
 *
 */
public class CollectCompany {
	/**
	 * 1：已经收藏
	 */
	public  static final int ISCOLLECT_YES = 1;
	/**
	 *0 ：没有收藏
	 */
	public  static final int ISCOLLECT_NO=0;
	
	private Long id;
	private Long userId;
	private Long companyId;
	
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
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyShortName() {
		return companyShortName;
	}
	public void setCompanyShortName(String companyShortName) {
		this.companyShortName = companyShortName;
	}
	public String getCompanyType() {
		return companyType;
	}
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getWebSite() {
		return webSite;
	}
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	public String getCompressLogo() {
		return compressLogo;
	}
	public void setCompressLogo(String compressLogo) {
		this.compressLogo = compressLogo;
	}
	public String getCompressQrCode() {
		return compressQrCode;
	}
	public void setCompressQrCode(String compressQrCode) {
		this.compressQrCode = compressQrCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public java.util.Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
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
	public String getCompanyAdress() {
		return companyAdress;
	}
	public void setCompanyAdress(String companyAdress) {
		this.companyAdress = companyAdress;
	}

}
