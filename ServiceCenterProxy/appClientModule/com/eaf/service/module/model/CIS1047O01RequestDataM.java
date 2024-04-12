package com.eaf.service.module.model;

import java.io.Serializable;

public class CIS1047O01RequestDataM  implements Serializable,Cloneable {
 private String userId;
 private String hubNo;
 private String branchNo;
 private String dataTypeCode;
 private String customerId;
 private String contactAddressNum;
 private String contactTypeCode;
 private String contactFunctionTypeCode;
 
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getHubNo() {
		return hubNo;
	}
	public void setHubNo(String hubNo) {
		this.hubNo = hubNo;
	}
	public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	public String getDataTypeCode() {
		return dataTypeCode;
	}
	public void setDataTypeCode(String dataTypeCode) {
		this.dataTypeCode = dataTypeCode;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getContactAddressNum() {
		return contactAddressNum;
	}
	public void setContactAddressNum(String contactAddressNum) {
		this.contactAddressNum = contactAddressNum;
	}
	public String getContactTypeCode() {
		return contactTypeCode;
	}
	public void setContactTypeCode(String contactTypeCode) {
		this.contactTypeCode = contactTypeCode;
	}
	public String getContactFunctionTypeCode() {
		return contactFunctionTypeCode;
	}
	public void setContactFunctionTypeCode(String contactFunctionTypeCode) {
		this.contactFunctionTypeCode = contactFunctionTypeCode;
	}
 
}
