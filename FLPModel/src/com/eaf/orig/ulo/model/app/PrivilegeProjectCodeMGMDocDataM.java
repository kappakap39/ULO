package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;

public class PrivilegeProjectCodeMGMDocDataM implements Serializable,Cloneable{
	public PrivilegeProjectCodeMGMDocDataM(){
		super();
	}
	private String privlgMgmDocId;	//XRULES_PRVLG_MGM_DOC.PRIVLG_MGM_DOC_ID(VARCHAR2)
	private String prvlgDocId;	//XRULES_PRVLG_MGM_DOC.PRVLG_DOC_ID(VARCHAR2)
	private String referCardHolderName;	//XRULES_PRVLG_MGM_DOC.REFER_CARD_HOLDER_NAME(VARCHAR2)
	private String referCardNo;	//XRULES_PRVLG_MGM_DOC.REFER_CARD_NO(VARCHAR2)
	private String createBy;	//XRULES_PRVLG_MGM_DOC.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//XRULES_PRVLG_MGM_DOC.CREATE_DATE(DATE)
	private String updateBy;	//XRULES_PRVLG_MGM_DOC.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//XRULES_PRVLG_MGM_DOC.UPDATE_DATE(DATE)
	
	public String getPrivlgMgmDocId() {
		return privlgMgmDocId;
	}
	public void setPrivlgMgmDocId(String privlgMgmDocId) {
		this.privlgMgmDocId = privlgMgmDocId;
	}
	public String getPrvlgDocId() {
		return prvlgDocId;
	}
	public void setPrvlgDocId(String prvlgDocId) {
		this.prvlgDocId = prvlgDocId;
	}
	public String getReferCardHolderName() {
		return referCardHolderName;
	}
	public void setReferCardHolderName(String referCardHolderName) {
		this.referCardHolderName = referCardHolderName;
	}
	public String getReferCardNo() {
		return referCardNo;
	}
	public void setReferCardNo(String referCardNo) {
		this.referCardNo = referCardNo;
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
