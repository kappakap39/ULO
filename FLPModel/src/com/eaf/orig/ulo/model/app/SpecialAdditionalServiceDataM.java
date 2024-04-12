package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class SpecialAdditionalServiceDataM implements Serializable,Cloneable{
	public SpecialAdditionalServiceDataM(){
		super();
	}	
	public void init(String uniqueId){
		this.serviceId=uniqueId;
	}
	private String serviceId;	//ORIG_ADDITIONAL_SERVICE.SERVICE_ID(VARCHAR2)
	private String currentAccName;	//ORIG_ADDITIONAL_SERVICE.CURRENT_ACC_NAME(VARCHAR2)
	private String savingAccNo;	//ORIG_ADDITIONAL_SERVICE.SAVING_ACC_NO(VARCHAR2)
	private String serviceType;	//ORIG_ADDITIONAL_SERVICE.SERVICE_TYPE(VARCHAR2)
	private String currentAccNo;	//ORIG_ADDITIONAL_SERVICE.CURRENT_ACC_NO(VARCHAR2)
	private String savingAccName;	//ORIG_ADDITIONAL_SERVICE.SAVING_ACC_NAME(VARCHAR2)
	private String createBy;	//ORIG_ADDITIONAL_SERVICE.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_ADDITIONAL_SERVICE.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_ADDITIONAL_SERVICE.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_ADDITIONAL_SERVICE.UPDATE_DATE(DATE)
	private String completeData; //ORIG_ADDITIONAL_SERVICE.COMPLETE_DATA(VARCHAR2)
	private String completeDataSaving; //ORIG_ADDITIONAL_SERVICE.COMPLETE_DATA_SV_AC(VARCHAR2)
	private String personalId;
	
	private ArrayList<SpecialAdditionalServiceMapDataM> specialAdditionalServiceMaps;
	
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getCurrentAccName() {
		return currentAccName;
	}
	public void setCurrentAccName(String currentAccName) {
		this.currentAccName = currentAccName;
	}
	public String getSavingAccNo() {
		return savingAccNo;
	}
	public void setSavingAccNo(String savingAccNo) {
		this.savingAccNo = savingAccNo;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getCurrentAccNo() {
		return currentAccNo;
	}
	public void setCurrentAccNo(String currentAccNo) {
		this.currentAccNo = currentAccNo;
	}
	public String getSavingAccName() {
		return savingAccName;
	}
	public void setSavingAccName(String savingAccName) {
		this.savingAccName = savingAccName;
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
	public String getCompleteData() {
		return completeData;
	}
	public void setCompleteData(String completeData) {
		this.completeData = completeData;
	}
	public String getCompleteDataSaving() {
		return completeDataSaving;
	}
	public void setCompleteDataSaving(String completeDataSaving) {
		this.completeDataSaving = completeDataSaving;
	}
	public ArrayList<SpecialAdditionalServiceMapDataM> getSpecialAdditionalServiceMaps() {
		return specialAdditionalServiceMaps;
	}
	public void setSpecialAdditionalServiceMaps(ArrayList<SpecialAdditionalServiceMapDataM> specialAdditionalServiceMaps) {
		this.specialAdditionalServiceMaps = specialAdditionalServiceMaps;
	}
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SpecialAdditionalServiceDataM [serviceId=");
		builder.append(serviceId);
		builder.append(", currentAccName=");
		builder.append(currentAccName);
		builder.append(", savingAccNo=");
		builder.append(savingAccNo);
		builder.append(", serviceType=");
		builder.append(serviceType);
		builder.append(", currentAccNo=");
		builder.append(currentAccNo);
		builder.append(", savingAccName=");
		builder.append(savingAccName);
		builder.append(", createBy=");
		builder.append(createBy);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", updateBy=");
		builder.append(updateBy);
		builder.append(", updateDate=");
		builder.append(updateDate);
		builder.append(", completeData=");
		builder.append(completeData);
		builder.append(", completeDataSaving=");
		builder.append(completeDataSaving);
		builder.append(", personalId=");
		builder.append(personalId);
		builder.append(", specialAdditionalServiceMaps=");
		builder.append(specialAdditionalServiceMaps);
		builder.append("]");
		return builder.toString();
	}	
}
