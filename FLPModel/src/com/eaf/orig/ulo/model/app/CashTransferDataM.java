package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class CashTransferDataM implements Serializable,Cloneable{
	public CashTransferDataM(){
		super();
	}
	public void init(String referId, String cashTransferId){
		this.loanId = referId;
		this.cashTransferId = cashTransferId;
	}
	private String cashTransferId;	//ORIG_CASH_TRANSFER.CASH_TRANSFER_ID(VARCHAR2)
	private String loanId;	//ORIG_CASH_TRANSFER.LOAN_ID(VARCHAR2)
	private String bankTransfer;	//ORIG_CASH_TRANSFER.BANK_TRANSFER(VARCHAR2)
	private String cashTransferType;	//ORIG_CASH_TRANSFER.CASH_TRANSFER_TYPE(VARCHAR2)
	private String expressTransfer;	//ORIG_CASH_TRANSFER.EXPRESS_TRANSFER(VARCHAR2)
	private String accountName;	//ORIG_CASH_TRANSFER.ACCOUNT_NAME(VARCHAR2)
	private BigDecimal percentTransfer;	//ORIG_CASH_TRANSFER.PERCENT_TRANSFER(NUMBER)
	private String productType;	//ORIG_CASH_TRANSFER.PRODUCT_TYPE(VARCHAR2)
	private BigDecimal firstTransferAmount;	//ORIG_CASH_TRANSFER.FIRST_TRANSFER_AMOUNT(NUMBER)
	private String transferAccount;	//ORIG_CASH_TRANSFER.TRANSFER_ACCOUNT(VARCHAR2)
	private String createBy;	//ORIG_CASH_TRANSFER.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_CASH_TRANSFER.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_CASH_TRANSFER.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_CASH_TRANSFER.UPDATE_DATE(DATE)
	private String completeData; //ORIG_CASH_TRANSFER.COMPLETE_DATA(VARCHAR2)
	private String callForCashFlag; //displayFlag
	private String transferAccountType; //ORIG_CASH_TRANSFER.TRANSFER_ACCOUNT_TYPE(VARCHAR2)
	
	public String getTransferAccountType() {
		return transferAccountType;
	}
	public void setTransferAccountType(String transferAccountType) {
		this.transferAccountType = transferAccountType;
	}
	public String getCashTransferId() {
		return cashTransferId;
	}
	public void setCashTransferId(String cashTransferId) {
		this.cashTransferId = cashTransferId;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getBankTransfer() {
		return bankTransfer;
	}
	public void setBankTransfer(String bankTransfer) {
		this.bankTransfer = bankTransfer;
	}
	public String getCashTransferType() {
		return cashTransferType;
	}
	public void setCashTransferType(String cashTransferType) {
		this.cashTransferType = cashTransferType;
	}	
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public BigDecimal getPercentTransfer() {
		return percentTransfer;
	}
	public void setPercentTransfer(BigDecimal percentTransfer) {
		this.percentTransfer = percentTransfer;
	}
	public BigDecimal getFirstTransferAmount() {
		return firstTransferAmount;
	}
	public void setFirstTransferAmount(BigDecimal firstTransferAmount) {
		this.firstTransferAmount = firstTransferAmount;
	}
	public String getExpressTransfer() {
		return expressTransfer;
	}
	public void setExpressTransfer(String expressTransfer) {
		this.expressTransfer = expressTransfer;
	}
	public String getTransferAccount() {
		return transferAccount;
	}
	public void setTransferAccount(String transferAccount) {
		this.transferAccount = transferAccount;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
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
	public String getCompleteData() {
		return completeData;
	}
	public void setCompleteData(String completeData) {
		this.completeData = completeData;
	}
	public String getCallForCashFlag() {
		return callForCashFlag;
	}
	public void setCallForCashFlag(String callForCashFlag) {
		this.callForCashFlag = callForCashFlag;
	}	
	
}
