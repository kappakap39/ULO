package com.eaf.j2ee.pattern.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ScreenFlow implements Serializable,Cloneable{
	public ScreenFlow(){
		super();
	}
	private String screenId;
	public String getScreenId() {
		return screenId;
	}
	public void setScreenId(String screenId) {
		this.screenId = screenId;
	}	
}
