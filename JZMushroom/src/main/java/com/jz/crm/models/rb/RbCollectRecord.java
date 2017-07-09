package com.jz.crm.models.rb;
import java.io.Serializable;

import com.jz.crm.models.comm.PageComm;

public class RbCollectRecord implements Serializable, Cloneable {
	private Long id;
	private Long userId;
	private Long companyId;
	private String remark;
	private String createBy;
	private java.util.Date createTime;
	private String updateBy;
	private java.util.Date updateTime;
	
	public String companyType;
	public String companyName;
	public PageComm pageComm;

	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getCompanyId() {
		return companyId;
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
	public PageComm getPageComm() {
		return pageComm;
	}
	public void setPageComm(PageComm pageComm) {
		this.pageComm = pageComm;
	}
	public String getCompanyType() {
		return companyType;
	}
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	private static final String Table = "rb_collect_record";
	private static final String Alias = "as_rb_collect_record";
	private static final long serialVersionUID = 16454654984465L;
 
	public static final String DBStrId = Alias+".`id`";
	public static final String DBStrUserId = Alias+".`user_id`";
	public static final String DBStrCompanyId = Alias+".`company_id`";
	public static final String DBStrRemark = Alias+".`remark`";
	public static final String DBStrCreateBy = Alias+".`create_by`";
	public static final String DBStrCreateTime = Alias+".`create_time`";
	public static final String DBStrUpdateBy = Alias+".`update_by`";
	public static final String DBStrUpdateTime = Alias+".`update_time`";
	
	@Override
	public RbCollectRecord clone() {
		RbCollectRecord o = null;
		try{
			o = (RbCollectRecord)super.clone();
		}catch(CloneNotSupportedException ex){
			ex.printStackTrace();
		}
		return o;
	}
}

