package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class OtherBankFixedAccountDataM implements Serializable,Cloneable {
	public OtherBankFixedAccountDataM(){
		super();
	}
	private String othBankFixedAccId; //INC_OTH_BANK_FIXED_ACC.OTH_BANK_FIXED_ACC_ID(VARCHAR2)
	private String incomeId;	//INC_OTH_BANK_FIXED_ACC.INCOME_ID(VARCHAR2)
	private Date openDate;	//INC_OTH_BANK_FIXED_ACC.OPEN_DATE(DATE)
	private String bankCode;	//INC_OTH_BANK_FIXED_ACC.BANK_CODE(VARCHAR2)
	private String accountNo;	//INC_OTH_BANK_FIXED_ACC.ACCOUNT_NO(VARCHAR2)
	private BigDecimal accountBalance;	//INC_OTH_BANK_FIXED_ACC.ACCOUNT_BALANCE(NUMBER)
	private String accountName;	//INC_OTH_BANK_FIXED_ACC.ACCOUNT_NAME(VARCHAR2)
	private BigDecimal holdingRatio;	//INC_OTH_BANK_FIXED_ACC.HOLDING_RATIO(NUMBER)
	private String createBy;	//INC_OTH_BANK_FIXED_ACC.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//INC_OTH_BANK_FIXED_ACC.CREATE_DATE(DATE)
	private String updateBy;	//INC_OTH_BANK_FIXED_ACC.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//INC_OTH_BANK_FIXED_ACC.UPDATE_DATE(DATE)
	
	public String getIncomeId() {
		return incomeId;
	}
	public void setIncomeId(String incomeId) {
		this.incomeId = incomeId;
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
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public BigDecimal getHoldingRatio() {
		return holdingRatio;
	}
	public void setHoldingRatio(BigDecimal holdingRatio) {
		this.holdingRatio = holdingRatio;
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
	public String getOthBankFixedAccId() {
		return othBankFixedAccId;
	}
	public void setOthBankFixedAccId(String othBankFixedAccId) {
		this.othBankFixedAccId = othBankFixedAccId;
	}
}
