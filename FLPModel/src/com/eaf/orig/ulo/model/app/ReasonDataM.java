package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class ReasonDataM implements Serializable,Cloneable{
	public ReasonDataM(){
		super();
	}
	public void init(String referId){
		this.applicationRecordId = referId;
	}
	private String applicationRecordId;	//ORIG_REASON.APPLICATION_RECORD_ID(VARCHAR2)
	private String remark;	//ORIG_REASON.REMARK(VARCHAR2)
	private String reasonType;	//ORIG_REASON.REASON_TYPE(VARCHAR2)
	private String role;	//ORIG_REASON.ROLE(VARCHAR2)
	private String reasonCode;	//ORIG_REASON.REASON_CODE(VARCHAR2)
	private String createBy;	//ORIG_REASON.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_REASON.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_REASON.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_REASON.UPDATE_DATE(DATE)
	private String reasonOthDesc; //ORIG_REASON.REASON_OTH_DESC(VARCHAR2)	
	private String applicationGroupId; //ORIG_REASON.APPLICATION_GROUP_ID(VARCHAR2)	
	private String reasonId; //ORIG_REASON.REASON_ID(VARCHAR2)
	
	public String getReasonId() {
		return reasonId;
	}
	public void setReasonId(String reasonId) {
		this.reasonId = reasonId;
	}
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getReasonType() {
		return reasonType;
	}
	public void setReasonType(String reasonType) {
		this.reasonType = reasonType;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
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
	public String getReasonOthDesc() {
		return reasonOthDesc;
	}
	public void setReasonOthDesc(String reasonOthDesc) {
		this.reasonOthDesc = reasonOthDesc;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	
}
