package com.eaf.orig.ulo.model.app;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class OtherIncomeDataM extends IncomeCategoryDataM{
	public OtherIncomeDataM(){
		super();
	}
//	private String incomeId;	//INC_OTH_INCOME.INCOME_ID(VARCHAR2)
	private String othIncomeId;	//INC_OTH_INCOME.OTH_INCOME_ID(VARCHAR2)
	private String incomeTypeDesc;	//INC_OTH_INCOME.INCOME_TYPE_DESC(VARCHAR2)
	private BigDecimal incomeAmount;	//INC_OTH_INCOME.INCOME_AMOUNT(NUMBER)
	private String createBy;	//INC_OTH_INCOME.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//INC_OTH_INCOME.CREATE_DATE(DATE)
	private String updateBy;	//INC_OTH_INCOME.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//INC_OTH_INCOME.UPDATE_DATE(DATE)
	
//	public String getIncomeId() {
//		return incomeId;
//	}
//	public void setIncomeId(String incomeId) {
//		this.incomeId = incomeId;
//	}
	public String getOthIncomeId() {
		return othIncomeId;
	}
	public void setOthIncomeId(String othIncomeId) {
		this.othIncomeId = othIncomeId;
	}
	public String getIncomeTypeDesc() {
		return incomeTypeDesc;
	}
	public void setIncomeTypeDesc(String incomeTypeDesc) {
		this.incomeTypeDesc = incomeTypeDesc;
	}
	public BigDecimal getIncomeAmount() {
		return incomeAmount;
	}
	public void setIncomeAmount(BigDecimal incomeAmount) {
		this.incomeAmount = incomeAmount;
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
		return getOthIncomeId();
	}	
}
