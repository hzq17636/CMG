package com.jz.crm.models.mg;
import java.io.Serializable;

import com.jz.crm.base.manager.LoginUser;

public class UserInfo  extends LoginUser implements Serializable, Cloneable {
	private Long id;
	private String userName;
	private String passwd;
	private String remark;
	private String createBy;
	private java.util.Date createTime;
	private String updateBy;
	private java.util.Date updateTime;
	
	private String verificationCode;

	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getPasswd() {
		return passwd;
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
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	private static final String Table = "mg_user_info";
	private static final String Alias = "as_mg_user_info";
	private static final long serialVersionUID = 16454654984465L;
 
	public static final String DBStrId = Alias+".`id`";
	public static final String DBStrUserName = Alias+".`user_name`";
	public static final String DBStrPasswd = Alias+".`passwd`";
	public static final String DBStrRemark = Alias+".`remark`";
	public static final String DBStrCreateBy = Alias+".`create_by`";
	public static final String DBStrCreateTime = Alias+".`create_time`";
	public static final String DBStrUpdateBy = Alias+".`update_by`";
	public static final String DBStrUpdateTime = Alias+".`update_time`";
	
	@Override
	public UserInfo clone() {
		UserInfo o = null;
		try{
			o = (UserInfo)super.clone();
		}catch(CloneNotSupportedException ex){
			ex.printStackTrace();
		}
		return o;
	}
}

