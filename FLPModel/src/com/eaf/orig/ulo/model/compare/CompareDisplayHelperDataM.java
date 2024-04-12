package com.eaf.orig.ulo.model.compare;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;


@SuppressWarnings("serial")
public class CompareDisplayHelperDataM extends CompareDataM implements Serializable,Cloneable{
	public CompareDisplayHelperDataM(){
		super();
	}
	public static final String MISSING_FIELD = "MISSING_FIELD";
	public static final String REKEY_HEADER = "REKEY_HEADER";
	public static final String PAYMENT_METHOD = "PAYMENT_METHOD";
	public static final String PAYMENT_RATIO = "PAYMENT_RATIO";
	public static final String PAYMENT_ACCOUNT_NO = "ACCOUNT_NO";
	
	public CompareDisplayHelperDataM(String refLevel){
		this.refLevel = refLevel;
	}
	private String refLevel;
	private HashMap<String, ArrayList<CompareDataM>> comparisonFields;
	public String getRefLevel() {
		return refLevel;
	}
	public void setRefLevel(String refLevel) {
		this.refLevel = refLevel;
	}
	public HashMap<String, ArrayList<CompareDataM>> getComparisonFields() {
		return comparisonFields;
	}
	public ArrayList<CompareDataM> getComparisonFields(String keyId) {
		return comparisonFields.get(keyId);
	}
	public Set<String> getComparisonKeys() {
		if(comparisonFields != null) {
			return comparisonFields.keySet();
		}
		return null;
	}
	
	public void setComparisonFields(HashMap<String, ArrayList<CompareDataM>> comparisonFields) {
		this.comparisonFields = comparisonFields;
	}
	public void addComparisonFields(String keyId, CompareDataM comparisonField) {
		if(comparisonFields == null) {
			comparisonFields = new HashMap<String, ArrayList<CompareDataM>>();
		}
		if(CompareDataM.RefLevel.ADDRESS.equals(refLevel)) {
			keyId = comparisonField.getCurrentRefId();
		}
		ArrayList<CompareDataM> compareListForKey = comparisonFields.get(keyId);
		if(compareListForKey == null) {
			compareListForKey = new ArrayList<CompareDataM>();
			comparisonFields.put(keyId, compareListForKey);
			
			CompareDataM headerField = new CompareDataM();
			headerField.setRefLevel(refLevel);
			headerField.setRefId(comparisonField.getRefId());
			headerField.setCurrentRefId(comparisonField.getCurrentRefId());
			headerField.setFieldNameType(REKEY_HEADER);
			headerField.setSeq(0);
			compareListForKey.add(headerField);
		} 
		compareListForKey.add(comparisonField);
	}
	public void addComparisonFields(String keyId, ArrayList<CompareDataM> comparisonFieldList) {
		if(comparisonFieldList == null || comparisonFieldList.isEmpty()) {
			return;
		}
		if(comparisonFields == null) {
			comparisonFields = new HashMap<String, ArrayList<CompareDataM>>();
		}
		ArrayList<CompareDataM> compareListForKey = comparisonFields.get(keyId);
		if(compareListForKey == null) {
			CompareDataM listData = comparisonFieldList.get(0);
			CompareDataM headerField = new CompareDataM();
			headerField.setRefLevel(refLevel);
			headerField.setRefId(listData.getRefId());
			headerField.setCurrentRefId(listData.getCurrentRefId());
			headerField.setFieldNameType(REKEY_HEADER);
			headerField.setSeq(0);
			comparisonFieldList.add(0,headerField);
			
			comparisonFields.put(keyId, comparisonFieldList);
		} else {
			compareListForKey.addAll(comparisonFieldList);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CompareSensitiveDisplayDataM [refLevel=");
		builder.append(refLevel);
		builder.append(", comparisonFields=");
		builder.append(comparisonFields);
		builder.append("]");
		return builder.toString();
	}
	public void addMissingFields(CompareDataM lastRoleDataM) {
		if(comparisonFields == null) {
			comparisonFields = new HashMap<String, ArrayList<CompareDataM>>();
		}
		String keyId = lastRoleDataM.getRefId();
		if(CompareDataM.RefLevel.ADDRESS.equals(refLevel) || CompareDataM.RefLevel.CARD.equals(refLevel)) {
			keyId = lastRoleDataM.getCurrentRefId();
		}
		ArrayList<CompareDataM> compareListForKey = comparisonFields.get(keyId);
		if(compareListForKey == null) {
			compareListForKey = new ArrayList<CompareDataM>();
			comparisonFields.put(keyId, compareListForKey);
			
			CompareDataM headerField = new CompareDataM();
			headerField.setRefLevel(refLevel);
			headerField.setRefId(lastRoleDataM.getRefId());
			headerField.setCurrentRefId(lastRoleDataM.getCurrentRefId());
			headerField.setFieldNameType(REKEY_HEADER);
			headerField.setSeq(0);
			compareListForKey.add(headerField);
			
			CompareDataM missingData = new CompareDataM();
			missingData.setRefLevel(lastRoleDataM.getRefLevel());
			missingData.setFieldNameType(CompareDisplayHelperDataM.MISSING_FIELD);
			missingData.setRefId(lastRoleDataM.getRefId());
			compareListForKey.add(missingData);
		}
		
	}
	
	public void sortList(Comparator<CompareDataM> comparator) {
		if(comparisonFields != null) {
			Set<String> personalInfoFields = comparisonFields.keySet();
			for(String refId : personalInfoFields) {
				ArrayList<CompareDataM> comparisonList = comparisonFields.get(refId);
				if(comparisonList != null && !comparisonList.isEmpty()) {
					Collections.sort(comparisonList, comparator);
				}
			}
		}
	}
	
}
