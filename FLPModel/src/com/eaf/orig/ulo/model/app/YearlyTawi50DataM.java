package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class YearlyTawi50DataM extends IncomeCategoryDataM {
	public YearlyTawi50DataM(){
		super();
	}
//	private int seq;
	private String yearlyTawiId; //INC_YEARLY_TAWI.YEARLY_TAWI_ID(VARCHAR2)
//	private String incomeId;	//INC_YEARLY_TAWI.INCOME_ID(VARCHAR2)
	private String companyName;	//INC_YEARLY_TAWI.COMPANY_NAME(VARCHAR2)
	private int month;	//INC_YEARLY_TAWI.NO_MONTH(NUMBER)
	private int year;	//INC_YEARLY_TAWI.YEAR(NUMBER)
	private BigDecimal sumSso;	//INC_YEARLY_TAWI.SUM_SSO(NUMBER)
	private BigDecimal income402;	//INC_YEARLY_TAWI.INCOME40_2(NUMBER)
	private BigDecimal income401;	//INC_YEARLY_TAWI.INCOME40_1(NUMBER)
	private String createBy;	//INC_YEARLY_TAWI.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//INC_YEARLY_TAWI.CREATE_DATE(DATE)
	private String updateBy;	//INC_YEARLY_TAWI.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//INC_YEARLY_TAWI.UPDATE_DATE(DATE)
//	private String compareFlag; //INC_YEARLY_TAWI.COMPARE_FLAG(VARCHAR2)
		
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
	public String getYearlyTawiId() {
		return yearlyTawiId;
	}
	public void setYearlyTawiId(String yearlyTawiId) {
		this.yearlyTawiId = yearlyTawiId;
	}
//	public String getIncomeId() {
//		return incomeId;
//	}
//	public void setIncomeId(String incomeId) {
//		this.incomeId = incomeId;
//	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public BigDecimal getSumSso() {
		return sumSso;
	}
	public void setSumSso(BigDecimal sumSso) {
		this.sumSso = sumSso;
	}
	public BigDecimal getIncome402() {
		return income402;
	}
	public void setIncome402(BigDecimal income402) {
		this.income402 = income402;
	}
	public BigDecimal getIncome401() {
		return income401;
	}
	public void setIncome401(BigDecimal income401) {
		this.income401 = income401;
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
		return getYearlyTawiId();
	}
	
}
