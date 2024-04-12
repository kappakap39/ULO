package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class PrivilegeProjectCodeProductTradingDataM implements Serializable,Cloneable{
	public PrivilegeProjectCodeProductTradingDataM(){
		super();
	}
//	PRVLG_DOC_ID
	private String privlgTradingDocId; //XRULES_PRVLG_TRADING_DOC.PRIVLG_TRADING_DOC_ID(VARCHAR2)
	private String prvlgDocId; //XRULES_PRVLG_TRADING_DOC.PRVLG_DOC_ID(VARCHAR2)
	private BigDecimal propertyValue; //XRULES_PRVLG_TRADING_DOC.PROPERTY_VALUE(VARCHAR2)
	private String createBy;	//XRULES_PRVLG_TRADING_DOC.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//XRULES_PRVLG_TRADING_DOC.CREATE_DATE(DATE)
	private String updateBy;	//XRULES_PRVLG_TRADING_DOC.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//XRULES_PRVLG_TRADING_DOC.UPDATE_DATE(DATE)
	
	
	
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
	public String getPrivlgTradingDocId() {
		return privlgTradingDocId;
	}
	public void setPrivlgTradingDocId(String privlgTradingDocId) {
		this.privlgTradingDocId = privlgTradingDocId;
	}
	public String getPrvlgDocId() {
		return prvlgDocId;
	}
	public void setPrvlgDocId(String prvlgDocId) {
		this.prvlgDocId = prvlgDocId;
	}
	public BigDecimal getPropertyValue() {
		return propertyValue;
	}
	public void setPropertyValue(BigDecimal propertyValue) {
		this.propertyValue = propertyValue;
	}
	
}
