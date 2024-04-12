package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class BundleSMEDataM implements Serializable,Cloneable{
	public BundleSMEDataM(){
		super();
	}
	public void init(String referId){
		this.applicationRecordId = referId;
	}
	private String applicationRecordId;	//ORIG_BUNDLE_SME.APPLICATION_RECORD_ID(VARCHAR2)
	private String applicantQuality;	//ORIG_BUNDLE_SME.APPLICANT_QUALITY(VARCHAR2)
	private BigDecimal approvalLimit;	//ORIG_BUNDLE_SME.APPROVAL_LIMIT(NUMBER)
	private String busOwnerFlag;	//ORIG_BUNDLE_SME.BUSINESS_OWNER_FLAG(VARCHAR2)
	private BigDecimal corporateRatio;	//ORIG_BUNDLE_SME.CORPORATE_RATIO(NUMBER)
	private BigDecimal gDscrReq;	//ORIG_BUNDLE_SME.G_DSCR_REQ(NUMBER)
	private BigDecimal gTotExistPayment;	//ORIG_BUNDLE_SME.G_TOTAL_EXIST_PAYMENT(NUMBER)
	private BigDecimal gTotNewPayReq;	//ORIG_BUNDLE_SME.G_TOTAL_NEWPAY_REQ(NUMBER)
	private BigDecimal individualRatio;	//ORIG_BUNDLE_SME.INDIVIDUAL_RATIO(NUMBER)
	private String borrowingType;	//ORIG_BUNDLE_SME.BORROWING_TYPE(String)
	private String businessCode;	//ORIG_BUNDLE_SME.BUSINESS_CODE(String)
	private Date approvalDate;	//ORIG_BUNDLE_SME.SME_APPROVE_DATE(DATE)
	private String createBy;	//ORIG_BUNDLE_SME.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_BUNDLE_SME.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_BUNDLE_SME.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_BUNDLE_SME.UPDATE_DATE(DATE)
	private String compareFlag; //ORIG_BUNDLE_SME.COMPARE_FLAG(VARCHAR2)
	private String typeLimit; //ORIG_BUNDLE_SME.ACCOUNT_TYPE(VARCHAR2)
	private String accountNo; //ORIG_BUNDLE_SME.ACCOUNT_NO(VARCHAR2)
	private String accountName; //ORIG_BUNDLE_SME.ACCOUNT_NAME(VARCHAR2)
	private Date accountDate; //ORIG_BUNDLE_SME.ACCOUNT_OPEN_DATE(DATE)
	private String accountStatus;
	private BigDecimal verifiedIncome;	//ORIG_BUNDLE_SME.VERIFIED_INCOME(NUMBER)
	
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getTypeLimit() {
		return typeLimit;
	}
	public void setTypeLimit(String typeLimit) {
		this.typeLimit = typeLimit;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String noLimit) {
		this.accountNo = noLimit;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public Date getAccountDate() {
		return accountDate;
	}
	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}
	
	public String getApplicationRecordId() {
		return applicationRecordId;
	}
	public void setApplicationRecordId(String applicationRecordId) {
		this.applicationRecordId = applicationRecordId;
	}
	public String getApplicantQuality() {
		return applicantQuality;
	}
	public void setApplicantQuality(String applicantQuality) {
		this.applicantQuality = applicantQuality;
	}
	public BigDecimal getApprovalLimit() {
		return approvalLimit;
	}
	public void setApprovalLimit(BigDecimal approvalLimit) {
		this.approvalLimit = approvalLimit;
	}
	public String getBusOwnerFlag() {
		return busOwnerFlag;
	}
	public void setBusOwnerFlag(String busOwnerFlag) {
		this.busOwnerFlag = busOwnerFlag;
	}
	public BigDecimal getCorporateRatio() {
		return corporateRatio;
	}
	public void setCorporateRatio(BigDecimal corporateRatio) {
		this.corporateRatio = corporateRatio;
	}	
	public BigDecimal getgDscrReq() {
		return gDscrReq;
	}
	public void setgDscrReq(BigDecimal gDscrReq) {
		this.gDscrReq = gDscrReq;
	}
	public BigDecimal getgTotExistPayment() {
		return gTotExistPayment;
	}
	public void setgTotExistPayment(BigDecimal gTotExistPayment) {
		this.gTotExistPayment = gTotExistPayment;
	}
	public BigDecimal getgTotNewPayReq() {
		return gTotNewPayReq;
	}
	public void setgTotNewPayReq(BigDecimal gTotNewPayReq) {
		this.gTotNewPayReq = gTotNewPayReq;
	}
	public BigDecimal getIndividualRatio() {
		return individualRatio;
	}
	public void setIndividualRatio(BigDecimal individualRatio) {
		this.individualRatio = individualRatio;
	}
	public String getBorrowingType() {
		return borrowingType;
	}
	public void setBorrowingType(String borrowingType) {
		this.borrowingType = borrowingType;
	}
	public String getBusinessCode() {
		return businessCode;
	}
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}
	public Date getApprovalDate() {
		return approvalDate;
	}
	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
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
	public BigDecimal getVerifiedIncome() {
		return verifiedIncome;
	}
	public void setVerifiedIncome(BigDecimal verifiedIncome) {
		this.verifiedIncome = verifiedIncome;
	}	
}
