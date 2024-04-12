package com.eaf.orig.ulo.model.app;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class PayrollDataM extends IncomeCategoryDataM {
	public PayrollDataM(){
		super();
	}
//	private int seq;
//	private String incomeId;	//INC_PAYROLL.INCOME_ID(VARCHAR2)
	private String payrollId;	//INC_PAYROLL.PAYROLL_ID(VARCHAR2)
	private BigDecimal income;	//INC_PAYROLL.INCOME(NUMBER)
//	private String month;	//INC_PAYROLL.MONTH(VARCHAR2)
//	private String year;	//INC_PAYROLL.YEAR(VARCHAR2)
//	private String editable;	//INC_PAYROLL.EDITABLE(VARCHAR2)
	private String createBy;	//INC_PAYROLL.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//INC_PAYROLL.CREATE_DATE(DATE)
	private String updateBy;	//INC_PAYROLL.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//INC_PAYROLL.UPDATE_DATE(DATE)
//	private String compareFlag; //INC_PAYROLL.COMPARE_FLAG(VARCHAR2)
	public static final int RECORD_COUNT = 6;
	private int noOfEmployee; //INC_PAYROLL.NO_OF_EMPLOYEE(NUMBER)	
	
//	public String getCompareFlag() {
//		return compareFlag;
//	}
//	public void setCompareFlag(String compareFlag) {
//		this.compareFlag = compareFlag;
//	}
//	public int getSeq() {
//		return seq;
//	}
//	public void setSeq(int seq) {
//		this.seq = seq;
//	}
//	public String getIncomeId() {
//		return incomeId;
//	}
//	public void setIncomeId(String incomeId) {
//		this.incomeId = incomeId;
//	}
	public String getPayrollId() {
		return payrollId;
	}
	public void setPayrollId(String payrollId) {
		this.payrollId = payrollId;
	}
	public BigDecimal getIncome() {
		return income;
	}
	public void setIncome(BigDecimal income) {
		this.income = income;
	}	
//	public String getMonth() {
//		return month;
//	}
//	public void setMonth(String month) {
//		this.month = month;
//	}
//	public String getYear() {
//		return year;
//	}
//	public void setYear(String year) {
//		this.year = year;
//	}
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
//	public String getEditable() {
//		return editable;
//	}
//	public void setEditable(String editable) {
//		this.editable = editable;
//	}	
	@Override
	public String getId() {
		return getPayrollId();
	}
	public int getNoOfEmployee() {
		return noOfEmployee;
	}
	public void setNoOfEmployee(int noOfEmployee) {
		this.noOfEmployee = noOfEmployee;
	}	
}
