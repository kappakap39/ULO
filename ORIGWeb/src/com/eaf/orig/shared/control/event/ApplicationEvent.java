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

import java.io.Serializable;

import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventHelper;
import com.eaf.orig.profile.model.UserDetailM;

/**
 * @author weeraya
 *
 * Type: ApplicationEvent
 */
public class ApplicationEvent extends EventHelper implements Event, Serializable {
    /* Type Constant */
	public static final int DE_SAVE = 101;
	public static final int DE_SUBMIT = 102;
	
	public static final int DE_SUBMIT_ALL = 103;
	
	
	public static final int UW_SAVE = 201;
	public static final int UW_SUBMIT = 202;
	public static final int UW_REOPEN = 203;
	
	public static final int CMR_SAVE = 301;
	public static final int CMR_SUBMIT = 302;

	public static final int PROPOSAL_SAVE = 501;
	public static final int PROPOSAL_SUBMIT = 502;
	
	public static final int PD_SAVE = 401;
	public static final int PD_SUBMIT = 402;
	
	public static final int XCMR_SAVE = 601;
	public static final int XCMR_SUBMIT = 602;
	public static final int NCB_SAVE = 9999;
	public static final int PRESCORE_SAVE = 9998;
	public static final int PD_REVERSE = 9997;
	public static final int MANUAL_CANCEL = 9996;
	//new role xuw
	public static final int XUW_SAVE = 701;
	public static final int XUW_SUBMIT = 702;
	private UserDetailM userM;
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.control.event.Event#getEventName()
     */
    public String getEventName() {
         
        return "ApplicationEvent";
    }
    
	/**
	 * Constructor
	 */
	public ApplicationEvent(int eventType, Object object){
		this.setEventType(eventType);
		this.setObject(object);
	}
	
	public ApplicationEvent(int eventType, Object object, UserDetailM userM){
		this.setEventType(eventType);
		this.setObject(object);
		this.setUserM(userM);
		this.setUserName((userM!=null)?userM.getUserName():"");
	}
	
    /**
     * @return Returns the userM.
     */
    public UserDetailM getUserM() {
        return userM;
    }
    /**
     * @param userM The userM to set.
     */
    public void setUserM(UserDetailM userM) {
        this.userM = userM;
    }
}
