package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Comparator;

public class PhoneVerificationDataM implements Serializable,Cloneable,Comparator<Object>{
	public PhoneVerificationDataM(){
		super();
	}
	private String verResultId;	//XRULES_PHONE_VERIFICATION.VER_RESULT_ID(VARCHAR2)	
	private String phoneVerifyId;	//XRULES_PHONE_VERIFICATION.PHONE_VERIFY_ID(VARCHAR2)
	private String phoneVerStatus;	//XRULES_PHONE_VERIFICATION.PHONE_VER_STATUS(VARCHAR2)
	private String telephoneNumber;	//XRULES_PHONE_VERIFICATION.TELEPHONE_NUMBER(VARCHAR2)
	private int seq;	//XRULES_PHONE_VERIFICATION.SEQ(NUMBER)
	private String callType;	//XRULES_PHONE_VERIFICATION.CALL_TYPE(VARCHAR2)
	private String remark;	//XRULES_PHONE_VERIFICATION.REMARK(VARCHAR2)
	private String contactType;	//XRULES_PHONE_VERIFICATION.CONTACT_TYPE(VARCHAR2)
	private String createBy;	//XRULES_PHONE_VERIFICATION.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//XRULES_PHONE_VERIFICATION.CREATE_DATE(DATE)
	private String updateBy;	//XRULES_PHONE_VERIFICATION.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//XRULES_PHONE_VERIFICATION.UPDATE_DATE(DATE)
	private int lifeCycle;	//XRULES_PHONE_VERIFICATION.LIFE_CYCLE(VARCHAR2)
		
	public String getVerResultId() {
		return verResultId;
	}
	public void setVerResultId(String verResultId) {
		this.verResultId = verResultId;
	}
	public String getPhoneVerifyId() {
		return phoneVerifyId;
	}
	public void setPhoneVerifyId(String phoneVerifyId) {
		this.phoneVerifyId = phoneVerifyId;
	}
	public String getPhoneVerStatus() {
		return phoneVerStatus;
	}
	public void setPhoneVerStatus(String phoneVerStatus) {
		this.phoneVerStatus = phoneVerStatus;
	}
	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getCallType() {
		return callType;
	}
	public void setCallType(String callType) {
		this.callType = callType;
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
	@Override
	public int compare(Object o1, Object o2) {
		if(o1 instanceof PhoneVerificationDataM && o2 instanceof PhoneVerificationDataM){
			PhoneVerificationDataM phoneVerification1 = (PhoneVerificationDataM)o1;
			PhoneVerificationDataM phoneVerification2 = (PhoneVerificationDataM)o2;
			try{
				int sqeDate = phoneVerification1.getCreateDate().compareTo(phoneVerification2.getCreateDate());
				return sqeDate;
			}catch(Exception e){
				return 0;
			}
		}
		return 0;
	}
	public int getLifeCycle() {
		return lifeCycle;
	}
	public void setLifeCycle(int lifeCycle) {
		this.lifeCycle = lifeCycle;
	}
}
