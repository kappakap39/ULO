package com.eaf.inf.batch.ulo.report.model;

public class PeriodDataM {
	private String periodDesc;
	public String getPeriodDesc() {
		return periodDesc;
	}
	public void setPeriodDesc(String periodDesc) {
		this.periodDesc = periodDesc;
	}
	private String period;
	private String periodCon;
	private String sortField;
	
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getPeriodCon() {
		return periodCon;
	}
	public void setPeriodCon(String periodCon) {
		this.periodCon = periodCon;
	}
	public String getSortField() {
		return sortField;
	}
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

}
