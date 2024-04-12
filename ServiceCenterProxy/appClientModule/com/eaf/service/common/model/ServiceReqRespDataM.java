package com.eaf.service.common.model;

import java.io.Serializable;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class ServiceReqRespDataM implements Serializable,Cloneable{
	public ServiceReqRespDataM(){
		super();
	}
	private String reqRespId;
	private String serviceId;
	private String activityType;
	private String refCode;
	private String respCode;
	private String respDesc;
	private String errorMessage;
	private Timestamp createDate;
	private String createBy;
	private String contentMsg;
	private String appId;
	private String transId;
	private String serviceData;
	
	public String getReqRespId() {
		return reqRespId;
	}
	public void setReqRespId(String reqRespId) {
		this.reqRespId = reqRespId;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public String getRefCode() {
		return refCode;
	}
	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespDesc() {
		return respDesc;
	}
	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getContentMsg() {
		return contentMsg;
	}
	public void setContentMsg(String contentMsg) {
		this.contentMsg = contentMsg;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public String getServiceData() {
		return serviceData;
	}
	public void setServiceData(String serviceData) {
		this.serviceData = serviceData;
	}
}
