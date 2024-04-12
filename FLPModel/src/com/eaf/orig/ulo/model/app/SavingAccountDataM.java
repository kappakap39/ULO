package com.eaf.orig.ulo.model.app;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

public class SavingAccountDataM extends IncomeCategoryDataM {
	public SavingAccountDataM(){
		super();
	}
//	private int seq;
//	private String incomeId;	//INC_SAVING_ACC.INCOME_ID(VARCHAR2)
	private String savingAccId;	//INC_SAVING_ACC.SAVING_ACC_ID(VARCHAR2)
	private BigDecimal holdingRatio;	//INC_SAVING_ACC.HOLDING_RATIO(NUMBER)
	private String accountName;	//INC_SAVING_ACC.ACCOUNT_NAME(VARCHAR2)
	private Date openDate;	//INC_SAVING_ACC.OPEN_DATE(DATE)
	private BigDecimal accountBalance;	//INC_SAVING_ACC.ACCOUNT_BALANCE(NUMBER)
	private String accountNo;	//INC_SAVING_ACC.ACCOUNT_NO(VARCHAR2)
	private String bankCode;    //INC_SAVING_ACC.BANK_CODE(VARCHAR2)
	private String createBy;	//INC_SAVING_ACC.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//INC_SAVING_ACC.CREATE_DATE(DATE)
	private String updateBy;	//INC_SAVING_ACC.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//INC_SAVING_ACC.UPDATE_DATE(DATE)
//	private String compareFlag; //INC_SAVING_ACC.COMPARE_FLAG(VARCHAR2)
	
	private ArrayList<SavingAccountDetailDataM> savingAccountDetails;
		
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
//	public String getIncomeId() {
//		return incomeId;
//	}
//	public void setIncomeId(String incomeId) {
//		this.incomeId = incomeId;
//	}
	public String getSavingAccId() {
		return savingAccId;
	}
	public void setSavingAccId(String savingAccId) {
		this.savingAccId = savingAccId;
	}
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
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
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
	public ArrayList<SavingAccountDetailDataM> getSavingAccountDetails() {
		return savingAccountDetails;
	}
	public void setSavingAccountDetails(ArrayList<SavingAccountDetailDataM> savingAccountDetails) {
		this.savingAccountDetails = savingAccountDetails;
	}
	
	public SavingAccountDetailDataM getSavingAcctDetailById(String savingAccDetailId) {
		if(savingAccDetailId != null && savingAccountDetails != null) {
			for(SavingAccountDetailDataM monthlyDetailM: savingAccountDetails){
				if(savingAccDetailId.equals(monthlyDetailM.getSavingAccDetailId())) {
					return monthlyDetailM;
				}
			}
		}
		return null;
	}
	@Override
	public String getId() {
		return getSavingAccId();
	}
}
