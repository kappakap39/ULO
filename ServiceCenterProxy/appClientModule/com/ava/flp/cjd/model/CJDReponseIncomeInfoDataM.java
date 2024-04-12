package com.ava.flp.cjd.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class CJDReponseIncomeInfoDataM implements Serializable,Cloneable{
	public CJDReponseIncomeInfoDataM(){
		super();
	}
	private String incomeSource;
	private BigDecimal verifiedIncome;
	
	public String getIncomeSource() {
		return incomeSource;
	}
	public void setIncomeSource(String incomeSource) {
		this.incomeSource = incomeSource;
	}
	public BigDecimal getVerifiedIncome() {
		return verifiedIncome;
	}
	public void setVerifiedIncome(BigDecimal verifiedIncome) {
		this.verifiedIncome = verifiedIncome;
	}
	
	
}
