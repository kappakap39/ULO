package com.eaf.orig.ulo.pl.control.event;

import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.control.event.EventResponseHelper;

public class PLApplicationEventResponse extends EventResponseHelper implements EventResponse{

	private static final long serialVersionUID = 1L;
	
	private String appNo;
    /**
	 * @param result
	 * @param message
	 */
	public PLApplicationEventResponse(int result, String message) {
		this.result = result;
		this.message = message;
	}
	
	/**
	 * @param eventType
	 * @param result
	 * @param message
	 * @param obj
	 */
	public PLApplicationEventResponse(int eventType, int result, String message, Object obj){
		this.type = eventType;
		this.result = result;
		this.message = message;
		this.encapData = obj;
	}
	
	public PLApplicationEventResponse(int eventType, int result, String message, Object obj, String appNo){
		this.type = eventType;
		this.result = result;
		this.message = message;
		this.encapData = obj;
	}
	/**
	 * @return Returns the appNo.
	 */
	public String getAppNo() {
		return appNo;
	}
	/**
	 * @param appNo The appNo to set.
	 */
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}	

}
