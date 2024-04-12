package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class PrivilegeProjectCodeItemDataM implements Serializable,Cloneable{
	public PrivilegeProjectCodeItemDataM(){
		super();
	}
	private String prvlgPrjCdeId;	//XRULES_PRVLG_PRJ_CDE_ITEM.PRVLG_PRJ_CDE_ID(VARCHAR2)
	private String prvlgPrjCdeItemId;	//XRULES_PRVLG_PRJ_CDE_ITEM.PRVLG_PRJ_CDE_ITEM_ID(VARCHAR2)
	private int seq;	//XRULES_PRVLG_PRJ_CDE_ITEM.SEQ(NUMBER)
	private String checkListCode;	//XRULES_PRVLG_PRJ_CDE_ITEM.CHECK_LIST_CODE(VARCHAR2)
	private String createBy;	//XRULES_PRVLG_PRJ_CDE_ITEM.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//XRULES_PRVLG_PRJ_CDE_ITEM.CREATE_DATE(DATE)
	private String updateBy;	//XRULES_PRVLG_PRJ_CDE_ITEM.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//XRULES_PRVLG_PRJ_CDE_ITEM.UPDATE_DATE(DATE)
	private ArrayList<PrivilegeProjectCodeResultDataM> privilegeProjectCodeResults;
	
	public String getPrvlgPrjCdeId() {
		return prvlgPrjCdeId;
	}
	public void setPrvlgPrjCdeId(String prvlgPrjCdeId) {
		this.prvlgPrjCdeId = prvlgPrjCdeId;
	}
	public String getPrvlgPrjCdeItemId() {
		return prvlgPrjCdeItemId;
	}
	public void setPrvlgPrjCdeItemId(String prvlgPrjCdeItemId) {
		this.prvlgPrjCdeItemId = prvlgPrjCdeItemId;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getCheckListCode() {
		return checkListCode;
	}
	public void setCheckListCode(String checkListCode) {
		this.checkListCode = checkListCode;
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
	public ArrayList<PrivilegeProjectCodeResultDataM> getPrivilegeProjectCodeResults() {
		return privilegeProjectCodeResults;
	}
	public void setPrivilegeProjectCodeResults(ArrayList<PrivilegeProjectCodeResultDataM> privilegeProjectCodeResults) {
		this.privilegeProjectCodeResults = privilegeProjectCodeResults;
	}	
	
}
