package com.eaf.service.rest.controller.followup.action;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.service.followup.result.model.FollowUpResultApplicationDataM;
import com.eaf.orig.ulo.service.lookup.LookupServiceCenter;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM;
import com.eaf.service.common.model.decisions.DecisionServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.module.manual.DecisionServiceProxy;
import com.eaf.service.rest.controller.eapp.action.EAppAction;
import com.eaf.service.rest.controller.followup.FollowUpResultUtil;
import com.eaf.service.rest.model.ServiceResponse;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;
import com.orig.bpm.workflow.handle.WorkflowManager;

public class CallDecisionPointAndSentToNextStationEAPP implements FollowupAction{


	private static Logger logger = Logger.getLogger(CallDecisionPointAndSentToNextStationNormal.class);
	String DECISION_SERVICE_POINT_DC =SystemConstant.getConstant("DECISION_SERVICE_POINT_DC");
	String DECISION_ACTION_REJECT = SystemConstant.getConstant("DECISION_ACTION_REJECT");	
	String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
	String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
	String BPM_USER_ID = SystemConfig.getProperty("BPM_USER_ID");
	String BPM_PASSWORD = SystemConfig.getProperty("BPM_PASSWORD");
	String DOCUMENT_SLA_TYPE_NOT_COMPLETED = SystemConfig.getProperty("DOCUMENT_SLA_TYPE_NOT_COMPLETED");
	
	@Override
	public ProcessActionResponse processAction(FollowUpResultApplicationDataM followUpResultApplication,ApplicationGroupDataM applicationGroup){

		ProcessActionResponse processActionResponse = new ProcessActionResponse();
		try{
			String userId = followUpResultApplication.getUserId();
			//String applicationGroupId = followUpResultApplication.getApplicationGroupId();
		
			FollowUpResultUtil.mapFollowUpData(applicationGroup,followUpResultApplication);
			applicationGroup.setUserId(userId);
			applicationGroup.setOverSlaDocumentType(followUpResultApplication.getDocumentSLAType());
			applicationGroup.setDecisionAction(DECISION_ACTION_REJECT);			
			ProcessActionResponse decisionResponse = requestDecision(applicationGroup);
			String decisionResultCode = decisionResponse.getResultCode();						
			logger.debug("decisionResultCode : "+decisionResultCode);	
			logger.debug("applicationGroup.getLastDecision : "+applicationGroup.getLastDecision());			
			processActionResponse.setResultCode(decisionResponse.getResultCode());
			processActionResponse.setErrorMsg(decisionResponse.getErrorMsg());			
			if(ServiceResponse.Status.SUCCESS.equals(decisionResultCode)){
				LookupServiceCenter.getServiceCenterManager().saveApplication(applicationGroup,userId,false);				
				ProcessActionResponse workflowResponse  = workflowAction(applicationGroup);
				String workflowResultCode = workflowResponse.getResultCode();
				logger.debug("workflowResultCode : "+workflowResultCode);				
				processActionResponse.setResultCode(workflowResponse.getResultCode());
				processActionResponse.setErrorMsg(workflowResponse.getErrorMsg());
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			processActionResponse.setResultCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processActionResponse.setErrorMsg(e.getLocalizedMessage());
		}
		logger.debug(processActionResponse);
		return processActionResponse;
	}
	
	
	private ProcessActionResponse requestDecision(ApplicationGroupDataM applicationGroup){
		applicationGroup.setUserId(applicationGroup.getSourceUserId());
		return  EAppAction.requestDecision(applicationGroup, DecisionServiceUtil.DECISION_POINT.DECISION_POINT_EDC, SystemConfig.getProperty("SYSTEM_FOLLOW_UP_USER_ID"));
		
	
	}
	
	private ProcessActionResponse workflowAction(ApplicationGroupDataM applicationGroup){
		ProcessActionResponse processActionResponse = new ProcessActionResponse();
		try{		
			WorkflowRequestDataM workflowRequest  = new WorkflowRequestDataM();
				workflowRequest.setApplicationGroup(applicationGroup);
				workflowRequest.setUniqueId(applicationGroup.getApplicationGroupId());
				workflowRequest.setUserId(applicationGroup.getUserId());	
				workflowRequest.setUsername(applicationGroup.getUserId());
				workflowRequest.setFormAction("");
				workflowRequest.setDecisionAction(applicationGroup.getDecisionAction());
				workflowRequest.setInstantId(String.valueOf(applicationGroup.getInstantId()));
				workflowRequest.setEApp(ApplicationUtil.eApp(applicationGroup.getSource()) || ApplicationUtil.cjd(applicationGroup.getSource()));

			WorkflowManager workflowManager = new WorkflowManager(BPM_HOST,BPM_PORT,BPM_USER_ID,BPM_PASSWORD);
			WorkflowResponseDataM workflowResponse = workflowManager.completeWorkflowTask(workflowRequest,null);
			
			logger.debug("workflowResponse.getResultCode >> "+workflowResponse.getResultCode());
			logger.debug("workflowResponse.getResultDesc >> "+workflowResponse.getResultDesc());
			
			if(BpmProxyConstant.RestAPIResult.SUCCESS.equals(workflowResponse.getResultCode())){
				processActionResponse.setResultCode(ServiceResponse.Status.SUCCESS);
			}else{
				processActionResponse.setResultCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
				processActionResponse.setErrorMsg(workflowResponse.getResultDesc());
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			processActionResponse.setResultCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processActionResponse.setErrorMsg(e.getLocalizedMessage());
		}
		return processActionResponse;
	}

}
