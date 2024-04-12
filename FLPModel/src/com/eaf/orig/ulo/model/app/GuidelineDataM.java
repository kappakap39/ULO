package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;

public class GuidelineDataM implements Serializable,Cloneable{
	public GuidelineDataM(){
		super();
	}
	private String guideLineDataId;	//XRULES_GUIDELINE_DATA.GUIDELINE_DATA_ID(VARCHAR2)
	private String orPolicyRulesDetailId;	//XRULES_GUIDELINE_DATA.OR_POLICY_RULES_DETAIL_ID(VARCHAR2)
	private String name;	//XRULES_GUIDELINE_DATA.NAME(VARCHAR2)
	private String value;	//XRULES_GUIDELINE_DATA.VALUE(VARCHAR2)
	private String createBy;	//XRULES_GUIDELINE_DATA.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//XRULES_GUIDELINE_DATA.CREATE_DATE(DATE)
	private String updateBy;	//XRULES_GUIDELINE_DATA.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//XRULES_GUIDELINE_DATA.UPDATE_DATE(DATE)
	
	public String getOrPolicyRulesDetailId() {
		return orPolicyRulesDetailId;
	}
	public void setOrPolicyRulesDetailId(String orPolicyRulesDetailId) {
		this.orPolicyRulesDetailId = orPolicyRulesDetailId;
	}
	public String getGuideLineDataId() {
		return guideLineDataId;
	}
	public void setGuideLineDataId(String guideLineDataId) {
		this.guideLineDataId = guideLineDataId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
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
}
