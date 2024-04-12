package com.eaf.inf.batch.ulo.letter.reject.model;

import java.io.Serializable;

public class RejectLetterSortingContent implements Serializable, Cloneable{
	public RejectLetterSortingContent(){
		super();
	}
	private String zipcode;
	private RejectLetterInterfaceResponse rejectLetterInterfaceResponse;
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public RejectLetterInterfaceResponse getRejectLetterInterfaceResponse() {
		return rejectLetterInterfaceResponse;
	}
	public void setRejectLetterInterfaceResponse(
			RejectLetterInterfaceResponse rejectLetterInterfaceResponse) {
		this.rejectLetterInterfaceResponse = rejectLetterInterfaceResponse;
	}
}
