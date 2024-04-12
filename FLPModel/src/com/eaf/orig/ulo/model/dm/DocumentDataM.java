package com.eaf.orig.ulo.model.dm;

import java.io.Serializable;
import java.sql.Timestamp;

public class DocumentDataM implements Serializable,Cloneable {
	public DocumentDataM(){
		super();
	}
	private String dmId;	//DM_SUB_DOC.DM_ID(VARCHAR2)
	private String dmSubId;	//DM_SUB_DOC.DM_SUB_ID(VARCHAR2)
	private String status;	//DM_SUB_DOC.STATUS(VARCHAR2)
	private String docType;	//DM_SUB_DOC.DOC_TYPE(VARCHAR2)
	private int noOfPage;	//DM_SUB_DOC.NO_OF_PAGE(NUMBER)
	private String createBy;	//DM_SUB_DOC.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//DM_SUB_DOC.CREATE_DATE(DATE)
	private String updateBy;	//DM_SUB_DOC.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//DM_SUB_DOC.UPDATE_DATE(DATE)
	
	public String getDmId() {
		return dmId;
	}
	public void setDmId(String dmId) {
		this.dmId = dmId;
	}
	public String getDmSubId() {
		return dmSubId;
	}
	public void setDmSubId(String dmSubId) {
		this.dmSubId = dmSubId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public int getNoOfPage() {
		return noOfPage;
	}
	public void setNoOfPage(int noOfPage) {
		this.noOfPage = noOfPage;
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
