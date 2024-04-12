/*
 * Created on Oct 4, 2007
 * Created by weeraya
 * 
 * Copyright (c) 2007 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15-16th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
 * 
 */
package com.eaf.orig.shared.control.event;

import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.control.event.EventResponseHelper;

/**
 * @author weeraya
 *
 * Type: ApplicationEventResponse
 */
public class ApplicationEventResponse extends EventResponseHelper implements EventResponse {
	private String appNo;
    /**
	 * @param result
	 * @param message
	 */
	public ApplicationEventResponse(int result, String message) {
		this.result = result;
		this.message = message;
	}
	
	/**
	 * @param eventType
	 * @param result
	 * @param message
	 * @param obj
	 */
	public ApplicationEventResponse(int eventType, int result, String message, Object obj){
		this.type = eventType;
		this.result = result;
		this.message = message;
		this.encapData = obj;
	}
	
	public ApplicationEventResponse(int eventType, int result, String message, Object obj, String appNo){
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
