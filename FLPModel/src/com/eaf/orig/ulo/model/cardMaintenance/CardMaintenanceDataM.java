package com.eaf.orig.ulo.model.cardMaintenance;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class CardMaintenanceDataM  implements Serializable, Cloneable {
	public CardMaintenanceDataM() {
		
	}
	private String applicationGroupId;
	private String applicationGroupNo;
	private String sendCardklinkFlag;
	private String cardlinkRefNo;
	private String processingDate;
	private String displayMode;
	private String regType;
	private  ArrayList<PersonalCardMaintenanceDataM> personalCardMaintenances;
	
	public String getRegType() {
		return regType;
	}
	public void setRegType(String regType) {
		this.regType = regType;
	}
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getApplicationGroupNo() {
		return applicationGroupNo;
	}
	public void setApplicationGroupNo(String applicationGroupNo) {
		this.applicationGroupNo = applicationGroupNo;
	}
	public String getSendCardklinkFlag() {
		return sendCardklinkFlag;
	}
	public void setSendCardklinkFlag(String sendCardklinkFlag) {
		this.sendCardklinkFlag = sendCardklinkFlag;
	}
	public String getCardlinkRefNo() {
		return cardlinkRefNo;
	}
	public void setCardlinkRefNo(String cardlinkRefNo) {
		this.cardlinkRefNo = cardlinkRefNo;
	}
	public String getProcessingDate() {
		return processingDate;
	}
	public void setProcessingDate(String processingDate) {
		this.processingDate = processingDate;
	}
	public ArrayList<PersonalCardMaintenanceDataM> getPersonalCardMaintenances() {
		return personalCardMaintenances;
	}
	public PersonalCardMaintenanceDataM  getPersonalCardMaintenanceById(String personalId) {
		if(null!=personalCardMaintenances){
			for(PersonalCardMaintenanceDataM  personalcard : personalCardMaintenances){
				if(personalId.equals(personalcard.getPersonalId())){
					return personalcard;
				}
			}
		}
		return null;
	}
	public void  addPersonalCardMaintenanceById(PersonalCardMaintenanceDataM personalCardMaintenance) {
		if(null==personalCardMaintenances){
			personalCardMaintenances = new ArrayList<PersonalCardMaintenanceDataM>();
		}
		personalCardMaintenances.add(personalCardMaintenance);
	}
	
	public void setPersonalCardMaintenances(ArrayList<PersonalCardMaintenanceDataM> personalCardMaintenances) {
		this.personalCardMaintenances = personalCardMaintenances;
	}
	public String getDisplayMode() {
		return displayMode;
	}
	public void setDisplayMode(String displayMode) {
		this.displayMode = displayMode;
	}
}
