package com.eaf.orig.ulo.model.app;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class SalaryCertDataM extends IncomeCategoryDataM{
	public SalaryCertDataM(){
		super();
	}
//	private int seq;
//	private String incomeId;	//INC_SALARY_CERT.INCOME_ID(VARCHAR2)
	private String salaryCertId;	//INC_SALARY_CERT.SALARY_CERT_ID(VARCHAR2)
	private String companyName;	//INC_SALARY_CERT.COMPANY_NAME(VARCHAR2)
	private BigDecimal income;	//INC_SALARY_CERT.INCOME(NUMBER)
	private BigDecimal otherIncome; //INC_SALARY_CERT.OTHER_INCOME(NUMBER)
	private BigDecimal totalIncome; //INC_SALARY_CERT.TOTAL_INCOME(NUMBER)
	private String createBy;	//INC_SALARY_CERT.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//INC_SALARY_CERT.CREATE_DATE(DATE)
	private String updateBy;	//INC_SALARY_CERT.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//INC_SALARY_CERT.UPDATE_DATE(DATE)
//	private String compareFlag; //INC_SALARY_CERT.COMPARE_FLAG(VARCHAR2)
		
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
	public String getSalaryCertId() {
		return salaryCertId;
	}
	public void setSalaryCertId(String salaryCertId) {
		this.salaryCertId = salaryCertId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public BigDecimal getIncome() {
		return income;
	}
	public BigDecimal getOtherIncome() {
		return otherIncome;
	}
	public void setOtherIncome(BigDecimal otherIncome) {
		this.otherIncome = otherIncome;
	}
	public BigDecimal getTotalIncome() {
		return totalIncome;
	}
	public void setTotalIncome(BigDecimal totalIncome) {
		this.totalIncome = totalIncome;
	}
	public void setIncome(BigDecimal income) {
		this.income = income;
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
		return getSalaryCertId();
	}
}
