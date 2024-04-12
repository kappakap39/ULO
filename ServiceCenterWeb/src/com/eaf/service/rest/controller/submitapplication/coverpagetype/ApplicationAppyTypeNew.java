package com.eaf.service.rest.controller.submitapplication.coverpagetype;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.orig.ulo.model.submitapplication.model.SubmitApplicationObjectDataM;
import com.eaf.service.rest.application.model.SubmitApplicationRequest;

@SuppressWarnings("serial")
public class ApplicationAppyTypeNew extends ProcessActionHelper{
	private static Logger logger = Logger.getLogger(ApplicationAppyTypeNew.class);	
	@Override
	public Object processAction(){
		SubmitApplicationObjectDataM submitApplicationObject =(SubmitApplicationObjectDataM)objectForm;
		SubmitApplicationRequest submitApplicationRequest = (SubmitApplicationRequest)submitApplicationObject.getSubmitApplicationRequest();
		logger.debug("submitApplicationObject : "+submitApplicationObject);
		logger.debug("submitApplicationRequest : "+submitApplicationRequest);
		logger.debug("submitApplicationRequest.getQr1() : "+submitApplicationRequest.getQr1());
		return ApplicationAppyTypeNewProcessFactory
				.getApplicationAppyTypeNewProcess(submitApplicationRequest.getQr1())
				.processAction(submitApplicationObject);
	}
}
