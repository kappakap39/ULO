package com.eaf.inf.batch.ulo.pega.model;

import java.io.Serializable;

import org.jsefa.csv.annotation.CsvDataType;
import org.jsefa.csv.annotation.CsvField;

@CsvDataType()
public class CSVContentDataM implements Serializable, Cloneable{
	public CSVContentDataM () {
		super();
	}
	
	@CsvField(pos = 1)
	private  String CaseID;
	
	@CsvField(pos = 2)
	private  String isClose;
	
	@CsvField(pos = 3)
	private  String isVetoEligible;

	public String getCaseID() {
		return CaseID;
	}

	public void setCaseID(String caseID) {
		CaseID = caseID;
	}

	public String getIsClose() {
		return isClose;
	}

	public void setIsClose(String isClose) {
		this.isClose = isClose;
	}

	public String getIsVetoEligible() {
		return isVetoEligible;
	}

	public void setIsVetoEligible(String isVetoEligible) {
		this.isVetoEligible = isVetoEligible;
	}

}
