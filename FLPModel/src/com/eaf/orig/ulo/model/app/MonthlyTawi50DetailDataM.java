package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class MonthlyTawi50DetailDataM implements Serializable,Cloneable{
	public MonthlyTawi50DetailDataM(){
		super();
	}
	private int seq;
	private String monthlyTawiDetailId;	//INC_MONTHLY_TAWI_DETAIL.MONTHLY_TAWI_DETAIL_ID(VARCHAR2)
	private String monthlyTawiId;	//INC_MONTHLY_TAWI_DETAIL.MONTHLY_TAWI_ID(VARCHAR2)
	private BigDecimal amount;	//INC_MONTHLY_TAWI_DETAIL.AMOUNT(NUMBER)
	private String month;	//INC_MONTHLY_TAWI_DETAIL.MONTH(VARCHAR2)
	private String year;	//INC_MONTHLY_TAWI_DETAIL.YEAR(VARCHAR2)
	private String createBy;	//INC_MONTHLY_TAWI_DETAIL.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//INC_MONTHLY_TAWI_DETAIL.CREATE_DATE(DATE)
	private String updateBy;	//INC_MONTHLY_TAWI_DETAIL.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//INC_MONTHLY_TAWI_DETAIL.UPDATE_DATE(DATE)
	public static final int MONTH_COUNT = 7;
	
	public String getMonthlyTawiDetailId() {
		return monthlyTawiDetailId;
	}
	public void setMonthlyTawiDetailId(String monthlyTawiDetailId) {
		this.monthlyTawiDetailId = monthlyTawiDetailId;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getMonthlyTawiId() {
		return monthlyTawiId;
	}
	public void setMonthlyTawiId(String monthlyTawiId) {
		this.monthlyTawiId = monthlyTawiId;
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
