package com.jz.crm.models.rb;
import java.io.Serializable;

import com.jz.crm.base.manager.LoginUser;

public class RbuserInfo extends LoginUser  implements Serializable, Cloneable {
	/**
	 * 0:没有注册
	 */
	public  static  final int HASREG_NO=0;
	/**
	 * 1:已注册
	 */
	public  static final int HASREG_YES=1;
	/**
	 * 1:已经领取红宝书
	 */
	public static final int RECEIVEREDBOOK_YES=1;
	/**
	 * 0:未领取红宝书
	 */
	public static final int RECEIVEREDBOOK_NO=0;
	/**
	 * 0 帮帮用户过来
	 */
	public static final int SOURCE_BB=0;
	/**
	 * 1  其他地方 过来
	 */
	public static final int SOURCE_OTHER=1;
	
	private Long id;
	private String openId;
	private String userName;
	private String password;
	private String email;
	private String mobile;
	private String mobileAdress;
	private String presentCompany;
	private String fuctionDep;
	private String position;
	private String headimgurl;
	private Integer hasReg;
	private Integer receiveRedBook;
	private Integer source;
	private String createBy;
	private java.util.Date createTime;
	private String updateBy;
	private java.util.Date updateTime;
	
	
	private String verificationCode;
	private long fromUserId;

	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobileAdress(String mobileAdress) {
		this.mobileAdress = mobileAdress;
	}
	public String getMobileAdress() {
		return mobileAdress;
	}
	public void setPresentCompany(String presentCompany) {
		this.presentCompany = presentCompany;
	}
	public String getPresentCompany() {
		return presentCompany;
	}
	public void setFuctionDep(String fuctionDep) {
		this.fuctionDep = fuctionDep;
	}
	public String getFuctionDep() {
		return fuctionDep;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getPosition() {
		return position;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHasReg(Integer hasReg) {
		this.hasReg = hasReg;
	}
	public Integer getHasReg() {
		return hasReg;
	}
	public void setReceiveRedBook(Integer receiveRedBook) {
		this.receiveRedBook = receiveRedBook;
	}
	public Integer getReceiveRedBook() {
		return receiveRedBook;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public Integer getSource() {
		return source;
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
	
	
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	public long getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(long fromUserId) {
		this.fromUserId = fromUserId;
	}

	private static final String Table = "rb_user_info";
	private static final String Alias = "as_rb_user_info";
	private static final long serialVersionUID = 16454654984465L;
 
	public static final String DBStrId = Alias+".`id`";
	public static final String DBStrOpenId = Alias+".`open_id`";
	public static final String DBStrUserName = Alias+".`user_name`";
	public static final String DBStrPassword = Alias+".`password`";
	public static final String DBStrEmail = Alias+".`email`";
	public static final String DBStrMobile = Alias+".`mobile`";
	public static final String DBStrMobileAdress = Alias+".`mobile_adress`";
	public static final String DBStrPresentCompany = Alias+".`present_company`";
	public static final String DBStrFuctionDep = Alias+".`fuction_dep`";
	public static final String DBStrPosition = Alias+".`position`";
	public static final String DBStrHeadimgurl = Alias+".`headimgurl`";
	public static final String DBStrHasReg = Alias+".`has_reg`";
	public static final String DBStrReceiveRedBook = Alias+".`receive_red_book`";
	public static final String DBStrSource = Alias+".`source`";
	public static final String DBStrCreateBy = Alias+".`create_by`";
	public static final String DBStrCreateTime = Alias+".`create_time`";
	public static final String DBStrUpdateBy = Alias+".`update_by`";
	public static final String DBStrUpdateTime = Alias+".`update_time`";
	
	@Override
	public RbuserInfo clone() {
		RbuserInfo o = null;
		try{
			o = (RbuserInfo)super.clone();
		}catch(CloneNotSupportedException ex){
			ex.printStackTrace();
		}
		return o;
	}
}


