/*
 * Created on Nov 25, 2007
 * Created by Prawit Limwattanachai
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

import java.io.Serializable;

import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventHelper;

/**
 * @author Administrator
 *
 * Type: RunParamEvent
 */
public class PolicyRulesEvent extends EventHelper implements Event, Serializable {

	/* Type Constant */
	public static final int POLICY_RULES_SAVE = 401;
	public static final int POLICY_RULES_SELECT = 402;
	public static final int POLICY_RULES_UPDATE = 403;
	public static final int POLICY_RULES_DELETE = 404;
	
	private int eventType;	
	private Object object;
	
	
    public String getEventName() {
         
        return "PolicyRulesEvent";
    }
    
	/**
	 * Constructor
	 */
    public PolicyRulesEvent(){
	}
    
	public PolicyRulesEvent(int eventType, Object object){
		this.setEventType(eventType);
		this.setObject(object);
	}
	
   
	public int getEventType() {
		return eventType;
	}
	public void setEventType(int eventType) {
		this.eventType = eventType;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}

}
