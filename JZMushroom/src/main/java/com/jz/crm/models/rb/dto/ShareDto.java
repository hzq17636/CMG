package com.jz.crm.models.rb.dto;
//分享实体
public class ShareDto {
	private Long userId;
	private String presentCompany;
	private String userName;
	private String mobile;
	private String position;
	private int bbInvitationNum;
	private int otherInvitationNum;
	private int regNum;
	private int hasReg;
	
	private int source;
	private java.util.Date createTime;
	private java.util.Date shareTime;
	
	private String startTime;
	private String endTime;
	private int showCount;//每页显示的条数
	private int totalPage;//总页数
	private int totalResult;//总条数
	private int currentPage;//当前页数
	
	public ShareDto(){
		this.showCount=10;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getPresentCompany() {
		return presentCompany;
	}
	public void setPresentCompany(String presentCompany) {
		this.presentCompany = presentCompany;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public int getRegNum() {
		return regNum;
	}
	public void setRegNum(int regNum) {
		this.regNum = regNum;
	}	
	public int getShowCount() {
		return showCount;
	}
	public void setShowCount(int showCount) {
		this.showCount = showCount;
	}
	public int getTotalPage() {
		if (this.totalResult % this.showCount == 0)
			this.totalPage = (this.totalResult / this.showCount);
		else
			this.totalPage = (this.totalResult / this.showCount + 1);
		return this.totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalResult() {
		return totalResult;
	}
	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
	}
	public int getCurrentPage() {
		if (this.currentPage <= 0)
			this.currentPage = 1;
		if (this.currentPage > getTotalPage())
			this.currentPage = getTotalPage();
		return this.currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getBbInvitationNum() {
		return bbInvitationNum;
	}
	public void setBbInvitationNum(int bbInvitationNum) {
		this.bbInvitationNum = bbInvitationNum;
	}
	public int getOtherInvitationNum() {
		return otherInvitationNum;
	}
	public void setOtherInvitationNum(int otherInvitationNum) {
		this.otherInvitationNum = otherInvitationNum;
	}
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public java.util.Date getShareTime() {
		return shareTime;
	}
	public void setShareTime(java.util.Date shareTime) {
		this.shareTime = shareTime;
	}
	public int getHasReg() {
		return hasReg;
	}
	public void setHasReg(int hasReg) {
		this.hasReg = hasReg;
	}
	
	
}
