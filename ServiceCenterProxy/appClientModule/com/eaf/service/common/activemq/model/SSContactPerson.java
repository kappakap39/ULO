package com.eaf.service.common.activemq.model;

import java.io.Serializable;

public class SSContactPerson implements Serializable {

	private static final long serialVersionUID = -709551643492300866L;

	private String FullName;
	private String PhoneNo;
	private String Relationship;
	private String Address;
	private String Email;

	public String getFullName() {
		return FullName;
	}

	public void setFullName(String fullName) {
		FullName = fullName;
	}

	public String getPhoneNo() {
		return PhoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		PhoneNo = phoneNo;
	}

	public String getRelationship() {
		return Relationship;
	}

	public void setRelationship(String relationship) {
		Relationship = relationship;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

}
