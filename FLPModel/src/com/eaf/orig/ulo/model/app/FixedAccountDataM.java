package com.eaf.orig.ulo.model.app;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author septemwi
 *
 */
public class FixedAccountDataM extends IncomeCategoryDataM {
	public FixedAccountDataM(){
		super();
	}
//	private int seq;
	private String fixedAccId;	//INC_FIXED_ACC.FIXED_ACC_ID(VARCHAR2)
	private String accountName;	//INC_FIXED_ACC.ACCOUNT_NAME(VARCHAR2)
	private Date openDate;	//INC_FIXED_ACC.OPEN_DATE(DATE)
	private BigDecimal holdingRatio;	//INC_FIXED_ACC.HOLDING_RATIO(NUMBER)
	private BigDecimal accountBalance;	//INC_FIXED_ACC.ACCOUNT_BALANCE(NUMBER)
	private String accountNo;	//INC_FIXED_ACC.ACCOUNT_NO(VARCHAR2)
	private String bankCode;    //INC_FIXED_ACC.BANK_CODE(VARCHAR2)
	private String createBy;	//INC_FIXED_ACC.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//INC_FIXED_ACC.CREATE_DATE(DATE)
	private String updateBy;	//INC_FIXED_ACC.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//INC_FIXED_ACC.UPDATE_DATE(DATE)
	
	public String getFixedAccId() {
		return fixedAccId;
	}
	public void setFixedAccId(String fixedAccId) {
		this.fixedAccId = fixedAccId;
	}
//	public String getIncomeId() {
//		return incomeId;
//	}
//	public void setIncomeId(String incomeId) {
//		this.incomeId = incomeId;
//	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	public BigDecimal getHoldingRatio() {
		return holdingRatio;
	}
	public void setHoldingRatio(BigDecimal holdingRatio) {
		this.holdingRatio = holdingRatio;
	}
	public BigDecimal getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
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
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}	
//	public ArrayList<FixedAccountDetailDataM> getFixedAccountDetails() {
//		return fixedAccountDetails;
//	}
//	public void setFixedAccountDetails(ArrayList<FixedAccountDetailDataM> fixedAccountDetails) {
//		this.fixedAccountDetails = fixedAccountDetails;
//	}
	@Override
	public String getId() {
		return getFixedAccId();
	}
}
