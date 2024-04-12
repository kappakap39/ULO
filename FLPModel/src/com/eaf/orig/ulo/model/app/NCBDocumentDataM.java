package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;

public class NCBDocumentDataM implements Serializable,Cloneable{
	public NCBDocumentDataM(){
		super();
	}
	private String ncbDocumentId;	//ORIG_NCB_DOCUMENT.NCB_DOCUMENT_ID(VARCHAR2)
	private String personalId;	//ORIG_NCB_DOCUMENT.PERSONAL_ID(VARCHAR2)
	private String imgId;	//ORIG_NCB_DOCUMENT.IMG_ID(VARCHAR2)
	private String documentCode;	//ORIG_NCB_DOCUMENT.DOCUMENT_CODE(VARCHAR2)
	private String createBy;	//ORIG_NCB_DOCUMENT.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_NCB_DOCUMENT.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_NCB_DOCUMENT.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_NCB_DOCUMENT.UPDATE_DATE(DATE)
	
	public String getNcbDocumentId() {
		return ncbDocumentId;
	}
	public void setNcbDocumentId(String ncbDocumentId) {
		this.ncbDocumentId = ncbDocumentId;
	}
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public String getImgId() {
		return imgId;
	}
	public void setImgId(String imgId) {
		this.imgId = imgId;
	}
	public String getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
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
