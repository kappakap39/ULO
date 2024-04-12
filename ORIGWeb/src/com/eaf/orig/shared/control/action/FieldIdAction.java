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
package com.eaf.orig.shared.control.action;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.action.Action;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.control.event.EventResponseHelper;
import com.eaf.orig.master.ejb.OrigMasterManager;
import com.eaf.orig.master.shared.model.FieldIdM;
import com.eaf.orig.shared.control.event.FieldIdEvent;
import com.eaf.orig.shared.control.event.OrigMasterEventResponse;
import com.eaf.orig.shared.service.ORIGEJBService;

/**
 * @author Administrator
 *
 * Type: FieldIdAction
 */
public class FieldIdAction implements Action {

	Logger logger = Logger.getLogger(FieldIdAction.class);

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.control.action.Action#perform(com.eaf.j2ee.pattern.control.event.Event)
	 */
	public EventResponse perform(Event ev) {
		OrigMasterEventResponse origMasterEventResponse = null;
		FieldIdEvent event = (FieldIdEvent)ev;
		int eventType = event.getEventType();
		
		logger.debug("chk eventType = "+eventType );
		
		Object object = event.getObject();
		OrigMasterManager manager = ORIGEJBService.getOrigMasterManager();
		
		logger.debug("chk manager = "+manager );
		
		try{
		    switch(eventType){
				case FieldIdEvent.FIELD_ID_SAVE:
					boolean hvFieldID;
					logger.debug("now i'm in case FIELD_ID_SAVE");
					
					hvFieldID = manager.chkHvFieldID(((FieldIdM)object).getFieldID());
					logger.debug("///hvFieldID = "+hvFieldID);
					if(!hvFieldID){
						manager.saveFieldId((FieldIdM)object);
					}else{
						String errMsg ="Field ID is already exist.";
						Object encapData = null;
						return new OrigMasterEventResponse(event.getEventType(), EventResponseHelper.FAILED, errMsg, encapData);
					}
				    break;
				case FieldIdEvent.FIELD_ID_SELECT:
					logger.debug("now i'm in case FIELD_ID_SELECT");
					int fieldId = ((FieldIdM)object).getFieldID();
					object = manager.selectFieldId(fieldId);
				    break;
				case FieldIdEvent.FIELD_ID_UPDATE:
					logger.debug("now i'm in case FIELD_ID_UPDATE");					
					manager.updateFieldId((FieldIdM)object);
				    break;
				case FieldIdEvent.FIELD_ID_DELETE:
					logger.debug("now i'm in case FIELD_ID_DELETE");					
					manager.deleteFieldId((int[])object);
					logger.debug("manager.deleteFieldId((String[])object) COMPLETED!!!");	
				    break;
				
				default:break;						
			}
		}catch(Exception e){
		    logger.error("OrigMasterAction.perform error >>",e);
			String errMsg ="Operation fail,please contact admin";
			Object encapData = null;
			if(origMasterEventResponse!=null) encapData = origMasterEventResponse.getEncapData();
			return new OrigMasterEventResponse(event.getEventType(), EventResponseHelper.FAILED, errMsg, encapData);
		}
		logger.debug("now i'm returning object = "+object);
		return new OrigMasterEventResponse(event.getEventType(), EventResponseHelper.SUCCESS, "", object);
		
	}

}
