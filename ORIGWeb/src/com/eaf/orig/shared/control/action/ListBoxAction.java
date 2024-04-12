/*
 * Created on Nov 23, 2007
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
import com.eaf.orig.master.shared.model.ListBoxMasterM;
import com.eaf.orig.shared.control.event.ListBoxEvent;
import com.eaf.orig.shared.control.event.OrigMasterEventResponse;
import com.eaf.orig.shared.service.ORIGEJBService;

/**
 * @author Administrator
 *
 * Type: ListBoxAction
 */
public class ListBoxAction implements Action {

	Logger logger = Logger.getLogger(ListBoxAction.class);

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.control.action.Action#perform(com.eaf.j2ee.pattern.control.event.Event)
	 */
	public EventResponse perform(Event ev) {
		OrigMasterEventResponse origMasterEventResponse = null;
		ListBoxEvent event = (ListBoxEvent)ev;
		int eventType = event.getEventType();
		
		logger.debug("chk eventType = "+eventType );
		
		Object object = event.getObject();
		OrigMasterManager manager = ORIGEJBService.getOrigMasterManager();
		
		logger.debug("chk manager = "+manager );
		
		try{
		    switch(eventType){
				case ListBoxEvent.LISTBOX_SAVE:
//					boolean hvUserName;
					logger.debug("now i'm in case LISTBOX_SAVE");
					manager.saveListBox((ListBoxMasterM)object);
//					hvUserName = manager.chkHvUserName(((UserDetailM)object).getUserName());
//					logger.debug("///hvUserName = "+hvUserName);
//					if(!hvUserName){
//						manager.saveUserDetail((UserDetailM)object);
//					}else{
//						String errMsg ="User Name is already exist.";
//						Object encapData = null;
//						return new OrigMasterEventResponse(event.getEventType(), EventResponseHelper.FAILED, errMsg, encapData);
//					}
				    break;
				case ListBoxEvent.LISTBOX_SELECT:
					logger.debug("now i'm in case LISTBOX_SELECT");
					String listID = ((ListBoxMasterM)object).getListBoxID();
					object = manager.selectListBox(listID);
				    break;
				case ListBoxEvent.LISTBOX_UPDATE:
					logger.debug("now i'm in case LISTBOX_UPDATE");					
					manager.updateListBox((ListBoxMasterM)object);
				    break;
				case ListBoxEvent.LISTBOX_DELETE:
					logger.debug("now i'm in case LISTBOX_DELETE");					
					manager.deleteListBox((String[])object);
					logger.debug("manager.deleteListBox(ListBoxMasterM) COMPLETED!!!");	
				    break;
				
				default:break;						
			}
		}catch(Exception e){
		    logger.error("ListBoxAction.perform error >>",e);
			String errMsg ="Operation fail,please contact admin";
			Object encapData = null;
			if(origMasterEventResponse!=null) encapData = origMasterEventResponse.getEncapData();
			return new OrigMasterEventResponse(event.getEventType(), EventResponseHelper.FAILED, errMsg, encapData);
		}
		logger.debug("now i'm returning object = "+object);
		return new OrigMasterEventResponse(event.getEventType(), EventResponseHelper.SUCCESS, "", object);
		
	}

}
