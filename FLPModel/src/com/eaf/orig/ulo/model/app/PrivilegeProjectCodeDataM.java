package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class PrivilegeProjectCodeDataM implements Serializable,Cloneable{
	public PrivilegeProjectCodeDataM(){
		super();
	}
	private String verResultId;	//XRULES_PRVLG_PRJ_CDE.VER_RESULT_ID(VARCHAR2)	
	private String prvlgPrjCdeId;	//XRULES_PRVLG_PRJ_CDE.PRVLG_PRJ_CDE_ID(VARCHAR2)
	private String projectCode;	//XRULES_PRVLG_PRJ_CDE.PROJECT_CODE(VARCHAR2)
	private String createBy;	//XRULES_PRVLG_PRJ_CDE.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//XRULES_PRVLG_PRJ_CDE.CREATE_DATE(DATE)
	private String updateBy;	//XRULES_PRVLG_PRJ_CDE.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//XRULES_PRVLG_PRJ_CDE.UPDATE_DATE(DATE)
	private String projectType; //XRULES_PRVLG_PRJ_CDE.PRVLG_TYPE(VARCHAR2)

	//	private ArrayList<PrivilegeProjectCodeVerificationItemDataM> privilegeProjectCodeItems;	
	private ArrayList<PrivilegeProjectCodeDocDataM> privilegeProjectCodeDocs;
	private ArrayList<PrivilegeProjectCodeProductInsuranceDataM> privilegeProjectCodeProductInsurances;
	private ArrayList<PrivilegeProjectCodeProductCCADataM> privilegeProjectCodeProductCCAs;
	private ArrayList<PrivilegeProjectCodeRccmdPrjCdeDataM> privilegeProjectCodePrjCdes;
	private ArrayList<PrivilegeProjectCodeProductSavingDataM> privilegeProjectCodeProductSavings;

	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public String getVerResultId() {
		return verResultId;
	}
	public void setVerResultId(String verResultId) {
		this.verResultId = verResultId;
	}
	public String getPrvlgPrjCdeId() {
		return prvlgPrjCdeId;
	}
	public void setPrvlgPrjCdeId(String prvlgPrjCdeId) {
		this.prvlgPrjCdeId = prvlgPrjCdeId;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
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
//	public ArrayList<PrivilegeProjectCodeVerificationItemDataM> getPrivilegeProjectCodeItems() {
//		return privilegeProjectCodeItems;
//	}
//	public void setPrivilegeProjectCodeItems(ArrayList<PrivilegeProjectCodeVerificationItemDataM> privilegeProjectCodeItems) {
//		this.privilegeProjectCodeItems = privilegeProjectCodeItems;
//	}	
	
	public ArrayList<PrivilegeProjectCodeDocDataM> getPrivilegeProjectCodeDocs() {
		return privilegeProjectCodeDocs;
	}
	public void setPrivilegeProjectCodeDocs(ArrayList<PrivilegeProjectCodeDocDataM> privilegeProjectCodeDocs) {
		this.privilegeProjectCodeDocs = privilegeProjectCodeDocs;
	}
	public ArrayList<PrivilegeProjectCodeProductInsuranceDataM> getPrivilegeProjectCodeProductInsurances() {
		return privilegeProjectCodeProductInsurances;
	}
	public void setPrivilegeProjectCodeProductInsurances(ArrayList<PrivilegeProjectCodeProductInsuranceDataM> privilegeProjectCodeProductInsurances) {
		this.privilegeProjectCodeProductInsurances = privilegeProjectCodeProductInsurances;
	}
	public ArrayList<PrivilegeProjectCodeProductCCADataM> getPrivilegeProjectCodeProductCCAs() {
		return privilegeProjectCodeProductCCAs;
	}
	public void setPrivilegeProjectCodeProductCCAs(ArrayList<PrivilegeProjectCodeProductCCADataM> privilegeProjectCodeProductCCAs) {
		this.privilegeProjectCodeProductCCAs = privilegeProjectCodeProductCCAs;
	}
	public ArrayList<PrivilegeProjectCodeRccmdPrjCdeDataM> getPrivilegeProjectCodePrjCdes() {
		return privilegeProjectCodePrjCdes;
	}
	public void setPrivilegeProjectCodePrjCdes(ArrayList<PrivilegeProjectCodeRccmdPrjCdeDataM> privilegeProjectCodePrjCdes) {
		this.privilegeProjectCodePrjCdes = privilegeProjectCodePrjCdes;
	}
	public ArrayList<PrivilegeProjectCodeProductSavingDataM> getPrivilegeProjectCodeProductSavings() {
		return privilegeProjectCodeProductSavings;
	}
	public void setPrivilegeProjectCodeProductSavings(ArrayList<PrivilegeProjectCodeProductSavingDataM> privilegeProjectCodeProductSavings) {
		this.privilegeProjectCodeProductSavings = privilegeProjectCodeProductSavings;
	}	
	
	public PrivilegeProjectCodeDocDataM getPrivilegeProjectCodeDoc(int index){
		if(null != privilegeProjectCodeDocs){
			return privilegeProjectCodeDocs.get(index);
		}
		return null;
	}
	public PrivilegeProjectCodeProductCCADataM getPrivilegeProjectCodeProductCCAs(int index){
		if(null != privilegeProjectCodeProductCCAs){
			return privilegeProjectCodeProductCCAs.get(index);
		}
		return null;
	}
}
