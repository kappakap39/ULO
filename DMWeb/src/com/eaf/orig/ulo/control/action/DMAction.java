package com.eaf.orig.ulo.control.action;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.engine.NotifyDataM;
import com.eaf.core.ulo.common.util.NotifyForm;
import com.eaf.j2ee.pattern.control.action.Action;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.control.event.EventResponseHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.ejb.view.DMManager;
import com.eaf.orig.ulo.app.proxy.DMServiceProxy;
import com.eaf.orig.ulo.control.event.DMEvent;
import com.eaf.orig.ulo.control.event.DMEventResponse;
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.model.dm.DocumentManagementDataM;

public class DMAction implements Action{
	private static final long serialVersionUID = 1L;
	private int eventResp = EventResponseHelper.SUCCESS;
	private static transient Logger logger = Logger.getLogger(DMAction.class);
	@Override
	public EventResponse perform(Event ev) {
		DMEventResponse eventResponse = null;
		DMEvent event = (DMEvent) ev;
		int eventType = event.getEventType();
		Object object = event.getObject();
		UserDetailM userM = event.getUserM();
		Object returnObject = null;
		DocumentManagementDataM docMangeM = (DocumentManagementDataM)event.getObject();
		try{
			DMManager  dmManager = DMServiceProxy.getDMManager(); 
			switch(eventType){
				case DMEvent.DM_SAVE_STORE:
					logger.debug("###Save store###");
					dmManager.saveDMStore(docMangeM, userM);
					break;
				case DMEvent.DM_SAVE_BORROW:
					logger.debug("###Save Borrow###");
					dmManager.saveDMBorrow(docMangeM, userM);
					break;
				default:
					break;
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			String MSG = Message.error(e);
/*			Object encapData = null;
			if (eventResponse != null)encapData = eventResponse.getEncapData();
			return new DMEventResponse(event.getEventType(),EventResponseHelper.FAILED, MSG, encapData);*/
			NotifyDataM notify = NotifyForm.error(e);
			return new DMEventResponse(event.getEventType(),EventResponseHelper.FAILED, MSG, notify);
		}
		return new DMEventResponse(event.getEventType(),EventResponseHelper.SUCCESS,"", returnObject);
	}
}
