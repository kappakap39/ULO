package com.eaf.service.module.model;

import java.io.Serializable;

public class RegulationDataM implements Serializable,Cloneable{
	private String regulationSubType;
	private String regulationType;
	
	public String getRegulationSubType() {
		return regulationSubType;
	}
	public void setRegulationSubType(String regulationSubType) {
		this.regulationSubType = regulationSubType;
	}
	public String getRegulationType() {
		return regulationType;
	}
	public void setRegulationType(String regulationType) {
		this.regulationType = regulationType;
	}

}
