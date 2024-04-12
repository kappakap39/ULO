package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class PrivilegeProjectCodeProductSavingDataM implements Serializable,Cloneable{
	public PrivilegeProjectCodeProductSavingDataM(){
		super();
	}
	private String productSavingId;	//XRULES_PRVLG_PRODUCT_SAVING.PRODUCT_SAVING_ID(VARCHAR2)
	private String prvlgPrjCdeId;	//XRULES_PRVLG_PRODUCT_SAVING.PRVLG_PRJ_CDE_ID(VARCHAR2)
	private BigDecimal holdingRatio;	//XRULES_PRVLG_PRODUCT_SAVING.HOLDING_RATIO(NUMBER)
	private String idNo;	//XRULES_PRVLG_PRODUCT_SAVING.ID_NO(VARCHAR2)
	private String relationshipTransfer;	//XRULES_PRVLG_PRODUCT_SAVING.RELATIONSHIP_TRANSFER(VARCHAR2)
	private String productType;	//XRULES_PRVLG_PRODUCT_SAVING.PRODUCT_TYPE(VARCHAR2)
	private BigDecimal accountBalance;	//XRULES_PRVLG_PRODUCT_SAVING.ACCOUNT_BALANCE(NUMBER)
	private String accountNo;	//XRULES_PRVLG_PRODUCT_SAVING.ACCOUNT_NO(VARCHAR2)
	private String cisNo;	//XRULES_PRVLG_PRODUCT_SAVING.CIS_NO(VARCHAR2)
	private String createBy;	//XRULES_PRVLG_PRODUCT_SAVING.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//XRULES_PRVLG_PRODUCT_SAVING.CREATE_DATE(DATE)
	private String updateBy;	//XRULES_PRVLG_PRODUCT_SAVING.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//XRULES_PRVLG_PRODUCT_SAVING.UPDATE_DATE(DATE)
	private BigDecimal accountBalanceStart; //XRULES_PRVLG_PRODUCT_SAVING.UPDATE_DATE(DATE)
	
	private int seq;
	public BigDecimal getAccountBalanceStart() {
		return accountBalanceStart;
	}
	public void setAccountBalanceStart(BigDecimal accountBalanceStart) {
		this.accountBalanceStart = accountBalanceStart;
	}
	public String getProductSavingId() {
		return productSavingId;
	}
	public void setProductSavingId(String productSavingId) {
		this.productSavingId = productSavingId;
	}
	public String getPrvlgPrjCdeId() {
		return prvlgPrjCdeId;
	}
	public void setPrvlgPrjCdeId(String prvlgPrjCdeId) {
		this.prvlgPrjCdeId = prvlgPrjCdeId;
	}
	public BigDecimal getHoldingRatio() {
		return holdingRatio;
	}
	public void setHoldingRatio(BigDecimal holdingRatio) {
		this.holdingRatio = holdingRatio;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getRelationshipTransfer() {
		return relationshipTransfer;
	}
	public void setRelationshipTransfer(String relationshipTransfer) {
		this.relationshipTransfer = relationshipTransfer;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
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
	public String getCisNo() {
		return cisNo;
	}
	public void setCisNo(String cisNo) {
		this.cisNo = cisNo;
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
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
}
