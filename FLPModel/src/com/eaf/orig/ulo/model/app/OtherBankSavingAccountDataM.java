package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class OtherBankSavingAccountDataM implements Serializable,Cloneable{
	public OtherBankSavingAccountDataM(){
		super();
	}
	private String incomeId;	//INC_OTH_BANK_SAV_ACC.INCOME_ID(VARCHAR2)
	private String othSavAccId;	//INC_OTH_BANK_SAV_ACC.OTH_SAV_ACC_ID(VARCHAR2)
	private BigDecimal monthN3;	//INC_OTH_BANK_SAV_ACC.MONTH_N3(NUMBER)
	private BigDecimal monthN2;	//INC_OTH_BANK_SAV_ACC.MONTH_N2(NUMBER)
	private String accountName;	//INC_OTH_BANK_SAV_ACC.ACCOUNT_NAME(VARCHAR2)
	private Date openDate;	//INC_OTH_BANK_SAV_ACC.OPEN_DATE(DATE)
	private BigDecimal monthN5;	//INC_OTH_BANK_SAV_ACC.MONTH_N5(NUMBER)
	private String bankCode;	//INC_OTH_BANK_SAV_ACC.BANK_CODE(VARCHAR2)
	private BigDecimal holdingRatio;	//INC_OTH_BANK_SAV_ACC.HOLDING_RATIO(NUMBER)
	private String accountNo;	//INC_OTH_BANK_SAV_ACC.ACCOUNT_NO(VARCHAR2)
	private BigDecimal monthN;	//INC_OTH_BANK_SAV_ACC.MONTH_N(NUMBER)
	private BigDecimal monthN1;	//INC_OTH_BANK_SAV_ACC.MONTH_N1(NUMBER)
	private BigDecimal monthN6;	//INC_OTH_BANK_SAV_ACC.MONTH_N6(NUMBER)
	private BigDecimal monthN4;	//INC_OTH_BANK_SAV_ACC.MONTH_N4(NUMBER)
	private String createBy;	//INC_OTH_BANK_SAV_ACC.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//INC_OTH_BANK_SAV_ACC.CREATE_DATE(DATE)
	private String updateBy;	//INC_OTH_BANK_SAV_ACC.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//INC_OTH_BANK_SAV_ACC.UPDATE_DATE(DATE)
	
	public String getIncomeId() {
		return incomeId;
	}
	public void setIncomeId(String incomeId) {
		this.incomeId = incomeId;
	}
	public String getOthSavAccId() {
		return othSavAccId;
	}
	public void setOthSavAccId(String othSavAccId) {
		this.othSavAccId = othSavAccId;
	}
	public BigDecimal getMonthN3() {
		return monthN3;
	}
	public void setMonthN3(BigDecimal monthN3) {
		this.monthN3 = monthN3;
	}
	public BigDecimal getMonthN2() {
		return monthN2;
	}
	public void setMonthN2(BigDecimal monthN2) {
		this.monthN2 = monthN2;
	}
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
	public BigDecimal getMonthN5() {
		return monthN5;
	}
	public void setMonthN5(BigDecimal monthN5) {
		this.monthN5 = monthN5;
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
	public BigDecimal getMonthN() {
		return monthN;
	}
	public void setMonthN(BigDecimal monthN) {
		this.monthN = monthN;
	}
	public BigDecimal getMonthN1() {
		return monthN1;
	}
	public void setMonthN1(BigDecimal monthN1) {
		this.monthN1 = monthN1;
	}
	public BigDecimal getMonthN6() {
		return monthN6;
	}
	public void setMonthN6(BigDecimal monthN6) {
		this.monthN6 = monthN6;
	}
	public BigDecimal getMonthN4() {
		return monthN4;
	}
	public void setMonthN4(BigDecimal monthN4) {
		this.monthN4 = monthN4;
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
