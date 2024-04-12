package com.eaf.service.module.model;

import java.io.Serializable;
import java.util.ArrayList;


public class CIS0222I01ResponseDataM implements Serializable,Cloneable{
	private int totalResult;
	private ArrayList<CISZipCodeDataM> cisZipCodes;
	
	public int getTotalResult() {
		return totalResult;
	}

	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
	}

	public ArrayList<CISZipCodeDataM> getCisZipCode() {
		return cisZipCodes;
	}

	public void setCisZipCode(ArrayList<CISZipCodeDataM> cisZipCodes) {
		this.cisZipCodes = cisZipCodes;
	}
	
}
