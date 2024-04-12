package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;

public class LoanSetupStampDutyDataM implements Serializable,Cloneable {
	private String		claimId;			// LOAN_SETUP_STAMP_DUTY.CLAIM_ID
	private String		stampDutyId;		// LOAN_SETUP_STAMP_DUTY.STAMP_DUTY_ID
	private BigDecimal 	periodNo;			// LOAN_SETUP_STAMP_DUTY.PERIOD_NO
	private BigDecimal	periodDateFrom;		// LOAN_SETUP_STAMP_DUTY.PERIOD_DATE_FROM
	private BigDecimal	periodDateTo;		// LOAN_SETUP_STAMP_DUTY.PERIOD_DATE_TO
	private BigDecimal	periodMonth;		// LOAN_SETUP_STAMP_DUTY.PERIOD_MONTH
	private BigDecimal	periodYear;			// LOAN_SETUP_STAMP_DUTY.PERIOD_YEAR
	private BigDecimal	stampDutyFee;		// LOAN_SETUP_STAMP_DUTY.STAMP_DUTY_FEE
	private BigDecimal	finalLoanAmt;		// LOAN_SETUP_STAMP_DUTY.FINAL_LOAN_AMT
	private String		requesterName;		// LOAN_SETUP_STAMP_DUTY.REQUESTER_NAME
	private String		requesterPosition;	// LOAN_SETUP_STAMP_DUTY.REQUESTER_POSITION
	private String 		loanId;				// LOAN_SETUP_STAMP_DUTY.LOAN_ID
	
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
	public String getStampDutyId() {
		return stampDutyId;
	}
	public void setStampDutyId(String stampDutyId) {
		this.stampDutyId = stampDutyId;
	}
	public BigDecimal getPeriodNo() {
		return periodNo;
	}
	public void setPeriodNo(BigDecimal periodNo) {
		this.periodNo = periodNo;
	}
	public BigDecimal getPeriodDateFrom() {
		return periodDateFrom;
	}
	public void setPeriodDateFrom(BigDecimal periodDateFrom) {
		this.periodDateFrom = periodDateFrom;
	}
	public BigDecimal getPeriodDateTo() {
		return periodDateTo;
	}
	public void setPeriodDateTo(BigDecimal periodDateTo) {
		this.periodDateTo = periodDateTo;
	}
	public BigDecimal getPeriodMonth() {
		return periodMonth;
	}
	public void setPeriodMonth(BigDecimal periodMonth) {
		this.periodMonth = periodMonth;
	}
	public BigDecimal getPeriodYear() {
		return periodYear;
	}
	public void setPeriodYear(BigDecimal periodYear) {
		this.periodYear = periodYear;
	}
	public BigDecimal getStampDutyFee() {
		return stampDutyFee;
	}
	public void setStampDutyFee(BigDecimal stampDutyFee) {
		this.stampDutyFee = stampDutyFee;
	}
	public BigDecimal getFinalLoanAmt() {
		return finalLoanAmt;
	}
	public void setFinalLoanAmt(BigDecimal finalLoanAmt) {
		this.finalLoanAmt = finalLoanAmt;
	}
	public String getRequesterName() {
		return requesterName;
	}
	public void setRequesterName(String requesterName) {
		this.requesterName = requesterName;
	}
	public String getRequesterPosition() {
		return requesterPosition;
	}
	public void setRequesterPosition(String requesterPosition) {
		this.requesterPosition = requesterPosition;
	}
	public String toString() {
		StringBuilder s = new StringBuilder("{");
		s.append("claimId="); s.append(claimId);
		s.append(",stampDutyId="); s.append(stampDutyId);
		s.append(",periodNo="); s.append(periodNo);
		s.append(",periodDateFrom="); s.append(periodDateFrom);
		s.append(",periodDateTo="); s.append(periodDateTo);
		s.append(",periodMonth="); s.append(periodMonth);
		s.append(",periodYear="); s.append(periodYear);
		s.append(",stampDutyFee="); s.append(stampDutyFee);
		s.append(",finalLoanAmt="); s.append(finalLoanAmt);
		s.append(",requesterName="); s.append(requesterName);
		s.append(",requesterPosition="); s.append(requesterPosition);
		s.append("}");
		return s.toString();
	}
}
