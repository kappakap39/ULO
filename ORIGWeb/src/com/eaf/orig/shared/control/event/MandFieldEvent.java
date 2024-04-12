/*
 * Created on Nov 27, 2007
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
 * Type: MandFieldEvent
 */
public class MandFieldEvent extends EventHelper implements Event, Serializable {

	/* Type Constant */
	public static final int MAND_FIELD_SAVE = 601;
	public static final int MAND_FIELD_SELECT = 602;
	public static final int MAND_FIELD_UPDATE = 603;
	public static final int MAND_FIELD_DELETE = 604;
	
	private int eventType;	
	private Object object;
	
	
    public String getEventName() {
         
        return "MandFieldEvent";
    }
    
	/**
	 * Constructor
	 */
    public MandFieldEvent(){
	}
    
	public MandFieldEvent(int eventType, Object object){
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
