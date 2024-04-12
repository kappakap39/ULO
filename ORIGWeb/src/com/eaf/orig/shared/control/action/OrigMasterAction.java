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
package com.eaf.orig.shared.control.action;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.action.Action;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.control.event.EventResponseHelper;
import com.eaf.orig.master.ejb.OrigMasterManager;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.control.event.OrigMasterEvent;
import com.eaf.orig.shared.control.event.OrigMasterEventResponse;
import com.eaf.orig.shared.service.ORIGEJBService;


/**
 * @author Administrator
 *
 * Type: OrigMasterAction
 */
public class OrigMasterAction implements Action {
	Logger logger = Logger.getLogger(OrigMasterAction.class);

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.control.action.Action#perform(com.eaf.j2ee.pattern.control.event.Event)
	 */
	public EventResponse perform(Event ev) {
		OrigMasterEventResponse origMasterEventResponse = null;
		OrigMasterEvent event = (OrigMasterEvent)ev;
		int eventType = event.getEventType();
		
		logger.debug("chk eventType = "+eventType );
		
		Object object = event.getObject();
//		UserDetailM userDetailM = (UserDetailM)event.getObject();
		OrigMasterManager manager = ORIGEJBService.getOrigMasterManager();
		
		logger.debug("chk manager = "+manager );
		
		try{
		    switch(eventType){
				case OrigMasterEvent.USER_DETAIL_SAVE:
					boolean hvUserName;
					logger.debug("now i'm in case USER_DETAIL_SAVE");
					hvUserName = manager.chkHvUserName(((UserDetailM)object).getUserName());
					logger.debug("///hvUserName = "+hvUserName);
					if(!hvUserName){
						manager.saveUserDetail((UserDetailM)object);
					}else{
						String errMsg ="User Name is already exist.";
						Object encapData = null;
						return new OrigMasterEventResponse(event.getEventType(), EventResponseHelper.FAILED, errMsg, encapData);
					}
				    break;
				case OrigMasterEvent.USER_DETAIL_SELECT:
					logger.debug("now i'm in case USER_DETAIL_SELECT");
					String userNameEdit = ((UserDetailM)object).getUserName();
					object = manager.selectUserDetail(userNameEdit);
				    break;
				case OrigMasterEvent.USER_DETAIL_UPDATE:
					logger.debug("now i'm in case USER_DETAIL_UPDATE");					
					manager.updateUserDetail((UserDetailM)object);
				    break;
				case OrigMasterEvent.USER_DETAIL_DELETE:
					logger.debug("now i'm in case USER_DETAIL_DELETE");					
					manager.deleteUserDetail((String[])object);
					logger.debug("manager.deleteUserDetail(userDetailM) COMPLETED!!!");	
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
