package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class DocumentRelationDataM implements Serializable,Cloneable{
	public DocumentRelationDataM(){
		super();
	}
	private String docCheckListId;	//ORIG_DOC_RELATION.DOC_CHECK_LIST_ID(VARCHAR2)
	private String refId;	//ORIG_DOC_RELATION.REF_ID(VARCHAR2)
	private String refLevel;	//ORIG_DOC_RELATION.REF_LEVEL(VARCHAR2)
	private String createBy;	//ORIG_DOC_RELATION.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_DOC_RELATION.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_DOC_RELATION.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_DOC_RELATION.UPDATE_DATE(DATE)
	
	private String listDocCode;
	
	public String getDocCheckListId() {
		return docCheckListId;
	}
	public void setDocCheckListId(String docCheckListId) {
		this.docCheckListId = docCheckListId;
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	public String getRefLevel() {
		return refLevel;
	}
	public void setRefLevel(String refLevel) {
		this.refLevel = refLevel;
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
	public String getListDocCode() {
		return listDocCode;
	}
	public void setListDocCode(String listDocCode) {
		this.listDocCode = listDocCode;
	}
	
}
