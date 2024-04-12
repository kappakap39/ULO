package com.eaf.orig.ulo.model.app;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class PreviousIncomeDataM extends IncomeCategoryDataM {
	public PreviousIncomeDataM(){
		super();
	}
//	private int seq;
//	private String incomeId;	//INC_PREVIOUS_INCOME.INCOME_ID(VARCHAR2)
	private String previousIncomeId;	//INC_PREVIOUS_INCOME.PREVIOUS_INCOME_ID(VARCHAR2)
	private Date incomeDate;	//INC_PREVIOUS_INCOME.INCOME_DATE(CLOB)
	private String product;	//INC_PREVIOUS_INCOME.PRODUCT(VARCHAR2)
	private BigDecimal income;	//INC_PREVIOUS_INCOME.INCOME(NUMBER)
	private String createBy;	//INC_PREVIOUS_INCOME.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//INC_PREVIOUS_INCOME.CREATE_DATE(DATE)
	private String updateBy;	//INC_PREVIOUS_INCOME.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//INC_PREVIOUS_INCOME.UPDATE_DATE(DATE)
//	private String compareFlag; //INC_PREVIOUS_INCOME.COMPARE_FLAG(VARCHAR2)
//		
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
	public String getPreviousIncomeId() {
		return previousIncomeId;
	}
	public void setPreviousIncomeId(String previousIncomeId) {
		this.previousIncomeId = previousIncomeId;
	}
	public Date getIncomeDate() {
		return incomeDate;
	}
	public void setIncomeDate(Date incomeDate) {
		this.incomeDate = incomeDate;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public BigDecimal getIncome() {
		return income;
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
		return getPreviousIncomeId();
	}
	
}
