package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;

public class ReasonLogDataM implements Serializable,Cloneable{
	public ReasonLogDataM(){
		super();
	}
	public void init(String referId,String uniqueId){
		this.applicationRecordId = referId;
		this.reasonLogId=uniqueId;
	}
	private String applicationRecordId;	//ORIG_REASON_LOG.APPLICATION_RECORD_ID(VARCHAR2)
	private String reasonLogId;	//ORIG_REASON_LOG.REASON_LOG_ID(VARCHAR2)
	private String reasonType;	//ORIG_REASON_LOG.REASON_TYPE(VARCHAR2)
	private String reasonCode;	//ORIG_REASON_LOG.REASON_CODE(VARCHAR2)
	private String role;	//ORIG_REASON_LOG.ROLE(VARCHAR2)
	private String remark;	//ORIG_REASON_LOG.REMARK(VARCHAR2)
	private String createBy;	//ORIG_REASON_LOG.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_REASON_LOG.CREATE_DATE(DATE)
	private String reasonOthDesc; //ORIG_REASON.REASON_OTH_DESC(VARCHAR2)	
	private String applicationGroupId; //ORIG_REASON.APPLICATION_GROUP_ID(VARCHAR2)
	
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getApplicationRecordId() {
		return applicationRecordId;
	}
	public void setApplicationRecordId(String applicationRecordId) {
		this.applicationRecordId = applicationRecordId;
	}
	public String getReasonLogId() {
		return reasonLogId;
	}
	public void setReasonLogId(String reasonLogId) {
		this.reasonLogId = reasonLogId;
	}
	public String getReasonType() {
		return reasonType;
	}
	public void setReasonType(String reasonType) {
		this.reasonType = reasonType;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getReasonOthDesc() {
		return reasonOthDesc;
	}
	public void setReasonOthDesc(String reasonOthDesc) {
		this.reasonOthDesc = reasonOthDesc;
	}
	
}
