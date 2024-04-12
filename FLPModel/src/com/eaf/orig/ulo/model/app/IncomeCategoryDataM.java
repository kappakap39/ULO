package com.eaf.orig.ulo.model.app;

import java.io.Serializable;

@SuppressWarnings("serial")
public class IncomeCategoryDataM implements Cloneable, Serializable {
	private int seq;
	private String incomeId;
	private String compareFlag; //COMPARE_FLAG(VARCHAR2)
	private String incomeType;
	
	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getIncomeId() {
		return incomeId;
	}

	public void setIncomeId(String incomeId) {
		this.incomeId = incomeId;
	}
	
	public String getCompareFlag() {
		return compareFlag;
	}

	public void setCompareFlag(String compareFlag) {
		this.compareFlag = compareFlag;
	}

	public String getIncomeType() {
		return incomeType;
	}

	public void setIncomeType(String incomeType) {
		this.incomeType = incomeType;
	}

	public String getId(){
		return null;
	}

}
