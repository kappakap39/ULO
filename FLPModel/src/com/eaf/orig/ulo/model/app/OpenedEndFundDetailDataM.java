package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class OpenedEndFundDetailDataM implements Serializable,Cloneable {
	public OpenedEndFundDetailDataM(){
		super();
	}
	private int seq;
	private String opnEndFundDetailId;	//INC_OPN_END_FUND_DETAIL.OPN_END_FUND_DETAIL_ID(VARCHAR2)
	private String opnEndFundId;	//INC_OPN_END_FUND_DETAIL.OPN_END_FUND_ID(VARCHAR2)
	private BigDecimal amount;	//INC_OPN_END_FUND_DETAIL.AMOUNT(NUMBER)
	private String month;	//INC_OPN_END_FUND_DETAIL.MONTH(VARCHAR2)
	private String year;	//INC_OPN_END_FUND_DETAIL.YEAR(VARCHAR2)
	private String createBy;	//INC_OPN_END_FUND_DETAIL.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//INC_OPN_END_FUND_DETAIL.CREATE_DATE(DATE)
	private String updateBy;	//INC_OPN_END_FUND_DETAIL.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//INC_OPN_END_FUND_DETAIL.UPDATE_DATE(DATE)
	
	public static final int MONTH_COUNT = 7;
	public static final int CONSECUTIVE_COUNT = 6;
	
	public String getOpnEndFundDetailId() {
		return opnEndFundDetailId;
	}
	public void setOpnEndFundDetailId(String opnEndFundDetailId) {
		this.opnEndFundDetailId = opnEndFundDetailId;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getOpnEndFundId() {
		return opnEndFundId;
	}
	public void setOpnEndFundId(String opnEndFundId) {
		this.opnEndFundId = opnEndFundId;
	}	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
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
