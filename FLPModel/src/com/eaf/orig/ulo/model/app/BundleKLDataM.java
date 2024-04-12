package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class BundleKLDataM implements Serializable,Cloneable{
	public BundleKLDataM(){
		super();
	}
	public void init(String referId){
		this.applicationRecordId = referId;
	}
	private String applicationRecordId;	//ORIG_BUNDLE_KL.APPLICATION_RECORD_ID(VARCHAR2)
//	private BigDecimal approvalLimit;	//ORIG_BUNDLE_KL.APPROVAL_LIMIT(NUMBER)
	private BigDecimal verifiedIncome;	//ORIG_BUNDLE_KL.VERFIED_INCOME(NUMBER)
	private Date verifiedDate;	//ORIG_BUNDLE_KL.VERFIED_INCOME(DATE)
	private BigDecimal estimated_income;//ORIG_BUNDLE_KL.ESTIMATED_INCOME
	private String createBy;	//ORIG_BUNDLE_KL.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_BUNDLE_KL.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_BUNDLE_KL.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_BUNDLE_KL.UPDATE_DATE(DATE)
	private String compareFlag; //ORIG_BUNDLE_KL.COMPARE_FLAG(VARCHAR2)	
	
	public String getApplicationRecordId() {
		return applicationRecordId;
	}
	public void setApplicationRecordId(String applicationRecordId) {
		this.applicationRecordId = applicationRecordId;
	}
//	public BigDecimal getApprovalLimit() {
//		return approvalLimit;
//	}
//	public void setApprovalLimit(BigDecimal approvalLimit) {
//		this.approvalLimit = approvalLimit;
//	}
	public BigDecimal getVerifiedIncome() {
		return verifiedIncome;
	}
	public void setVerifiedIncome(BigDecimal verifiedIncome) {
		this.verifiedIncome = verifiedIncome;
	}
	public Date getVerifiedDate() {
		return verifiedDate;
	}
	public void setVerifiedDate(Date verifiedDate) {
		this.verifiedDate = verifiedDate;
	}
	public BigDecimal getEstimated_income() {
		return estimated_income;
	}
	public void setEstimated_income(BigDecimal estimated_income) {
		this.estimated_income = estimated_income;
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
	public String getCompareFlag() {
		return compareFlag;
	}
	public void setCompareFlag(String compareFlag) {
		this.compareFlag = compareFlag;
	}	
}
