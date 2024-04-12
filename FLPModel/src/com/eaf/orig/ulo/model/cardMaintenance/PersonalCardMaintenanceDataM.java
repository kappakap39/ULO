package com.eaf.orig.ulo.model.cardMaintenance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

@SuppressWarnings("serial")
public class PersonalCardMaintenanceDataM  implements Serializable, Cloneable,Comparator<Object> {
	public PersonalCardMaintenanceDataM() {
	
	}
	private String firstname;
	private String personalId;
	private String lastname;
	private String custNo;
	private ArrayList<CardMaintenanceDetailDataM> cardMaintenanceDetails;
	private String personalType;
	private String personalStatus;
	private String businessClassId;
	private String errorCustDesc;
	private String errorSupCustDesc;
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public ArrayList<CardMaintenanceDetailDataM> getCardMaintenanceDetails() {
		return cardMaintenanceDetails;
	}
	public void setCardMaintenanceDetails(ArrayList<CardMaintenanceDetailDataM> cardMaintenanceDetails) {
		this.cardMaintenanceDetails = cardMaintenanceDetails;
	}
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public String getPersonalType() {
		return personalType;
	}
	public void setPersonalType(String personalType) {
		this.personalType = personalType;
	}
	public String getPersonalStatus() {
		return personalStatus;
	}
	public void setPersonalStatus(String personalStatus) {
		this.personalStatus = personalStatus;
	}
	public String getBusinessClassId() {
		return businessClassId;
	}
	public void setBusinessClassId(String businessClassId) {
		this.businessClassId = businessClassId;
	}
	public String getErrorCustDesc() {
		return errorCustDesc;
	}
	public void setErrorCustDesc(String errorCustDesc) {
		this.errorCustDesc = errorCustDesc;
	}
	public String getErrorSupCustDesc() {
		return errorSupCustDesc;
	}
	public void setErrorSupCustDesc(String errorSupCustDesc) {
		this.errorSupCustDesc = errorSupCustDesc;
	}
	@Override
	public int compare(Object o1, Object o2) {
		int compare = compareObjectValue(o1,o2);
		return compare;
	}
	private  int compareObjectValue(Object obj1 ,Object obj2){ 
		try{
			if(obj1!=null && obj2!=null && obj1 instanceof String && obj2 instanceof String){
				String personalTpye1 = (String)obj1;
				String personalType2 = (String)obj2;
				return personalTpye1.compareTo(personalType2);
			}
		}catch(Exception e){}
		return 0;
	}
}
