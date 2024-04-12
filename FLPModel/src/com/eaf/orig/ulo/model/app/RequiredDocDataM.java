package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class RequiredDocDataM implements Serializable,Cloneable {
	public RequiredDocDataM(){
		super();
	}
	private String requiredDocId;	//XRULES_REQUIRED_DOC.REQUIRED_DOC_ID(VARCHAR2)
	private String verResultId;	//XRULES_REQUIRED_DOC.VER_RESULT_ID(VARCHAR2)
	private String documentGroupCode;	//XRULES_REQUIRED_DOC.DOCUMENT_GROUP_CODE(VARCHAR2)
	private String createBy;	//XRULES_REQUIRED_DOC.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//XRULES_REQUIRED_DOC.CREATE_DATE(DATE)
	private String updateBy;	//XRULES_REQUIRED_DOC.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//XRULES_REQUIRED_DOC.UPDATE_DATE(DATE)
	private String scenarioType; //XRULES_REQUIRED_DOC.SCENARIO_TYPE(VARCHAR2)
	private String docCompletedFlag;	//XRULES_REQUIRED_DOC.DOC_COMPLETED_FLAG(VARCHAR2)
	
	private ArrayList<RequiredDocDetailDataM> requiredDocDetails;
 	
	public String getRequiredDocId() {
		return requiredDocId;
	}
	public void setRequiredDocId(String requiredDocId) {
		this.requiredDocId = requiredDocId;
	}
	public String getVerResultId() {
		return verResultId;
	}
	public void setVerResultId(String verResultId) {
		this.verResultId = verResultId;
	}
	public String getDocumentGroupCode() {
		return documentGroupCode;
	}
	public void setDocumentGroupCode(String documentGroupCode) {
		this.documentGroupCode = documentGroupCode;
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
	public ArrayList<RequiredDocDetailDataM> getRequiredDocDetails() {
		return requiredDocDetails;
	}
	public void setRequiredDocDetails(ArrayList<RequiredDocDetailDataM> requiredDocDetails) {
		this.requiredDocDetails = requiredDocDetails;
	}
	public String getDocCompletedFlag() {
		return docCompletedFlag;
	}
	public void setDocCompletedFlag(String docCompletedFlag) {
		this.docCompletedFlag = docCompletedFlag;
	}
	public void addRequiredDocDetails(RequiredDocDetailDataM requiredDocDetail) {
		if(null==requiredDocDetails){
			requiredDocDetails = new ArrayList<RequiredDocDetailDataM>();
		}
		requiredDocDetails.add(requiredDocDetail);
	}
	public RequiredDocDetailDataM filterRequiredDocDetail(String documentCode) {
		if(null!=requiredDocDetails && null!=documentCode){
			for(RequiredDocDetailDataM requireddocdetail: requiredDocDetails){
				if(documentCode.equals(requireddocdetail.getDocumentCode())){
					return requireddocdetail;
				}
			}
		}
		return null;
	}
	public String getScenarioType() {
		return scenarioType;
	}
	public void setScenarioType(String scenarioType) {
		this.scenarioType = scenarioType;
	}		
	
}
