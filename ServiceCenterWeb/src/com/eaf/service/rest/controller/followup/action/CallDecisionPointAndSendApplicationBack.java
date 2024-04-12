package com.eaf.service.rest.controller.followup.action;

import java.util.LinkedList;
import java.util.Queue;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ProcessActionResponse;
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
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.module.manual.DecisionServiceProxy;
import com.eaf.service.rest.controller.followup.FollowUpResultUtil;
import com.eaf.service.rest.model.ServiceResponse;

public class CallDecisionPointAndSendApplicationBack extends SendApplicationBack{
	private static Logger logger = Logger.getLogger(CallDecisionPointAndSendApplicationBack.class);
	String DECISION_SERVICE_POINT_DOC_COMPLETE = SystemConfig.getProperty("DECISION_SERVICE_POINT_DOC_COMPLETE");
	String DECISION_SERVICE_POINT_BUREAU_DOC = SystemConfig.getProperty("DECISION_SERVICE_POINT_BUREAU_DOC");
	String SEND_BACK_DECISION = SystemConstant.getConstant("SEND_BACK_DECISION");
	String WIP_JOBSTATE_WAIT_FOLLOW_FU = SystemConstant.getConstant("PARAM_CODE_WIP_JOBSTATE_WAIT_FOLLOW_FU");
	String WIP_JOBSTATE_WAIT_FOLLOW_DV = SystemConstant.getConstant("PARAM_CODE_WIP_JOBSTATE_WAIT_FOLLOW_DV");		
	
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
			
			return FollowupActionFactory.getCallDecisionPointAndSendApplicationBack(applicationGroup.getSource())
					.processAction(followUpResultApplication, applicationGroup);
					
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			processActionResponse.setResultCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processActionResponse.setErrorMsg(e.getLocalizedMessage());
		}
		return processActionResponse;
	}
}
