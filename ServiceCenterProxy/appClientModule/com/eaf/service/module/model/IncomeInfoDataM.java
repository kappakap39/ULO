package com.eaf.service.module.model;

import java.io.Serializable;
import java.util.ArrayList;

public class IncomeInfoDataM implements Serializable,Cloneable{
	private String incomeType;
	private ArrayList<PreviousIncomeDataM> previousIncomes;
	
	public String getIncomeType() {
		return incomeType;
	}
	public void setIncomeType(String incomeType) {
		this.incomeType = incomeType;
	}
	public ArrayList<PreviousIncomeDataM> getPreviousIncomeM() {
		return previousIncomes;
	}
	public void setPreviousIncomeM(ArrayList<PreviousIncomeDataM> previousIncomes) {
		this.previousIncomes = previousIncomes;
	} 
}
