package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;

public class PrivilegeProjectCodeResultDataM implements Serializable,Cloneable{
	public PrivilegeProjectCodeResultDataM(){
		super();
	}
	private String prvlgPrjCdeItemId;	//XRULES_PRVLG_PRJ_CDE_RESULT.PRVLG_PRJ_CDE_ITEM_ID(VARCHAR2)
	private String prvlgPrjCdeResultId;	//XRULES_PRVLG_PRJ_CDE_RESULT.PRVLG_PRJ_CDE_RESULT_ID(VARCHAR2)
	private String result;	//XRULES_PRVLG_PRJ_CDE_RESULT.RESULT(VARCHAR2)
	private String createBy;	//XRULES_PRVLG_PRJ_CDE_RESULT.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//XRULES_PRVLG_PRJ_CDE_RESULT.CREATE_DATE(DATE)
	private String updateBy;	//XRULES_PRVLG_PRJ_CDE_RESULT.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//XRULES_PRVLG_PRJ_CDE_RESULT.UPDATE_DATE(DATE)
		
	public String getPrvlgPrjCdeItemId() {
		return prvlgPrjCdeItemId;
	}
	public void setPrvlgPrjCdeItemId(String prvlgPrjCdeItemId) {
		this.prvlgPrjCdeItemId = prvlgPrjCdeItemId;
	}
	public String getPrvlgPrjCdeResultId() {
		return prvlgPrjCdeResultId;
	}
	public void setPrvlgPrjCdeResultId(String prvlgPrjCdeResultId) {
		this.prvlgPrjCdeResultId = prvlgPrjCdeResultId;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
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
