package com.eaf.inf.batch.ulo.debt.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@SuppressWarnings("serial")
public class GenerateDebtReportDataM implements Serializable,Cloneable{
	public GenerateDebtReportDataM(){
		super();
	}
	private String applicationGroupId;
	private String applicationNo;
	private String cisNo;
	private String idNo;
	private String consentRefNo;
	private BigDecimal commercialAmount;
	private BigDecimal consumerAmount;
	private BigDecimal OtherDebtAmount;
	private BigDecimal totalDebtAmount;
	private String productType;
	private String sourceOfApp;
	private BigDecimal verifiedIncome;
	private BigDecimal approveAmount;
	private Date dataDate;
	
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public String getCisNo() {
		return cisNo;
	}
	public void setCisNo(String cisNo) {
		this.cisNo = cisNo;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getConsentRefNo() {
		return consentRefNo;
	}
	public void setConsentRefNo(String consentRefNo) {
		this.consentRefNo = consentRefNo;
	}
	public BigDecimal getCommercialAmount() {
		return commercialAmount;
	}
	public void setCommercialAmount(BigDecimal commercialAmount) {
		this.commercialAmount = commercialAmount;
	}
	public BigDecimal getConsumerAmount() {
		return consumerAmount;
	}
	public void setConsumerAmount(BigDecimal consumerAmount) {
		this.consumerAmount = consumerAmount;
	}
	public BigDecimal getOtherDebtAmount() {
		return OtherDebtAmount;
	}
	public void setOtherDebtAmount(BigDecimal otherDebtAmount) {
		OtherDebtAmount = otherDebtAmount;
	}
	public BigDecimal getTotalDebtAmount() {
		return totalDebtAmount;
	}
	public void setTotalDebtAmount(BigDecimal totalDebtAmount) {
		this.totalDebtAmount = totalDebtAmount;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getSourceOfApp() {
		return sourceOfApp;
	}
	public void setSourceOfApp(String sourceOfApp) {
		this.sourceOfApp = sourceOfApp;
	}
	public BigDecimal getVerifiedIncome() {
		return verifiedIncome;
	}
	public void setVerifiedIncome(BigDecimal verifiedIncome) {
		this.verifiedIncome = verifiedIncome;
	}
	public BigDecimal getApproveAmount() {
		return approveAmount;
	}
	public void setApproveAmount(BigDecimal approveAmount) {
		this.approveAmount = approveAmount;
	}
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public Date getDataDate() {
		return dataDate;
	}
	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}
	
}
