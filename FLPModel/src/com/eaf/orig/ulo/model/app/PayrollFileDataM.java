package com.eaf.orig.ulo.model.app;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class PayrollFileDataM extends IncomeCategoryDataM {
	public PayrollFileDataM(){
		super();
	}
	private String payrollFileId;	//INC_PAYROLL_FILE.PAYROLL_FILE_ID(VARCHAR2)
	private String month;			//INC_PAYROLL_FILE.MONTH(VARCHAR2)
	private String year;			//INC_PAYROLL_FILE.YEAR(VARCHAR2)
	private BigDecimal amount;		//INC_PAYROLL_FILE.AMOUNT(NUMBER)
	private String fromFileFlag;	//INC_PAYROLL_FILE.FROM_FILE_FLAG(VARCHAR2)
	private String createBy;		//INC_PAYROLL_FILE.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//INC_PAYROLL_FILE.CREATE_DATE(DATE)
	private String updateBy;		//INC_PAYROLL_FILE.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//INC_PAYROLL_FILE.UPDATE_DATE(DATE)
//	private String compareFlag; 	//INC_PAYROLL_FILE.COMPARE_FLAG(VARCHAR2)
	public static final int RECORD_COUNT = 7;
	
	
	public String getPayrollFileId() {
		return payrollFileId;
	}
	public void setPayrollFileId(String payrollFileId) {
		this.payrollFileId = payrollFileId;
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
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getFromFileFlag() {
		return fromFileFlag;
	}
	public void setFromFileFlag(String fromFileFlag) {
		this.fromFileFlag = fromFileFlag;
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
	@Override
	public String getId() {
		return getPayrollFileId();
	}	
}
