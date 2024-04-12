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
import com.eaf.orig.master.shared.model.GeneralParamM;
import com.eaf.orig.shared.control.event.GenParamEvent;
import com.eaf.orig.shared.control.event.OrigMasterEventResponse;
import com.eaf.orig.shared.service.ORIGEJBService;

/**
 * @author Administrator
 *
 * Type: GenParamAction
 */
public class GenParamAction implements Action {

	Logger logger = Logger.getLogger(GenParamAction.class);

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.control.action.Action#perform(com.eaf.j2ee.pattern.control.event.Event)
	 */
	public EventResponse perform(Event ev) {
		OrigMasterEventResponse origMasterEventResponse = null;
		GenParamEvent event = (GenParamEvent)ev;
		int eventType = event.getEventType();
		
		logger.debug("chk eventType = "+eventType );
		
		Object object = event.getObject();
		OrigMasterManager manager = ORIGEJBService.getOrigMasterManager();
		
		logger.debug("chk manager = "+manager );
		
		try{
		    switch(eventType){
				case GenParamEvent.GEN_PARAM_SAVE:
					logger.debug("now i'm in case GEN_PARAM_SAVE");
					boolean hvGenPrmCde;
					
					hvGenPrmCde = manager.chkHvGenPrmCde(((GeneralParamM)object).getParamCode());
					logger.debug("///hvGenPrmCde = "+hvGenPrmCde);
					if(!hvGenPrmCde){
						manager.saveGenParam((GeneralParamM)object);
					}else{
						String errMsg ="General Parameter Code is already exist.";
						Object encapData = null;
						return new OrigMasterEventResponse(event.getEventType(), EventResponseHelper.FAILED, errMsg, encapData);
					}
				    break;
				case GenParamEvent.GEN_PARAM_SELECT:
					logger.debug("now i'm in case GEN_PARAM_SELECT");
					String genParamCde = ((GeneralParamM)object).getParamCode();
					String busID = ((GeneralParamM)object).getBusClassID();
					object = manager.selectGenParamM(genParamCde,busID);
				    break;
				case GenParamEvent.GEN_PARAM_UPDATE:
					logger.debug("now i'm in case GEN_PARAM_UPDATE");					
					manager.updateGenParam((GeneralParamM)object);
				    break;
				case GenParamEvent.GEN_PARAM_DELETE:
					logger.debug("now i'm in case GEN_PARAM_DELETE");					
					manager.deleteGenParam((String[])object);
					logger.debug("manager.deleteGenParam((String[])object) COMPLETED!!!");	
				    break;
				
				default:break;						
			}
		}catch(Exception e){
		    logger.error("RunParamAction.perform error >>",e);
			String errMsg ="Operation fail,please contact admin";
			Object encapData = null;
			if(origMasterEventResponse!=null) encapData = origMasterEventResponse.getEncapData();
			return new OrigMasterEventResponse(event.getEventType(), EventResponseHelper.FAILED, errMsg, encapData);
		}
		logger.debug("now i'm returning object = "+object);
		return new OrigMasterEventResponse(event.getEventType(), EventResponseHelper.SUCCESS, "", object);
		
	}

}
