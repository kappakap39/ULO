package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;

public class SpecialAdditionalServiceMapDataM implements Serializable,Cloneable{
	public SpecialAdditionalServiceMapDataM(){
		super();
	}
	private String serviceId; //ORIG_ADDITIONAL_SERVICE_MAP.SERVICE_ID(VARCHAR2)
	private String loanId; //ORIG_ADDITIONAL_SERVICE_MAP.LOAN_ID(VARCHAR2)
	private String createBy;	//ORIG_ADDITIONAL_SERVICE_MAP.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_ADDITIONAL_SERVICE_MAP.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_ADDITIONAL_SERVICE_MAP.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_ADDITIONAL_SERVICE_MAP.UPDATE_DATE(DATE)
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
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
