package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class PrivilegeProjectCodeDocDataM implements Serializable,Cloneable{
	public PrivilegeProjectCodeDocDataM(){
		super();
	}
	private String prvlgDocId;	//XRULES_PRVLG_DOC.PRVLG_DOC_ID(VARCHAR2)
	private String prvlgPrjCdeId;	//XRULES_PRVLG_DOC.PRVLG_PRJ_CDE_ID(VARCHAR2)
	private String docType;	//XRULES_PRVLG_DOC.DOC_TYPE(VARCHAR2)
	private String createBy;	//XRULES_PRVLG_DOC.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//XRULES_PRVLG_DOC.CREATE_DATE(DATE)
	private String updateBy;	//XRULES_PRVLG_DOC.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//XRULES_PRVLG_DOC.UPDATE_DATE(DATE)
	
	private ArrayList<PrivilegeProjectCodeKassetDocDataM> privilegeProjectCodeKassetDocs;
	private ArrayList<PrivilegeProjectCodeMGMDocDataM> privilegeProjectCodeMGMDocs;
	private ArrayList<PrivilegeProjectCodeExceptionDocDataM> privilegeProjectCodeExceptionDocs;
	private ArrayList<PrivilegeProjectCodeTransferDocDataM> privilegeProjectCodeTransferDocs;
	private ArrayList<PrivilegeProjectCodeProductTradingDataM> privilegeProjectCodeProductTradings;
	

	public String getPrvlgDocId() {
		return prvlgDocId;
	}
	public void setPrvlgDocId(String prvlgDocId) {
		this.prvlgDocId = prvlgDocId;
	}
	public String getPrvlgPrjCdeId() {
		return prvlgPrjCdeId;
	}
	public void setPrvlgPrjCdeId(String prvlgPrjCdeId) {
		this.prvlgPrjCdeId = prvlgPrjCdeId;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
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
	
	
	public ArrayList<PrivilegeProjectCodeKassetDocDataM> getPrivilegeProjectCodeKassetDocs() {
		return privilegeProjectCodeKassetDocs;
	}
	public void setPrivilegeProjectCodeKassetDocs(ArrayList<PrivilegeProjectCodeKassetDocDataM> privilegeProjectCodeKassetDocs) {
		this.privilegeProjectCodeKassetDocs = privilegeProjectCodeKassetDocs;
	}
	public ArrayList<PrivilegeProjectCodeMGMDocDataM> getPrivilegeProjectCodeMGMDocs() {
		return privilegeProjectCodeMGMDocs;
	}
	public void setPrivilegeProjectCodeMGMDocs(ArrayList<PrivilegeProjectCodeMGMDocDataM> privilegeProjectCodeMGMDocs) {
		this.privilegeProjectCodeMGMDocs = privilegeProjectCodeMGMDocs;
	}
	public ArrayList<PrivilegeProjectCodeExceptionDocDataM> getPrivilegeProjectCodeExceptionDocs() {
		return privilegeProjectCodeExceptionDocs;
	}
	public void setPrivilegeProjectCodeExceptionDocs(ArrayList<PrivilegeProjectCodeExceptionDocDataM> privilegeProjectCodeExceptionDocs) {
		this.privilegeProjectCodeExceptionDocs = privilegeProjectCodeExceptionDocs;
	}
	public ArrayList<PrivilegeProjectCodeTransferDocDataM> getPrivilegeProjectCodeTransferDocs() {
		return privilegeProjectCodeTransferDocs;
	}
	public void setPrivilegeProjectCodeTransferDocs(ArrayList<PrivilegeProjectCodeTransferDocDataM> privilegeProjectCodeTransferDocs) {
		this.privilegeProjectCodeTransferDocs = privilegeProjectCodeTransferDocs;
	}	
	
	public PrivilegeProjectCodeMGMDocDataM getPrivilegeProjectCodeMGMDoc(int index){
		if(null != privilegeProjectCodeMGMDocs){
			return privilegeProjectCodeMGMDocs.get(index);
		}
		return null;
	}
	public PrivilegeProjectCodeKassetDocDataM getPrivilegeProjectCodeKassetDoc(int index){
		if(null != privilegeProjectCodeKassetDocs){
			return privilegeProjectCodeKassetDocs.get(index);
		}
		return null;
	}
	public PrivilegeProjectCodeExceptionDocDataM getPrivilegeProjectCodeExceptionDoc(int index){
		if(null != privilegeProjectCodeExceptionDocs){
			return privilegeProjectCodeExceptionDocs.get(index);
		}
		return null;
	}
	public ArrayList<PrivilegeProjectCodeProductTradingDataM> getPrivilegeProjectCodeProductTradings() {
		return privilegeProjectCodeProductTradings;
	}
	public void setPrivilegeProjectCodeProductTradings(
			ArrayList<PrivilegeProjectCodeProductTradingDataM> privilegeProjectCodeProductTradings) {
		this.privilegeProjectCodeProductTradings = privilegeProjectCodeProductTradings;
	}
	public PrivilegeProjectCodeProductTradingDataM getPrivilegeProjectCodeProductTradings(int index){
		if(null != privilegeProjectCodeProductTradings){
			return privilegeProjectCodeProductTradings.get(index);
		}
		return null;
	}
	
}
