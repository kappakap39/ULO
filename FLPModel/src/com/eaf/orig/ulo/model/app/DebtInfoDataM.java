package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class DebtInfoDataM implements Cloneable,Serializable{
	public DebtInfoDataM(){
		super();
	}
	private int seq;
	private String debtId;	//ORIG_DEBT_INFO.DEBT_ID(VARCHAR2)
	private String personalId;	//ORIG_DEBT_INFO.PERSONAL_ID(VARCHAR2)
	private String debtType;	//ORIG_DEBT_INFO.DEBT_TYPE(VARCHAR2)
	private BigDecimal debtAmt; //ORIG_DEBT_INFO.DEBT_AMT(NUMBER)
	private String createBy;	//ORIG_DEBT_INFO.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_DEBT_INFO.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_DEBT_INFO.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_DEBT_INFO.UPDATE_DATE(DATE)
	private String compareFlag; //ORIG_DEBT_INFO.COMPARE_FLAG(VARCHAR2)
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getDebtId() {
		return debtId;
	}
	public void setDebtId(String debtId) {
		this.debtId = debtId;
	}
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public String getDebtType() {
		return debtType;
	}
	public void setDebtType(String debtType) {
		this.debtType = debtType;
	}
	public BigDecimal getDebtAmt() {
		return debtAmt;
	}
	public void setDebtAmt(BigDecimal debtAmt) {
		this.debtAmt = debtAmt;
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
	public String getCompareFlag() {
		return compareFlag;
	}
	public void setCompareFlag(String compareFlag) {
		this.compareFlag = compareFlag;
	}
}
