package com.eaf.orig.ulo.model.compare;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CompareDataM implements Serializable,Cloneable{
	public CompareDataM(){
		super();
	}	
	private String applicationGroupId;
	private String fieldName;
	private String role;
	private String value;
	private String compareFlag;
	private String srcOfData; //ORIG_COMPARE_DATA.SRC_OF_DATA(VARCHAR2)
	private String refId; //ORIG_COMPARE_DATA.REF_ID(VARCHAR2)
	private String refLevel; //ORIG_COMPARE_DATA.REF_LEVEL(VARCHAR2)
	private String fieldNameType; //ORIG_COMPARE_DATA.FIELD_NAME_TYPE(VARCHAR2)
	private int seq;
	private String createBy;
	private String updateBy;
	private String currentRefId;
	private String oldValue;
	private int lifeCycle = 1;
	private String uniqueId;
	private String uniqueLevel;
	private String configData;
	private String compareDataType;
	
	public static final String MARKER = "_";
	
	public class CompareDataType{
		public static final String DUMMY = "DUMMY";
	}	
	public class RefLevel{
		public static final String APPLICATION_GROUP = "AG";
		public static final String PERSONAL = "P";
		public static final String APPLICATION = "A";
		public static final String PAYMENT_METHOD = "PM";
		public static final String CARD = "C";
		public static final String CARD_INFORMATION = "CI";
		public static final String ADDRESS = "AD";
	}	
	public class UniqueLevel{
		public static final String APPLICATION_GROUP = "AG";
		public static final String PERSONAL = "P";
		public static final String APPLICATION = "A";
		public static final String PAYMENT_METHOD = "PM";
		public static final String ADDRESS = "AD";
		public static final String CARD_INFORMATION = "CI";
	}		
	
	public class UniqueLevelSeq{
		public static final int APPLICATION_GROUP = 1;
		public static final int PERSONAL = 2;
		public static final int APPLICATION = 3;
		public static final int PAYMENT_METHOD = 4;
		public static final int ADDRESS = 5;
		public static final int CARD_INFORMATION = 6;
	}
	public class GroupDataLevel{
		public static final String APPLICATION_GROUP = "APP_GROUP";
		public static final String PERSONAL_APPLICANT = "PERSONAL_APPLICANT";			
		public static final String PERSONAL_SUPPLEMENTATY = "PERSONAL_SUP";	
		public static final String CARD_INFORMATION = "CARD_INFO";
	}
	public class SoruceOfData{
		public static final String TWO_MAKER = "2MAKER";
		public static final String IA_TWO_MAKER = "IA_2MAKER";
		public static final String CIS = "CIS";		
		public static final String PREV_ROLE = "PREV_ROLE";
		public static final String CARD_LINK = "CARD_LINK";
	}
	
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getCompareFlag() {
		return compareFlag;
	}
	public void setCompareFlag(String compareFlag) {
		this.compareFlag = compareFlag;
	}		
	public String getSrcOfData() {
		return srcOfData;
	}
	public void setSrcOfData(String srcOfData) {
		this.srcOfData = srcOfData;
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	public String getRefLevel() {
		return refLevel;
	}
	public void setRefLevel(String refLevel) {
		this.refLevel = refLevel;
	}
	public String getFieldNameType() {
		return fieldNameType;
	}
	public void setFieldNameType(String fieldNameType) {
		this.fieldNameType = fieldNameType;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getCurrentRefId() {
		return currentRefId;
	}
	public void setCurrentRefId(String currentRefId) {
		this.currentRefId = currentRefId;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CompareDataM [applicationGroupId=");
		builder.append(applicationGroupId);
		builder.append(", fieldName=");
		builder.append(fieldName);
		builder.append(", role=");
		builder.append(role);
		builder.append(", value=");
		builder.append(value);
		builder.append(", compareFlag=");
		builder.append(compareFlag);
		builder.append(", srcOfData=");
		builder.append(srcOfData);
		builder.append(", refId=");
		builder.append(refId);
		builder.append(", refLevel=");
		builder.append(refLevel);
		builder.append(", fieldNameType=");
		builder.append(fieldNameType);
		builder.append(", seq=");
		builder.append(seq);
		builder.append(", createBy=");
		builder.append(createBy);
		builder.append(", updateBy=");
		builder.append(updateBy);
		builder.append(", currentRefId=");
		builder.append(currentRefId);
		builder.append(", oldValue=");
		builder.append(oldValue);
		builder.append(", lifeCycle=");
		builder.append(lifeCycle);
		builder.append(", uniqueId=");
		builder.append(uniqueId);
		builder.append(", uniqueLevel=");
		builder.append(uniqueLevel);
		builder.append(", configData=");
		builder.append(configData);
		builder.append(", compareDataType=");
		builder.append(compareDataType);
		builder.append("]");
		return builder.toString();
	}
	public int getLifeCycle() {
		return lifeCycle;
	}
	public void setLifeCycle(int lifeCycle) {
		this.lifeCycle = lifeCycle;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public String getUniqueLevel() {
		return uniqueLevel;
	}
	public void setUniqueLevel(String uniqueLevel) {
		this.uniqueLevel = uniqueLevel;
	}
	public String getConfigData() {
		return configData;
	}
	public void setConfigData(String configData) {
		this.configData = configData;
	}
	public String getCompareDataType() {
		return compareDataType;
	}
	public void setCompareDataType(String compareDataType) {
		this.compareDataType = compareDataType;
	}	
}
