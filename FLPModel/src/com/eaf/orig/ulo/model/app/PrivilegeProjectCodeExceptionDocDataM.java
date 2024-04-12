package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;

public class PrivilegeProjectCodeExceptionDocDataM implements Serializable,Cloneable{
	public PrivilegeProjectCodeExceptionDocDataM(){
		super();
	}
	private String exceptDocId;	//XRULES_PRVLG_EXCEPTION_DOC.EXCEPT_DOC_ID(VARCHAR2)
	private String prvlgDocId;	//XRULES_PRVLG_EXCEPTION_DOC.PRVLG_DOC_ID(VARCHAR2)
	private String exceptPolicyOth;	//XRULES_PRVLG_EXCEPTION_DOC.EXCEPT_POLICY_OTH(VARCHAR2)
	private String exceptPolicy;	//XRULES_PRVLG_EXCEPTION_DOC.EXCEPT_POLICY(VARCHAR2)
	private String exceptDocFrom;	//XRULES_PRVLG_EXCEPTION_DOC.EXCEPT_DOC_FROM(VARCHAR2)
	private String createBy;	//XRULES_PRVLG_EXCEPTION_DOC.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//XRULES_PRVLG_EXCEPTION_DOC.CREATE_DATE(DATE)
	private String updateBy;	//XRULES_PRVLG_EXCEPTION_DOC.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//XRULES_PRVLG_EXCEPTION_DOC.UPDATE_DATE(DATE)
	
	public String getExceptDocId() {
		return exceptDocId;
	}
	public void setExceptDocId(String exceptDocId) {
		this.exceptDocId = exceptDocId;
	}
	public String getPrvlgDocId() {
		return prvlgDocId;
	}
	public void setPrvlgDocId(String prvlgDocId) {
		this.prvlgDocId = prvlgDocId;
	}
	public String getExceptPolicyOth() {
		return exceptPolicyOth;
	}
	public void setExceptPolicyOth(String exceptPolicyOth) {
		this.exceptPolicyOth = exceptPolicyOth;
	}
	public String getExceptPolicy() {
		return exceptPolicy;
	}
	public void setExceptPolicy(String exceptPolicy) {
		this.exceptPolicy = exceptPolicy;
	}
	public String getExceptDocFrom() {
		return exceptDocFrom;
	}
	public void setExceptDocFrom(String exceptDocFrom) {
		this.exceptDocFrom = exceptDocFrom;
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
