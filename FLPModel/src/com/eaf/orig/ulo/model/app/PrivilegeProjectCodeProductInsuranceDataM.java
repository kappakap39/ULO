package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class PrivilegeProjectCodeProductInsuranceDataM implements Serializable,Cloneable{
	public PrivilegeProjectCodeProductInsuranceDataM(){
		super();
	}
	private String productInsuranceId;	//XRULES_PRVLG_PRODUCT_INSURANCE.PRODUCT_INSURANCE_ID(VARCHAR2)
	private String prvlgPrjCdeId;	//XRULES_PRVLG_PRODUCT_INSURANCE.PRVLG_PRJ_CDE_ID(VARCHAR2)
	private BigDecimal premium;	//XRULES_PRVLG_PRODUCT_INSURANCE.PREMIUM(NUMBER)
	private String policyNo;	//XRULES_PRVLG_PRODUCT_INSURANCE.POLICY_NO(VARCHAR2)
	private String insuranceType;	//XRULES_PRVLG_PRODUCT_INSURANCE.INSURANCE_TYPE(VARCHAR2)
	private String cisno;	//XRULES_PRVLG_PRODUCT_INSURANCE.CIS_NO(VARCHAR2)
	private String relationTranfer;	//XRULES_PRVLG_PRODUCT_INSURANCE.RELATIONSHIP(VARCHAR2)
	private String createBy;	//XRULES_PRVLG_PRODUCT_INSURANCE.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//XRULES_PRVLG_PRODUCT_INSURANCE.CREATE_DATE(DATE)
	private String updateBy;	//XRULES_PRVLG_PRODUCT_INSURANCE.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//XRULES_PRVLG_PRODUCT_INSURANCE.UPDATE_DATE(DATE)
	private int seq;
	public String getProductInsuranceId() {
		return productInsuranceId;
	}
	public void setProductInsuranceId(String productInsuranceId) {
		this.productInsuranceId = productInsuranceId;
	}
	public String getPrvlgPrjCdeId() {
		return prvlgPrjCdeId;
	}
	public void setPrvlgPrjCdeId(String prvlgPrjCdeId) {
		this.prvlgPrjCdeId = prvlgPrjCdeId;
	}
	public BigDecimal getPremium() {
		return premium;
	}
	public void setPremium(BigDecimal premium) {
		this.premium = premium;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getInsuranceType() {
		return insuranceType;
	}
	public void setInsuranceType(String insuranceType) {
		this.insuranceType = insuranceType;
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
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getCisno() {
		return cisno;
	}
	public void setCisno(String cisno) {
		this.cisno = cisno;
	}
	public String getRelationTranfer() {
		return relationTranfer;
	}
	public void setRelationTranfer(String relationTranfer) {
		this.relationTranfer = relationTranfer;
	}
}
