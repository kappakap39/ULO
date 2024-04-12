package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class PrivilegeProjectCodeTransferDocDataM implements Serializable,Cloneable{
	public PrivilegeProjectCodeTransferDocDataM(){
		super();
	}
	private String prvlgDocId;	//XRULES_PRVLG_TRANSFER_DOC.PRVLG_DOC_ID(VARCHAR2)
	private String prvlgTransferDocId;	//XRULES_PRVLG_TRANSFER_DOC.PRVLG_TRANSFER_DOC_ID(VARCHAR2)
	private String relationship;	//XRULES_PRVLG_TRANSFER_DOC.RELATIONSHIP(VARCHAR2)
	private BigDecimal holdingRatio;	//XRULES_PRVLG_TRANSFER_DOC.HOLDING_RATIO(NUMBER)
	private String investType;	//XRULES_PRVLG_TRANSFER_DOC.INVEST_TYPE(VARCHAR2)
	private String accountNo;	//XRULES_PRVLG_TRANSFER_DOC.ACCOUNT_NO(VARCHAR2)
	private String idNo;	//XRULES_PRVLG_TRANSFER_DOC.ID_NO(VARCHAR2)
	private BigDecimal amount;	//XRULES_PRVLG_TRANSFER_DOC.AMOUNT(NUMBER)
	private String cisNo;	//XRULES_PRVLG_TRANSFER_DOC.CIS_NO(VARCHAR2)
	private String createBy;	//XRULES_PRVLG_TRANSFER_DOC.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//XRULES_PRVLG_TRANSFER_DOC.CREATE_DATE(DATE)
	private String updateBy;	//XRULES_PRVLG_TRANSFER_DOC.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//XRULES_PRVLG_TRANSFER_DOC.UPDATE_DATE(DATE)
	private int seq;
	
	
	public String getPrvlgDocId() {
		return prvlgDocId;
	}
	public void setPrvlgDocId(String prvlgDocId) {
		this.prvlgDocId = prvlgDocId;
	}
	public String getPrvlgTransferDocId() {
		return prvlgTransferDocId;
	}
	public void setPrvlgTransferDocId(String prvlgTransferDocId) {
		this.prvlgTransferDocId = prvlgTransferDocId;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public BigDecimal getHoldingRatio() {
		return holdingRatio;
	}
	public void setHoldingRatio(BigDecimal holdingRatio) {
		this.holdingRatio = holdingRatio;
	}
	public String getInvestType() {
		return investType;
	}
	public void setInvestType(String investType) {
		this.investType = investType;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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
