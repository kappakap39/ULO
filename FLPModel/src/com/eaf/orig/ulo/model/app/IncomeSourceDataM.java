package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;

public class IncomeSourceDataM implements Cloneable,Serializable{
	public IncomeSourceDataM(){
		super();
	}
	private String incomeSourceId;	//ORIG_INCOME_SOURCE.INCOME_SOURCE_ID(VARCHAR2)
	private String personalId;	//ORIG_INCOME_SOURCE.PERSONAL_ID(VARCHAR2)
	private String incomeSource;	//ORIG_INCOME_SOURCE.INCOME_SOURCE(VARCHAR2)
	private String createBy;	//ORIG_INCOME_SOURCE.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_INCOME_SOURCE.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_INCOME_SOURCE.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_INCOME_SOURCE.UPDATE_DATE(DATE)
	
	public String getIncomeSourceId() {
		return incomeSourceId;
	}
	public void setIncomeSourceId(String incomeSourceId) {
		this.incomeSourceId = incomeSourceId;
	}
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public String getIncomeSource() {
		return incomeSource;
	}
	public void setIncomeSource(String incomeSource) {
		this.incomeSource = incomeSource;
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
