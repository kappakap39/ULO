package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class OtherClosedEndFundDataM implements Serializable,Cloneable {
	public OtherClosedEndFundDataM(){
		super();
	}
	private String incomeId;	//INC_OTH_CLS_END_FUND.INCOME_ID(VARCHAR2)
	private String othClsEndFundId; //INC_OTH_CLS_END_FUND.OTH_CLS_END_FUND_ID(VARCHAR2)
	private BigDecimal accountBalance;	//INC_OTH_CLS_END_FUND.ACCOUNT_BALANCE(NUMBER)
	private String fundType;	//INC_OTH_CLS_END_FUND.FUND_TYPE(VARCHAR2)
	private Date openDate;	//INC_OTH_CLS_END_FUND.OPEN_DATE(DATE)
	private String bankCode;	//INC_OTH_CLS_END_FUND.BANK_CODE(VARCHAR2)
	private BigDecimal holdingRatio;	//INC_OTH_CLS_END_FUND.HOLDING_RATIO(NUMBER)
	private String accountNo;	//INC_OTH_CLS_END_FUND.ACCOUNT_NO(VARCHAR2)
	private String accountName;	//INC_OTH_CLS_END_FUND.ACCOUNT_NAME(VARCHAR2)
	private String createBy;	//INC_OTH_CLS_END_FUND.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//INC_OTH_CLS_END_FUND.CREATE_DATE(DATE)
	private String updateBy;	//INC_OTH_CLS_END_FUND.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//INC_OTH_CLS_END_FUND.UPDATE_DATE(DATE)
		
	public String getOthClsEndFundId() {
		return othClsEndFundId;
	}
	public void setOthClsEndFundId(String othClsEndFundId) {
		this.othClsEndFundId = othClsEndFundId;
	}
	public String getIncomeId() {
		return incomeId;
	}
	public void setIncomeId(String incomeId) {
		this.incomeId = incomeId;
	}
	public BigDecimal getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}
	public String getFundType() {
		return fundType;
	}
	public void setFundType(String fundType) {
		this.fundType = fundType;
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
	public BigDecimal getHoldingRatio() {
		return holdingRatio;
	}
	public void setHoldingRatio(BigDecimal holdingRatio) {
		this.holdingRatio = holdingRatio;
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
}
