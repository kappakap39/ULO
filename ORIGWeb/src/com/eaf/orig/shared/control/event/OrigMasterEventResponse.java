/*
 * Created on Nov 20, 2007
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

import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.control.event.EventResponseHelper;

/**
 * @author Administrator
 *
 * Type: OrigMasterEventResponse
 */
public class OrigMasterEventResponse extends EventResponseHelper implements EventResponse{
	
	
	public OrigMasterEventResponse(int eventType, int result, String message, Object object ){
		
		this.type = eventType;
		this.encapData = object;
		this.result = result;
		this.message = message;
	}

}
