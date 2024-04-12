package com.ava.flp.eapp.wm.action;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.avalant.wm.model.WmTask;
import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.postapproval.CJDCardLinkAction;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.common.api.ServiceApplicationDate;
import com.eaf.service.common.constant.ServiceConstant;

public class WmCJDCardlinkResultAction extends ProcessActionHelper {

	private Logger logger = Logger.getLogger(WmCJDCardlinkResultAction.class);
	
	@Override
	public Object processAction() {
		logger.debug("Start WmCJDCardlinkResultAction");
		ProcessResponse response = new ProcessResponse();
		response.setStatusCode(ServiceConstant.Status.SUCCESS);
	    try{
    		WmTask task = (WmTask) objectForm;
    		logger.debug("refCode : "+task.getRefCode());
    		logger.debug("refId : "+task.getRefId());
    		
    		ArrayList<String> applicationGroupNoList = new ArrayList<String>();
    		applicationGroupNoList.add(task.getRefCode());
    		
    		CJDCardLinkAction.responseCJD(applicationGroupNoList);
    		
	    }catch(Exception e){
	    	logger.fatal("ERROR",e);
	    	response.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
	    	ErrorData errorData = new ErrorData();
			errorData.setErrorType(ErrorData.ErrorType.SYSTEM_ERROR);
			errorData.setErrorInformation(e.getLocalizedMessage());
			errorData.setErrorTime(ServiceApplicationDate.getTimestamp());
			errorData.setErrorCode(ErrorData.ErrorType.SYSTEM_ERROR);
			errorData.setErrorDesc(e.getMessage());
	    	response.setErrorData(errorData);
	    }
	    logger.debug("End WmCJDCardlinkResultAction");
		return response;
	}
}
