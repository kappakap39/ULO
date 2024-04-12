package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.util.HashMap;

import com.eaf.orig.ulo.model.compare.CompareDataM;

public class ComparisonGroupDataM implements Serializable,Cloneable{
	public ComparisonGroupDataM(){
		super();
	}
	public ComparisonGroupDataM(String srcOfData){
		this.srcOfData = srcOfData;
	}
	private String roleId;
	private String srcOfData;
	private HashMap<String,CompareDataM> comparisonFields = new HashMap<String, CompareDataM>();
	public String getSrcOfData() {
		return srcOfData;
	}
	public void setSrcOfData(String srcOfData) {
		this.srcOfData = srcOfData;
	}
	public HashMap<String, CompareDataM> getComparisonFields() {
		return comparisonFields;
	}
	public void setComparisonFields(HashMap<String, CompareDataM> comparisonFields) {
		this.comparisonFields = comparisonFields;
	}	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ComparisonGroupDataM [srcOfData=");
		builder.append(srcOfData);
		builder.append(", comparisonFields=");
		builder.append(comparisonFields);
		builder.append("]");
		return builder.toString();
	}	
	
}
