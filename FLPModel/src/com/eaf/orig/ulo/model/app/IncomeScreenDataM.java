package com.eaf.orig.ulo.model.app;

import java.io.Serializable;

public class IncomeScreenDataM implements Serializable,Cloneable{
	public IncomeScreenDataM(){
		super();
	}
	 private String incomeScreenType;
	public String getIncomeScreenType() {
		return incomeScreenType;
	}
	public void setIncomeScreenType(String incomeScreenType) {
		this.incomeScreenType = incomeScreenType;
	}	 
}
