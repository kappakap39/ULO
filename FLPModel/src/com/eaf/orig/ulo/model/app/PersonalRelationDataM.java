package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;

public class PersonalRelationDataM implements Serializable,Cloneable{
	public PersonalRelationDataM(){
		super();
	}
	private String personalId;	//ORIG_PERSONAL_RELATION.PERSONAL_ID(VARCHAR2)
	private String refId;	//ORIG_PERSONAL_RELATION.REF_ID(VARCHAR2)
	private String relationLevel;	//ORIG_PERSONAL_RELATION.RELATION_LEVEL(VARCHAR2)
	private String personalType;	//ORIG_PERSONAL_RELATION.PERSONAL_TYPE(VARCHAR2)
	private String createBy;	//ORIG_PERSONAL_RELATION.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_PERSONAL_RELATION.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_PERSONAL_RELATION.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_PERSONAL_RELATION.UPDATE_DATE(DATE)
	
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	public String getRelationLevel() {
		return relationLevel;
	}
	public void setRelationLevel(String relationLevel) {
		this.relationLevel = relationLevel;
	}
	public String getPersonalType() {
		return personalType;
	}
	public void setPersonalType(String personalType) {
		this.personalType = personalType;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PersonalRelationDataM [personalId=");
		builder.append(personalId);
		builder.append(", refId=");
		builder.append(refId);
		builder.append(", relationLevel=");
		builder.append(relationLevel);
		builder.append(", personalType=");
		builder.append(personalType);
		builder.append(", createBy=");
		builder.append(createBy);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", updateBy=");
		builder.append(updateBy);
		builder.append(", updateDate=");
		builder.append(updateDate);
		builder.append("]");
		return builder.toString();
	}
	
}
