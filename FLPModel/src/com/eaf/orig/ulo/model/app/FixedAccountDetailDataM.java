package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class FixedAccountDetailDataM implements Serializable,Cloneable{
	public FixedAccountDetailDataM(){
		super();
	}
	private int seq;
	private String fixedAccDetailId;	//INC_FIXED_ACC_DETAIL.FIXED_ACC_DETAIL_ID(VARCHAR2)
	private String fixedAccId;	//INC_FIXED_ACC_DETAIL.FIXED_ACC_ID(VARCHAR2)
	private String sub;	//INC_FIXED_ACC_DETAIL.SUB(VARCHAR2)
	private BigDecimal outstandingBalance;	//INC_FIXED_ACC_DETAIL.OUTSTANDING_BALANCE(NUMBER)
	private Date depositDate;	//INC_FIXED_ACC_DETAIL.DEPOSIT_DATE(DATE)
	private String createBy;	//INC_FIXED_ACC_DETAIL.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//INC_FIXED_ACC_DETAIL.CREATE_DATE(DATE)
	private String updateBy;	//INC_FIXED_ACC_DETAIL.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//INC_FIXED_ACC_DETAIL.UPDATE_DATE(DATE)
	
	public String getFixedAccDetailId() {
		return fixedAccDetailId;
	}
	public void setFixedAccDetailId(String fixedAccDetailId) {
		this.fixedAccDetailId = fixedAccDetailId;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getFixedAccId() {
		return fixedAccId;
	}
	public void setFixedAccId(String fixedAccId) {
		this.fixedAccId = fixedAccId;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public BigDecimal getOutstandingBalance() {
		return outstandingBalance;
	}
	public void setOutstandingBalance(BigDecimal outstandingBalance) {
		this.outstandingBalance = outstandingBalance;
	}
	public Date getDepositDate() {
		return depositDate;
	}
	public void setDepositDate(Date depositDate) {
		this.depositDate = depositDate;
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
