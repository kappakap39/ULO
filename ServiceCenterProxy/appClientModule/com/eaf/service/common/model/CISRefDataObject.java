package com.eaf.service.common.model;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

@SuppressWarnings("serial")
public class CISRefDataObject implements Serializable, Cloneable{
	
	public class RefType{
		public static final String PERSONAL = "P";
		public static final String ADDRESS = "AD";
	}
	
	private String serviceId;
	private String respCode;
	@Expose
	private String cisNo;
	@Expose
	private String personalId;
	@Expose
	private String cisAddressId;	
	private String refType;
	private boolean success = false;
	
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getCisNo() {
		return cisNo;
	}
	public void setCisNo(String cisNo) {
		this.cisNo = cisNo;
	}
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public String getCisAddressId() {
		return cisAddressId;
	}
	public void setCisAddressId(String cisAddressId) {
		this.cisAddressId = cisAddressId;
	}	
	public String getRefType() {
		return refType;
	}
	public void setRefType(String refType) {
		this.refType = refType;
	}	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CISRefDataObject [");
		if (serviceId != null) {
			builder.append("serviceId=");
			builder.append(serviceId);
			builder.append(", ");
		}
		if (respCode != null) {
			builder.append("respCode=");
			builder.append(respCode);
			builder.append(", ");
		}
		if (cisNo != null) {
			builder.append("cisNo=");
			builder.append(cisNo);
			builder.append(", ");
		}
		if (personalId != null) {
			builder.append("personalId=");
			builder.append(personalId);
			builder.append(", ");
		}
		if (cisAddressId != null) {
			builder.append("cisAddressId=");
			builder.append(cisAddressId);
			builder.append(", ");
		}
		if (refType != null) {
			builder.append("refType=");
			builder.append(refType);
			builder.append(", ");
		}
		builder.append("success=");
		builder.append(success);
		builder.append("]");
		return builder.toString();
	}	
}
