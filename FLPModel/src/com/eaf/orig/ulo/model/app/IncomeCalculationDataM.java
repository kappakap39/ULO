package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;

public class IncomeCalculationDataM implements Serializable,Cloneable {
	
	public IncomeCalculationDataM() {
		super();
	}
	
	private String incomeSourceType;
	private BigDecimal verifiedIncome;
	private String typeOfFin;
	private BigDecimal totalVerifiedIncome;
	private BigDecimal existingCreditLine;
	private String verifiedIncomeType;
	private String verifiedIncomeCalculation;
	
	public String getIncomeSourceType() {
		return incomeSourceType;
	}
	public void setIncomeSourceType(String incomeSourceType) {
		this.incomeSourceType = incomeSourceType;
	}
	public BigDecimal getVerifiedIncome() {
		return verifiedIncome;
	}
	public void setVerifiedIncome(BigDecimal verifiedIncome) {
		this.verifiedIncome = verifiedIncome;
	}
	public String getTypeOfFin() {
		return typeOfFin;
	}
	public void setTypeOfFin(String typeOfFin) {
		this.typeOfFin = typeOfFin;
	}
	public BigDecimal getTotalVerifiedIncome() {
		return totalVerifiedIncome;
	}
	public void setTotalVerifiedIncome(BigDecimal totalVerifiedIncome) {
		this.totalVerifiedIncome = totalVerifiedIncome;
	}
	public BigDecimal getExistingCreditLine() {
		return existingCreditLine;
	}
	public void setExistingCreditLine(BigDecimal existingCreditLine) {
		this.existingCreditLine = existingCreditLine;
	}
	public String getVerifiedIncomeType() {
		return verifiedIncomeType;
	}
	public void setVerifiedIncomeType(String verifiedIncomeType) {
		this.verifiedIncomeType = verifiedIncomeType;
	}
	public String getVerifiedIncomeCalculation() {
		return verifiedIncomeCalculation;
	}
	public void setVerifiedIncomeCalculation(String verifiedIncomeCalculation) {
		this.verifiedIncomeCalculation = verifiedIncomeCalculation;
	}

}
