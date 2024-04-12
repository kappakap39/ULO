package com.eaf.orig.ulo.model.search;

import java.io.Serializable;

public class SearchApplicantDataM implements Serializable,Cloneable{

	private String personalId;
	private String thFirstName;
	private String thLastName;
	private String thFullName;
	private String personalType;
	private String idno;
	
	public String getIdno() {
		return idno;
	}
	public void setIdno(String idno) {
		this.idno = idno;
	}
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public String getThFirstName() {
		return thFirstName;
	}
	public void setThFirstName(String thFirstName) {
		this.thFirstName = thFirstName;
	}
	public String getThLastName() {
		return thLastName;
	}
	public void setThLastName(String thLastName) {
		this.thLastName = thLastName;
	}
	public String getThFullName() {
		return thFullName;
	}
	public void setThFullName(String thFullName) {
		this.thFullName = thFullName;
	}
	public String getPersonalType() {
		return personalType;
	}
	public void setPersonalType(String personalType) {
		this.personalType = personalType;
	}
}
