package com.jz.crm.models.rb;
import java.io.Serializable;

public class RbShareRecord implements Serializable, Cloneable {
	public static final int STATUS_NO = 0;//无效
	public static final int STATUS_YES = 1;//有效
	
	private Long id;
	private Long userId;
	private Long shareUserId;
	private Integer status;
	private String createBy;
	private java.util.Date createTime;
	private String updateBy;
	private java.util.Date updateTime;
	
	private String userName;
	private String headimgurl;
	private Integer receiveRedBook;

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
	public void setShareUserId(Long shareUserId) {
		this.shareUserId = shareUserId;
	}
	public Long getShareUserId() {
		return shareUserId;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getStatus() {
		return status;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public Integer getReceiveRedBook() {
		return receiveRedBook;
	}
	public void setReceiveRedBook(Integer receiveRedBook) {
		this.receiveRedBook = receiveRedBook;
	}

	private static final String Table = "rb_share_record";
	private static final String Alias = "as_rb_share_record";
	private static final long serialVersionUID = 16454654984465L;
 
	public static final String DBStrId = Alias+".`id`";
	public static final String DBStrUserId = Alias+".`user_id`";
	public static final String DBStrShareUserId = Alias+".`share_user_id`";
	public static final String DBStrStatus = Alias+".`status`";
	public static final String DBStrCreateBy = Alias+".`create_by`";
	public static final String DBStrCreateTime = Alias+".`create_time`";
	public static final String DBStrUpdateBy = Alias+".`update_by`";
	public static final String DBStrUpdateTime = Alias+".`update_time`";
	
	@Override
	public RbShareRecord clone() {
		RbShareRecord o = null;
		try{
			o = (RbShareRecord)super.clone();
		}catch(CloneNotSupportedException ex){
			ex.printStackTrace();
		}
		return o;
	}
}

