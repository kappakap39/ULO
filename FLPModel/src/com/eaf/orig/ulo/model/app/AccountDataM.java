package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class AccountDataM implements Serializable,Cloneable{
	public AccountDataM(){
		super();
	}
	private String personalAccountId;	//ORIG_PERSONAL_ACCOUNT.PERSONAL_ACCOUNT_ID(VARCHAR2)
	private String personalId;	//ORIG_PERSONAL_ACCOUNT.PERSONAL_ID(VARCHAR2)
	private String accountType;	//ORIG_PERSONAL_ACCOUNT.ACCOUNT_TYPE(VARCHAR2)
	private String accountNo;	//ORIG_PERSONAL_ACCOUNT.ACCOUNT_NO(DATE)
	private String accountName;	//ORIG_PERSONAL_ACCOUNT.ACCOUNT_NAME(VARCHAR2)
	private Date  openDate; //ORIG_PERSONAL_ACCOUNT.OPEN_DATE(DATE)
	private String createBy;  //ORIG_PERSONAL_ACCOUNT.CREATE_BY(VARCHAR2)
	private String updateBy;  //ORIG_PERSONAL_ACCOUNT.UPDATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_PERSONAL_ACCOUNT.CREATE_DATE(DATE)
	private Timestamp updateDate;	//ORIG_PERSONAL_ACCOUNT.UPDATE_DATE(DATE)
	
	
	
	public String getPersonalAccountId() {
		return personalAccountId;
	}
	public void setPersonalAccountId(String personalAccountId) {
		this.personalAccountId = personalAccountId;
	}
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	@Override
	public String toString() {
		return "AccountDataM [personalAccountId=" + personalAccountId + ", personalId="
				+ personalId + ", accountType=" +accountType + ", accountNo=" + accountNo
				+ ", accountName=" + accountName + ", openDate=" + openDate
				+ "]";
	}
	
}
