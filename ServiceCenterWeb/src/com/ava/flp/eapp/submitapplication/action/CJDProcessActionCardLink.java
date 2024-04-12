package com.ava.flp.eapp.submitapplication.action;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.postapproval.CJDCardLinkAction;
import com.eaf.orig.ulo.model.eapp.WFSubmitApplicationM;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.rest.controller.eapp.action.EAppAction;
import com.eaf.service.rest.model.ServiceResponse;

public class CJDProcessActionCardLink extends ProcessActionHelper {

	private Logger logger = Logger.getLogger(CJDProcessActionCardLink.class);
	
	@Override
	public Object processAction() {
		
		ProcessResponse processResponse = new ProcessResponse();
		processResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
		try{
			WFSubmitApplicationM appObject = (WFSubmitApplicationM)objectForm;
			
			logger.debug("CJDProcessActionCardLink for " + appObject.getApplicationGroupNo());

			String applicationGroupId = appObject.getApplicationGroupId();
			String applicationGroupNo = appObject.getApplicationGroupNo();
			logger.debug("appGroupId = " + applicationGroupId);
			logger.debug("appGroupNo = " + applicationGroupNo);
			
			//Do CJDCardLinkAction - Generate CardLinkSetup message and sent to CJD MQ Queue
			CJDCardLinkAction.processCardLinkAction(applicationGroupId, applicationGroupNo);
			
		}
		catch(Exception e){
			logger.fatal("ERROR",e);
			processResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processResponse.setErrorData(EAppAction.error(e));
		}
		return processResponse;
	}
}
