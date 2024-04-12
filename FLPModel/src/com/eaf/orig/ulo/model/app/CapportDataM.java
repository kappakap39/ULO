package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;

public class CapportDataM  implements Serializable,Cloneable{
	public CapportDataM()
	{
		super();
	}
	
	private String capPortName;
	private BigDecimal approveAmount;
	private BigDecimal approveNumberOfApp;
	private String grantAmountCapName;
	private String grantNumberCapName;
	
	public String getCapPortName() {
		return capPortName;
	}
	public void setCapPortName(String capPortName) {
		this.capPortName = capPortName;
	}
	public BigDecimal getApproveAmount() {
		return approveAmount;
	}
	public void setApproveAmount(BigDecimal approveAmount) {
		this.approveAmount = approveAmount;
	}
	public BigDecimal getApproveNumberOfApp() {
		return approveNumberOfApp;
	}
	public void setApproveNumberOfApp(BigDecimal approveNumberOfApp) {
		this.approveNumberOfApp = approveNumberOfApp;
	}
	public String getGrantAmountCapName() {
		return grantAmountCapName;
	}
	public void setGrantAmountCapName(String grantAmountCapName) {
		this.grantAmountCapName = grantAmountCapName;
	}
	public String getGrantNumberCapName() {
		return grantNumberCapName;
	}
	public void setGrantNumberCapName(String grantNumberCapName) {
		this.grantNumberCapName = grantNumberCapName;
	}
	
}
