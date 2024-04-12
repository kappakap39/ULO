package com.eaf.orig.ulo.pl.app.utility;

import java.io.Serializable;

public class MandatoryDataM implements Serializable,Cloneable{
	public MandatoryDataM(){
		super();
	}
	private String subFormID;
	private String fieldName;
	private int logic;
	
	public String getSubFormID() {
		return subFormID;
	}
	public void setSubFormID(String subFormID) {
		this.subFormID = subFormID;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public int getLogic() {
		return logic;
	}
	public void setLogic(int logic) {
		this.logic = logic;
	}
	
}
