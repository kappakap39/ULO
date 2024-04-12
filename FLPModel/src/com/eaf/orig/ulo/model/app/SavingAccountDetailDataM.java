package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class SavingAccountDetailDataM implements Serializable,Cloneable{
	public SavingAccountDetailDataM(){
		super();
	}
	private int seq;
	private String savingAccDetailId;	//INC_SAVING_ACC_DETAIL.SAVING_ACC_DETAIL_ID(VARCHAR2)
	private String savingAccId;	//INC_SAVING_ACC_DETAIL.SAVING_ACC_ID(VARCHAR2)
	private String year;	//INC_SAVING_ACC_DETAIL.YEAR(VARCHAR2)
	private String month;	//INC_SAVING_ACC_DETAIL.MONTH(VARCHAR2)
	private BigDecimal amount;	//INC_SAVING_ACC_DETAIL.AMOUNT(NUMBER)
	private String createBy;	//INC_SAVING_ACC_DETAIL.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//INC_SAVING_ACC_DETAIL.CREATE_DATE(DATE)
	private String updateBy;	//INC_SAVING_ACC_DETAIL.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//INC_SAVING_ACC_DETAIL.UPDATE_DATE(DATE)
	
	public static final int MONTH_COUNT = 7;
	public static final int CONSECUTIVE_COUNT = 6;
	
	public String getSavingAccDetailId() {
		return savingAccDetailId;
	}
	public void setSavingAccDetailId(String savingAccDetailId) {
		this.savingAccDetailId = savingAccDetailId;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getSavingAccId() {
		return savingAccId;
	}
	public void setSavingAccId(String savingAccId) {
		this.savingAccId = savingAccId;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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
