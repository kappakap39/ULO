package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;

public class PrivilegeProjectCodeRccmdPrjCdeDataM implements Serializable,Cloneable{
	public PrivilegeProjectCodeRccmdPrjCdeDataM(){
		super();
	}
	private String prvlgPrjCdeId;	//XRULES_PRVLG_RCCMD_PRJ_CDE.PRVLG_PRJ_CDE_ID(VARCHAR2)
	private String rccmdPrjCdeId;	//XRULES_PRVLG_RCCMD_PRJ_CDE.RCCMD_PRJ_CDE_ID(VARCHAR2)
	private String projectCode;	//XRULES_PRVLG_RCCMD_PRJ_CDE.PROJECT_CODE(VARCHAR2)
	private String projectCodeDesc;	//XRULES_PRVLG_RCCMD_PRJ_CDE.PROJECT_CODE_DESC(VARCHAR2)
	private String createBy;	//XRULES_PRVLG_RCCMD_PRJ_CDE.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//XRULES_PRVLG_RCCMD_PRJ_CDE.CREATE_DATE(DATE)
	private String updateBy;	//XRULES_PRVLG_RCCMD_PRJ_CDE.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//XRULES_PRVLG_RCCMD_PRJ_CDE.UPDATE_DATE(DATE)
	
	public String getPrvlgPrjCdeId() {
		return prvlgPrjCdeId;
	}
	public void setPrvlgPrjCdeId(String prvlgPrjCdeId) {
		this.prvlgPrjCdeId = prvlgPrjCdeId;
	}
	public String getRccmdPrjCdeId() {
		return rccmdPrjCdeId;
	}
	public void setRccmdPrjCdeId(String rccmdPrjCdeId) {
		this.rccmdPrjCdeId = rccmdPrjCdeId;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getProjectCodeDesc() {
		return projectCodeDesc;
	}
	public void setProjectCodeDesc(String projectCodeDesc) {
		this.projectCodeDesc = projectCodeDesc;
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
