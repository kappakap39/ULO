package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;

public class HRVerificationDataM implements Serializable,Cloneable {
	public HRVerificationDataM(){
		super();
	}
	private String hrVerifyId;	//XRULES_HR_VERIFICATION.HR_VERIFY_ID(VARCHAR2)
	private String verResultId;	//XRULES_HR_VERIFICATION.VER_RESULT_ID(VARCHAR2)
	private String phoneVerStatus;	//XRULES_HR_VERIFICATION.PHONE_VER_STATUS(VARCHAR2)
	private String remark;	//XRULES_HR_VERIFICATION.REMARK(VARCHAR2)
	private String contactType;	//XRULES_HR_VERIFICATION.CONTACT_TYPE(VARCHAR2)
	private String phoneNo;	//XRULES_HR_VERIFICATION.PHONE_NO(VARCHAR2)
	private String createBy;	//XRULES_HR_VERIFICATION.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//XRULES_HR_VERIFICATION.CREATE_DATE(DATE)
	private String updateBy;	//XRULES_HR_VERIFICATION.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//XRULES_HR_VERIFICATION.UPDATE_DATE(DATE)
	
	public String getHrVerifyId() {
		return hrVerifyId;
	}
	public void setHrVerifyId(String hrVerifyId) {
		this.hrVerifyId = hrVerifyId;
	}	
	public String getVerResultId() {
		return verResultId;
	}
	public void setVerResultId(String verResultId) {
		this.verResultId = verResultId;
	}
	public String getPhoneVerStatus() {
		return phoneVerStatus;
	}
	public void setPhoneVerStatus(String phoneVerStatus) {
		this.phoneVerStatus = phoneVerStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getContactType() {
		return contactType;
	}
	public void setContactType(String contactType) {
		this.contactType = contactType;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	
}
