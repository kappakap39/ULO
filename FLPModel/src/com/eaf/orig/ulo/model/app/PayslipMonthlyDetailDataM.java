package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class PayslipMonthlyDetailDataM implements Serializable,Cloneable{
	public PayslipMonthlyDetailDataM(){
		super();
	}
	private int seq;
	private String payslipMonthlyDetailId;	//INC_PAYSLIP_MONTHLY_DETAIL.PAYSLIP_MONTHLY_DETAIL_ID(VARCHAR2)
	private String payslipMonthlyId;	//INC_PAYSLIP_MONTHLY_DETAIL.PAYSLIP_MONTHLY_ID(VARCHAR2)
	private BigDecimal amount;	//INC_PAYSLIP_MONTHLY_DETAIL.AMOUNT(NUMBER)
	private String month;	//INC_PAYSLIP_MONTHLY_DETAIL.MONTH(VARCHAR2)
	private String year;	//INC_PAYSLIP_MONTHLY_DETAIL.YEAR(VARCHAR2)
	private String createBy;	//INC_PAYSLIP_MONTHLY_DETAIL.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//INC_PAYSLIP_MONTHLY_DETAIL.CREATE_DATE(DATE)
	private String updateBy;	//INC_PAYSLIP_MONTHLY_DETAIL.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//INC_PAYSLIP_MONTHLY_DETAIL.UPDATE_DATE(DATE)
	public static final int MONTH_COUNT = 7;
	public static final int FIX_VALIDATION_MONTH_COUNT = 4;

	public String getPayslipMonthlyDetailId() {
		return payslipMonthlyDetailId;
	}
	public void setPayslipMonthlyDetailId(String payslipMonthlyDetailId) {
		this.payslipMonthlyDetailId = payslipMonthlyDetailId;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getPayslipMonthlyId() {
		return payslipMonthlyId;
	}
	public void setPayslipMonthlyId(String payslipMonthlyId) {
		this.payslipMonthlyId = payslipMonthlyId;
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
