package com.eaf.service.rest.controller.followup.action;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.service.app.submit.dao.SubmitApplicationManager;
import com.eaf.orig.ulo.service.followup.result.model.FollowUpResultApplicationDataM;
import com.eaf.orig.ulo.service.lookup.LookupServiceCenter;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM;
import com.eaf.service.common.model.decisions.DecisionServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.module.manual.DecisionServiceProxy;
import com.eaf.service.rest.controller.followup.FollowUpResultUtil;
import com.eaf.service.rest.model.ServiceResponse;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;
import com.orig.bpm.workflow.handle.WorkflowManager;

public class CallDecisionPointAndSentToNextStation extends ProcessActionHelper{
	private static Logger logger = Logger.getLogger(CallDecisionPointAndSentToNextStation.class);
	String DECISION_SERVICE_POINT_DC =SystemConstant.getConstant("DECISION_SERVICE_POINT_DC");
	String DECISION_ACTION_REJECT = SystemConstant.getConstant("DECISION_ACTION_REJECT");	
	String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
	String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
	String BPM_USER_ID = SystemConfig.getProperty("BPM_USER_ID");
	String BPM_PASSWORD = SystemConfig.getProperty("BPM_PASSWORD");
	String DOCUMENT_SLA_TYPE_NOT_COMPLETED = SystemConfig.getProperty("DOCUMENT_SLA_TYPE_NOT_COMPLETED");
	
	@Override
	public Object processAction(){
		ProcessActionResponse processActionResponse = new ProcessActionResponse();
		try{
			FollowUpResultApplicationDataM followUpResultApplication = (FollowUpResultApplicationDataM)objectForm;
			String userId = followUpResultApplication.getUserId();
			String applicationGroupId = followUpResultApplication.getApplicationGroupId();
			logger.debug("applicationGroupId : "+applicationGroupId);
			logger.debug("DocumentSLAType : "+followUpResultApplication.getDocumentSLAType());			
			ApplicationGroupDataM applicationGroup =  SubmitApplicationManager.loadApplicationGroup(applicationGroupId);
			
			
			return FollowupActionFactory.getCallDecisionPointAndSentToNextStation(applicationGroup.getSource())
					.processAction(followUpResultApplication, applicationGroup);

			
		}catch(Exception e){
			logger.fatal("ERROR",e);
			processActionResponse.setResultCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processActionResponse.setErrorMsg(e.getLocalizedMessage());
		}
		logger.debug(processActionResponse);
		return processActionResponse;
	}
	
	
}
