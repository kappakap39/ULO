/*
 * Created on Nov 28, 2007
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

import java.util.Date;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.action.Action;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.control.event.EventResponseHelper;
import com.eaf.orig.master.ejb.OrigMasterManager;
import com.eaf.orig.master.shared.model.HolidayM;
import com.eaf.orig.shared.control.event.HolMasterEvent;
import com.eaf.orig.shared.control.event.OrigMasterEventResponse;
import com.eaf.orig.shared.service.ORIGEJBService;

/**
 * @author Administrator
 *
 * Type: HolMasterAction
 */
public class HolMasterAction implements Action {

	Logger logger = Logger.getLogger(HolMasterAction.class);

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.control.action.Action#perform(com.eaf.j2ee.pattern.control.event.Event)
	 */
	public EventResponse perform(Event ev) {
		OrigMasterEventResponse origMasterEventResponse = null;
		HolMasterEvent event = (HolMasterEvent)ev;
		int eventType = event.getEventType();
		
		logger.debug("chk eventType = "+eventType );
		
		Object object = event.getObject();
		OrigMasterManager manager = ORIGEJBService.getOrigMasterManager();
		
		logger.debug("chk manager = "+manager );
		
		try{
		    switch(eventType){
				case HolMasterEvent.HOL_MASTER_SAVE:
					boolean hvHolDate=false;
					logger.debug("now i'm in case HOL_MASTER_SAVE");
					
					hvHolDate = manager.chkHvHolDate(((HolidayM)object).getHolDate());
					logger.debug("///hvHolDate = "+hvHolDate);
					if(!hvHolDate){
						manager.saveHoliday((HolidayM)object);
					}else{
						String errMsg ="Holiday is already exist.";
						Object encapData = null;
						return new OrigMasterEventResponse(event.getEventType(), EventResponseHelper.FAILED, errMsg, encapData);
					}
				    break;
				case HolMasterEvent.HOL_MASTER_SELECT:
					logger.debug("now i'm in case HOL_MASTER_SELECT");
					Date holDate = ((HolidayM)object).getHolDate();
					object = manager.selectHolMaster(holDate);
				    break;
				case HolMasterEvent.HOL_MASTER_UPDATE:
					logger.debug("now i'm in case HOL_MASTER_UPDATE");					
					manager.updateHoliday((HolidayM)object);
				    break;
				case HolMasterEvent.HOL_MASTER_DELETE:
					logger.debug("now i'm in case HOL_MASTER_DELETE");	
					manager.deleteHolDate((String[])object);
					logger.debug("manager.deleteHolDate((String[])object) COMPLETED!!!");	
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
