package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class PrivilegeProjectCodeKassetDocDataM implements Serializable,Cloneable{
	public PrivilegeProjectCodeKassetDocDataM(){
		super();
	}
	private String kassetDocId;	//XRULES_PRVLG_KASSET_DOC.KASSET_DOC_ID(VARCHAR2)
	private String prvlgDocId;	//XRULES_PRVLG_KASSET_DOC.PRVLG_DOC_ID(VARCHAR2)
	private BigDecimal fundL6m;	//XRULES_PRVLG_KASSET_DOC.FUND_L6M(NUMBER)
	private BigDecimal income;	//XRULES_PRVLG_KASSET_DOC.INCOME(NUMBER)
	private BigDecimal wealth;	//XRULES_PRVLG_KASSET_DOC.WEALTH(NUMBER)
	private BigDecimal fund6m;	//XRULES_PRVLG_KASSET_DOC.FUND_6M(NUMBER)
	private String createBy;	//XRULES_PRVLG_KASSET_DOC.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//XRULES_PRVLG_KASSET_DOC.CREATE_DATE(DATE)
	private String updateBy;	//XRULES_PRVLG_KASSET_DOC.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//XRULES_PRVLG_KASSET_DOC.UPDATE_DATE(DATE)
	private String kassetType; //XRULES_PRVLG_KASSET_DOC.KASSET_TYPE(VARCHAR2)
	private BigDecimal month1m;	//XRULES_PRVLG_KASSET_DOC.MONTH1_AMT(NUMBER)
	private BigDecimal month2m;	//XRULES_PRVLG_KASSET_DOC.MONTH2_AMT(NUMBER)
	private BigDecimal month3m;	//XRULES_PRVLG_KASSET_DOC.MONTH3_AMT(NUMBER)
	private BigDecimal month4m;	//XRULES_PRVLG_KASSET_DOC.MONTH4_AMT(NUMBER)
	private BigDecimal month5m;	//XRULES_PRVLG_KASSET_DOC.MONTH5_AMT(NUMBER)
	private BigDecimal month6m;	//XRULES_PRVLG_KASSET_DOC.MONTH6_AMT(NUMBER)
	public BigDecimal getMonth1m() {
		return month1m;
	}
	public void setMonth1m(BigDecimal month1m) {
		this.month1m = month1m;
	}
	public BigDecimal getMonth2m() {
		return month2m;
	}
	public void setMonth2m(BigDecimal month2m) {
		this.month2m = month2m;
	}
	public BigDecimal getMonth3m() {
		return month3m;
	}
	public void setMonth3m(BigDecimal month3m) {
		this.month3m = month3m;
	}
	public BigDecimal getMonth4m() {
		return month4m;
	}
	public void setMonth4m(BigDecimal month4m) {
		this.month4m = month4m;
	}
	public BigDecimal getMonth5m() {
		return month5m;
	}
	public void setMonth5m(BigDecimal month5m) {
		this.month5m = month5m;
	}
	public BigDecimal getMonth6m() {
		return month6m;
	}
	public void setMonth6m(BigDecimal month6m) {
		this.month6m = month6m;
	}
	public String getKassetType() {
		return kassetType;
	}
	public void setKassetType(String kassetType) {
		this.kassetType = kassetType;
	}
	public String getKassetDocId() {
		return kassetDocId;
	}
	public void setKassetDocId(String kassetDocId) {
		this.kassetDocId = kassetDocId;
	}
	public String getPrvlgDocId() {
		return prvlgDocId;
	}
	public void setPrvlgDocId(String prvlgDocId) {
		this.prvlgDocId = prvlgDocId;
	}
	public BigDecimal getFundL6m() {
		return fundL6m;
	}
	public void setFundL6m(BigDecimal fundL6m) {
		this.fundL6m = fundL6m;
	}
	public BigDecimal getIncome() {
		return income;
	}
	public void setIncome(BigDecimal income) {
		this.income = income;
	}
	public BigDecimal getWealth() {
		return wealth;
	}
	public void setWealth(BigDecimal wealth) {
		this.wealth = wealth;
	}
	public BigDecimal getFund6m() {
		return fund6m;
	}
	public void setFund6m(BigDecimal fund6m) {
		this.fund6m = fund6m;
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
