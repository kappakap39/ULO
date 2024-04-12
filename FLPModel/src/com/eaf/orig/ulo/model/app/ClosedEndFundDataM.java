package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class ClosedEndFundDataM extends IncomeCategoryDataM {
	public ClosedEndFundDataM(){
		super();
	}
//	private int seq;
	private String clsEndFundId; //INC_CLS_END_FUND.CLS_END_FUND_ID(VARCHAR2)
//	private String incomeId;	//INC_CLS_END_FUND.INCOME_ID(VARCHAR2)
	private BigDecimal holdingRatio;	//INC_CLS_END_FUND.HOLDING_RATIO(NUMBER)
	private String accountName;	//INC_CLS_END_FUND.ACCOUNT_NAME(VARCHAR2)
	private String fundName;	//INC_CLS_END_FUND.FUND_NAME(VARCHAR2)
	private Date openDate;	//INC_CLS_END_FUND.OPEN_DATE(DATE)
	private String bankCode;	//INC_CLS_END_FUND.BANK_CODE(VARCHAR2)
	private String accountNo;	//INC_CLS_END_FUND.ACCOUNT_NO(VARCHAR2)
	private BigDecimal accountBalance;	//INC_CLS_END_FUND.ACCOUNT_BALANCE(NUMBER)
	private String createBy;	//INC_CLS_END_FUND.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//INC_CLS_END_FUND.CREATE_DATE(DATE)
	private String updateBy;	//INC_CLS_END_FUND.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//INC_CLS_END_FUND.UPDATE_DATE(DATE)
//	private String compareFlag; //INC_CLS_END_FUND.COMPARE_FLAG(VARCHAR2)	
		
//	public String getCompareFlag() {
//		return compareFlag;
//	}
//	public void setCompareFlag(String compareFlag) {
//		this.compareFlag = compareFlag;
//	}
//	public int getSeq() {
//		return seq;
//	}
//	public void setSeq(int seq) {
//		this.seq = seq;
//	}
	public String getClsEndFundId() {
		return clsEndFundId;
	}
	public void setClsEndFundId(String clsEndFundId) {
		this.clsEndFundId = clsEndFundId;
	}
//	public String getIncomeId() {
//		return incomeId;
//	}
//	public void setIncomeId(String incomeId) {
//		this.incomeId = incomeId;
//	}
	public BigDecimal getHoldingRatio() {
		return holdingRatio;
	}
	public void setHoldingRatio(BigDecimal holdingRatio) {
		this.holdingRatio = holdingRatio;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}	
	public String getFundName() {
		return fundName;
	}
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public BigDecimal getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
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
	@Override
	public String getId() {
		return getClsEndFundId();
	}	
}
