package com.eaf.service.module.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PreviousIncomeDataM implements Serializable,Cloneable{
	private BigDecimal income;
	private Date incomeDate;
	
	public BigDecimal getIncome() {
		return income;
	}
	public void setIncome(BigDecimal income) {
		this.income = income;
	}
	public Date getIncomeDate() {
		return incomeDate;
	}
	public void setIncomeDate(Date incomeDate) {
		this.incomeDate = incomeDate;
	}
}
