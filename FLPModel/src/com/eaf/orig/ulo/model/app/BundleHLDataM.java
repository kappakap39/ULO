package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class BundleHLDataM implements Serializable,Cloneable{
	public BundleHLDataM(){
		super();
	}
	public void init(String referId){
		this.applicationRecordId = referId;
	}
	private String applicationRecordId;	//ORIG_BUNDLE_HL.APPLICATION_RECORD_ID(VARCHAR2)
	private BigDecimal approveCreditLine;	//ORIG_BUNDLE_HL.APPROVE_CREDIT_LINE(NUMBER)
	private BigDecimal verifiedIncome;	//ORIG_BUNDLE_HL.VERIFIED_INCOME(NUMBER)
	private String createBy;	//ORIG_BUNDLE_HL.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_BUNDLE_HL.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_BUNDLE_HL.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_BUNDLE_HL.UPDATE_DATE(DATE)
	private Date approvedDate; //ORIG_BUNDLE_HL.APPROVED_DATE(DATE)
	private BigDecimal ccApprovedAmt; //ORIG_BUNDLE_HL.CC_APPROVED_AMT(NUMBER)
	private BigDecimal kecApprovedAmt; //ORIG_BUNDLE_HL.KEC_APPROVED_AMT(NUMBER)
	private String compareFlag; //ORIG_BUNDLE_HL.COMPARE_FLAG(VARCHAR2)	
	
	private Date mortGageDate; //ORIG_BUNDLE_HL.MORTGAGE_DATE(DATE)
	private Date accountOpenDate; //ORIG_BUNDLE_HL.OPEN_DATE(DATE)
	private String accountNo; //ORIG_BUNDLE_HL.ACCOUNT_NO(VARCHAR2)
	private String accountName; //ORIG_BUNDLE_HL.ACCOUNT_NAME(VARCHAR2)
	

	
	public Date getMortGageDate() {
		return mortGageDate;
	}
	public void setMortGageDate(Date mortGageDate) {
		this.mortGageDate = mortGageDate;
	}
	public Date getAccountOpenDate() {
		return accountOpenDate;
	}
	public void setAccountOpenDate(Date accountOpenDate) {
		this.accountOpenDate = accountOpenDate;
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
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	public BigDecimal getCcApprovedAmt() {
		return ccApprovedAmt;
	}
	public void setCcApprovedAmt(BigDecimal ccApprovedAmt) {
		this.ccApprovedAmt = ccApprovedAmt;
	}
	public BigDecimal getKecApprovedAmt() {
		return kecApprovedAmt;
	}
	public void setKecApprovedAmt(BigDecimal kecApprovedAmt) {
		this.kecApprovedAmt = kecApprovedAmt;
	}
	public String getApplicationRecordId() {
		return applicationRecordId;
	}
	public void setApplicationRecordId(String applicationRecordId) {
		this.applicationRecordId = applicationRecordId;
	}
	public BigDecimal getApproveCreditLine() {
		return approveCreditLine;
	}
	public void setApproveCreditLine(BigDecimal approveCreditLine) {
		this.approveCreditLine = approveCreditLine;
	}
	public BigDecimal getVerifiedIncome() {
		return verifiedIncome;
	}
	public void setVerifiedIncome(BigDecimal verifiedIncome) {
		this.verifiedIncome = verifiedIncome;
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
