package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class KYCDataM implements Serializable,Cloneable{
	public KYCDataM(){
		super();
	}
	private String personalId;	//ORIG_KYC.PERSONAL_ID(VARCHAR2)
	private String relTitleName;	//ORIG_KYC.REL_TITLE_NAME(VARCHAR2)
	private String customerRiskGrade;	//ORIG_KYC.CUSTOMER_RISK_GRADE(VARCHAR2)
	private String relDetail;	//ORIG_KYC.REL_DETAIL(VARCHAR2)
	private String relSurname;	//ORIG_KYC.REL_SURNAME(VARCHAR2)
	private Date workEndDate;	//ORIG_KYC.WORK_END_DATE(DATE)
	private String relPosition;	//ORIG_KYC.REL_POSITION(VARCHAR2)
	private Date workStartDate;	//ORIG_KYC.WORK_START_DATE(DATE)
	private String sanctionList;	//ORIG_KYC.SANCTION_LIST(VARCHAR2)
	private String relName;	//ORIG_KYC.REL_NAME(VARCHAR2)
	private String relationFlag;	//ORIG_KYC.RELATION_FLAG(VARCHAR2)
	private String createBy;	//ORIG_KYC.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_KYC.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_KYC.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_KYC.UPDATE_DATE(DATE)
	private String relTitleDesc; //ORIG_KYC.REL_TITLE_DESC(VARCHAR2)	
	
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public String getRelTitleName() {
		return relTitleName;
	}
	public void setRelTitleName(String relTitleName) {
		this.relTitleName = relTitleName;
	}
	public String getCustomerRiskGrade() {
		return customerRiskGrade;
	}
	public void setCustomerRiskGrade(String customerRiskGrade) {
		this.customerRiskGrade = customerRiskGrade;
	}
	public String getRelDetail() {
		return relDetail;
	}
	public void setRelDetail(String relDetail) {
		this.relDetail = relDetail;
	}
	public String getRelSurname() {
		return relSurname;
	}
	public void setRelSurname(String relSurname) {
		this.relSurname = relSurname;
	}
	public Date getWorkEndDate() {
		return workEndDate;
	}
	public void setWorkEndDate(Date workEndDate) {
		this.workEndDate = workEndDate;
	}
	public String getRelPosition() {
		return relPosition;
	}
	public void setRelPosition(String relPosition) {
		this.relPosition = relPosition;
	}
	public Date getWorkStartDate() {
		return workStartDate;
	}
	public void setWorkStartDate(Date workStartDate) {
		this.workStartDate = workStartDate;
	}
	public String getSanctionList() {
		return sanctionList;
	}
	public void setSanctionList(String sanctionList) {
		this.sanctionList = sanctionList;
	}
	public String getRelName() {
		return relName;
	}
	public void setRelName(String relName) {
		this.relName = relName;
	}
	public String getRelationFlag() {
		return relationFlag;
	}
	public void setRelationFlag(String relationFlag) {
		this.relationFlag = relationFlag;
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
	public String getRelTitleDesc() {
		return relTitleDesc;
	}
	public void setRelTitleDesc(String relTitleDesc) {
		this.relTitleDesc = relTitleDesc;
	}	
}
