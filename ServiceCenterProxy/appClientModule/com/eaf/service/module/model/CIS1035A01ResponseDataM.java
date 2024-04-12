package com.eaf.service.module.model;

import java.io.Serializable;
import java.util.ArrayList;

public class CIS1035A01ResponseDataM implements Serializable,Cloneable{
	private String customerId;
	private ArrayList<CISContactDataM> cisContacts;
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public ArrayList<CISContactDataM> getCisContact() {
		return cisContacts;
	}
	public void setCisContact(ArrayList<CISContactDataM> cisContacts) {
		this.cisContacts = cisContacts;
	}
	
}
