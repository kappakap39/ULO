package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogManager;

public class LoanSetupDataM implements Serializable,Cloneable {
	public static final String	STAMP_DUTY_TYPE = "S";
	public static final String	MAILING_ADDRESS_TYPE = "M";
	public static final String	PENDING_SETUP_STATUS = "Pending Setup";
	public static final String	CLAIM_STATUS = "Claimed";
	public static final String	NOT_CLAIM_STATUS = "Not Claim";
	public static final String	COMPLETE_STATUS = "Completed";
	public static final String	CANCEL_STATUS = "Cancelled";
	
	private String	claimId;			// LOAN_SETUP_CLAIM.CLAIM_ID
	private String	addressId;			// LOAN_SETUP_CLAIM.ADDRESS_ID
	private String	type;				// LOAN_SETUP_CLAIM.TYPE
	private String	applicationGroupNo;	// LOAN_SETUP_CLAIM.APPLICATION_GROUP_NO
	private String	cisNo;				// LOAN_SETUP_CLAIM.CIS_NO
	private String	thTitle;			// LOAN_SETUP_CLAIM.TH_TITLE_CODE
	private String	thName;				// LOAN_SETUP_CLAIM.TH_FIRST_NAME
	private String	thMidName;			// LOAN_SETUP_CLAIM.TH_MID_NAME
	private String	thSurname;			// LOAN_SETUP_CLAIM.TH_LAST_NAME
	private Timestamp	submitDate;		// LOAN_SETUP_CLAIM.DE2_SUBMIT_DATE
	private String	claimFlag;			// LOAN_SETUP_CLAIM.CLAIM_FLAG
	private String	claimBy;			// LOAN_SETUP_CLAIM.CLAIM_BY
	private Timestamp	claimDate;		// LOAN_SETUP_CLAIM.CLAIM_DATE
	private String	unclaimBy;			// LOAN_SETUP_CLAIM.UN_CLAIM_FLAG_BY
	private Timestamp	unclaimDate;	// LOAN_SETUP_CLAIM.UN_CLAIM_FLAG_DATE
	private String	completeFlag;		// LOAN_SETUP_CLAIM.COMPLETE_FLAG
	private String	completeBy;			// LOAN_SETUP_CLAIM.COMPLETE_BY
	private Timestamp	completeDate;	// LOAN_SETUP_CLAIM.COMPLETE_DATE
	private String	status;				// LOAN_SETUP_CLAIM.LOAN_SETUP_STATUS
	private String	accountNo;			// LOAN_SETUP_CLAIM.LOAN_ACCOUNT_NO
	private String	accountStatus;		// LOAN_SETUP_CLAIM.LOAN_ACCOUNT_STATUS
	private Timestamp	accountOpenDate;// LOAN_SETUP_CLAIM.LOAN_ACCOUNT_OPEN_DATE
	private Timestamp	createDate;		// LOAN_SETUP_CLAIM.CREATE_DATE
	private String	createBy;			// LOAN_SETUP_CLAIM.CREATE_BY
	private Timestamp	updateDate;		// LOAN_SETUP_CLAIM.UPDATE_DATE
	private String	updateBy;			// LOAN_SETUP_CLAIM.UPDATE_BY
	private String 		loanId;			// LOAN_SETUP_CLAIM.LOAN_ID
	
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getClaimId() {
		return claimId;
	}
	public void setClaimId(String claimId) {
		this.claimId = claimId;
	}
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getApplicationGroupNo() {
		return applicationGroupNo;
	}
	public void setApplicationGroupNo(String applicationGroupNo) {
		this.applicationGroupNo = applicationGroupNo;
	}
	public String getCisNo() {
		return cisNo;
	}
	public void setCisNo(String cisNo) {
		this.cisNo = cisNo;
	}
	public String getThTitle() {
		return thTitle;
	}
	public void setThTitle(String thTitle) {
		this.thTitle = thTitle;
	}
	public String getThName() {
		return thName;
	}
	public void setThName(String thName) {
		this.thName = thName;
	}
	public String getThMidName() {
		return thMidName;
	}
	public void setThMidName(String thMidName) {
		this.thMidName = thMidName;
	}
	public String getThSurname() {
		return thSurname;
	}
	public void setThSurname(String thSurname) {
		this.thSurname = thSurname;
	}
	public Timestamp getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(Timestamp submitDate) {
		this.submitDate = submitDate;
	}
	// String must be in YYYY-MM-DD format
	public void setSubmitDate(String submitDateStr) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date submitDate;
		try {
			submitDate = df.parse(submitDateStr);
			Timestamp tm = new Timestamp(submitDate.getTime()); 
			this.submitDate = tm;
		}
		catch (Exception e) {
			System.err.println("setSubmitDate() cannot parse given date: " + submitDateStr);
			System.err.println(e);
		}
	}
	public String getClaimFlag() {
		return claimFlag;
	}
	public void setClaimFlag(String claimFlag) {
		this.claimFlag = claimFlag;
	}
	public String getClaimBy() {
		return claimBy;
	}
	public void setClaimBy(String claimBy) {
		this.claimBy = claimBy;
	}
	public Timestamp getClaimDate() {
		return claimDate;
	}
	public void setClaimDate(Timestamp claimDate) {
		this.claimDate = claimDate;
	}
	public String getUnclaimBy() {
		return unclaimBy;
	}
	public void setUnclaimBy(String unclaimBy) {
		this.unclaimBy = unclaimBy;
	}
	public Timestamp getUnclaimDate() {
		return unclaimDate;
	}
	public void setUnclaimDate(Timestamp unclaimDate) {
		this.unclaimDate = unclaimDate;
	}
	public String getCompleteFlag() {
		return completeFlag;
	}
	public void setCompleteFlag(String completeFlag) {
		this.completeFlag = completeFlag;
	}
	public String getCompleteBy() {
		return completeBy;
	}
	public void setCompleteBy(String completeBy) {
		this.completeBy = completeBy;
	}
	public Timestamp getCompleteDate() {
		return completeDate;
	}
	public void setCompleteDate(Timestamp completeDate) {
		this.completeDate = completeDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public Timestamp getAccountOpenDate() {
		return accountOpenDate;
	}
	public void setAccountOpenDate(Timestamp accountOpenDate) {
		this.accountOpenDate = accountOpenDate;
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
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public void setTypeOfStampDuty() {
		this.type = STAMP_DUTY_TYPE;
	}
	public void setTypeOfMailingAddress() {
		this.type = MAILING_ADDRESS_TYPE;
	}
	public void claim(String claimUserId, Timestamp claimDate) {
		this.claimBy = claimUserId;
		this.claimDate = claimDate;
		this.claimFlag = "Y";
		this.status = CLAIM_STATUS;
	}
	public void unclaim(String unclaimUserId, Timestamp unclaimDate) {
		this.unclaimBy = unclaimUserId;
		this.unclaimDate = unclaimDate;
		this.claimFlag = "N";
		this.status = NOT_CLAIM_STATUS;
	}
	public void complete(String completeUserId, Timestamp completeDate) {
		this.completeBy	 = completeUserId;
		this.completeDate = completeDate;
		this.completeFlag = "Y";
		this.claimFlag = "N";
		this.status = COMPLETE_STATUS;
	}
	public void pendingSetup() {
		this.claimBy = null;
		this.claimDate = null;
		this.unclaimBy = null;
		this.unclaimDate = null;
		this.completeBy	 = null;
		this.completeDate = null;
		this.claimFlag = "N";
		this.completeFlag = "N";
		this.status = PENDING_SETUP_STATUS;
	}
	public String toString() {
		StringBuilder s = new StringBuilder("{");
		s.append("claimId="); s.append(claimId);
		s.append(",addressId="); s.append(addressId);
		s.append(",type="); s.append(type);
		s.append(",applicationGroupNo="); s.append(applicationGroupNo);
		s.append(",cisNo="); s.append(cisNo);
		s.append(",thTitle="); s.append(thTitle);
		s.append(",thName="); s.append(thName);
		s.append(",thMidName="); s.append(thMidName);
		s.append(",thSurname="); s.append(thSurname);
		s.append(",submitDate="); s.append(submitDate);
		s.append(",claimFlag="); s.append(claimFlag);
		s.append(",claimBy="); s.append(claimBy);
		s.append(",claimDate="); s.append(claimDate);
		s.append(",unclaimBy="); s.append(unclaimBy);
		s.append(",unclaimDate="); s.append(unclaimDate);
		s.append(",completeFlag="); s.append(completeFlag);
		s.append(",completeBy="); s.append(completeBy);
		s.append(",completeDate="); s.append(completeDate);
		s.append(",status="); s.append(status);
		s.append(",accountNo="); s.append(accountNo);
		s.append(",accountStatus="); s.append(accountStatus);
		s.append(",accountOpenDate="); s.append(accountOpenDate);
		s.append(",createDate="); s.append(createDate);
		s.append(",createBy="); s.append(createBy);
		s.append(",updateDate="); s.append(updateDate);
		s.append(",updateBy="); s.append(updateBy);
		s.append("}");
		return s.toString();
	}
}