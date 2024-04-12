package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;

public class CapportLoanDataM  implements Serializable,Cloneable
{
	
    protected String capportId;
    protected String amountCapportId;
    protected BigDecimal capAmount;
    protected BigDecimal grantedAmount;
    protected BigDecimal remainingAmount;
    protected String applicationCapportId;
    protected BigDecimal capApplication;
    protected BigDecimal grantedApplication;
    protected BigDecimal remainingApplication;
    
	public String getCapportId() {
		return capportId;
	}
	public void setCapportId(String capportId) {
		this.capportId = capportId;
	}
	public String getAmountCapportId() {
		return amountCapportId;
	}
	public void setAmountCapportId(String amountCapportId) {
		this.amountCapportId = amountCapportId;
	}
	public BigDecimal getCapAmount() {
		return capAmount;
	}
	public void setCapAmount(BigDecimal capAmount) {
		this.capAmount = capAmount;
	}
	public BigDecimal getGrantedAmount() {
		return grantedAmount;
	}
	public void setGrantedAmount(BigDecimal grantedAmount) {
		this.grantedAmount = grantedAmount;
	}
	public BigDecimal getRemainingAmount() {
		return remainingAmount;
	}
	public void setRemainingAmount(BigDecimal remainingAmount) {
		this.remainingAmount = remainingAmount;
	}
	public String getApplicationCapportId() {
		return applicationCapportId;
	}
	public void setApplicationCapportId(String applicationCapportId) {
		this.applicationCapportId = applicationCapportId;
	}
	public BigDecimal getCapApplication() {
		return capApplication;
	}
	public void setCapApplication(BigDecimal capApplication) {
		this.capApplication = capApplication;
	}
	public BigDecimal getGrantedApplication() {
		return grantedApplication;
	}
	public void setGrantedApplication(BigDecimal grantedApplication) {
		this.grantedApplication = grantedApplication;
	}
	public BigDecimal getRemainingApplication() {
		return remainingApplication;
	}
	public void setRemainingApplication(BigDecimal remainingApplication) {
		this.remainingApplication = remainingApplication;
	}
	
}