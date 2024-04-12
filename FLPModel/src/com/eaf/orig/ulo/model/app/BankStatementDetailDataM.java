package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class BankStatementDetailDataM implements Serializable,Cloneable{
	public BankStatementDetailDataM(){
		super();
	}
	private int seq;	
	private String bankStatementDetailId;	//INC_BANK_STATEMENT_DETAIL.BANK_STATEMENT_DETAIL_ID(VARCHAR2)
	private String bankStatementId;	//INC_BANK_STATEMENT_DETAIL.BANK_STATEMENT_ID(VARCHAR2)
	private String year;	//INC_BANK_STATEMENT_DETAIL.YEAR(VARCHAR2)
	private String month;	//INC_BANK_STATEMENT_DETAIL.MONTH(VARCHAR2)
	private BigDecimal amount;	//INC_BANK_STATEMENT_DETAIL.AMOUNT(NUMBER)
	private String createBy;	//INC_BANK_STATEMENT_DETAIL.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//INC_BANK_STATEMENT_DETAIL.CREATE_DATE(DATE)
	private String updateBy;	//INC_BANK_STATEMENT_DETAIL.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//INC_BANK_STATEMENT_DETAIL.UPDATE_DATE(DATE)
	public static final int MONTH_COUNT = 7;
	public static final int CONSECUTIVE_COUNT = 3;
	 
	public String getBankStatementDetailId() {
		return bankStatementDetailId;
	}
	public void setBankStatementDetailId(String bankStatementDetailId) {
		this.bankStatementDetailId = bankStatementDetailId;
	}
	public String getBankStatementId() {
		return bankStatementId;
	}
	public void setBankStatementId(String bankStatementId) {
		this.bankStatementId = bankStatementId;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
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
