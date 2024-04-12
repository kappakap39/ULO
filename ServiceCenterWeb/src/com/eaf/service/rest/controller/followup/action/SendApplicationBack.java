package com.eaf.service.rest.controller.followup.action;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.service.app.submit.dao.SubmitApplicationManager;
import com.eaf.orig.ulo.service.followup.result.model.FollowUpResultApplicationDataM;
import com.eaf.orig.ulo.service.lookup.LookupServiceCenter;
import com.eaf.service.rest.controller.followup.FollowUpResultUtil;
import com.eaf.service.rest.model.ServiceResponse;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;
import com.orig.bpm.workflow.handle.WorkflowManager;

public class SendApplicationBack  extends ProcessActionHelper{
	private static Logger logger = Logger.getLogger(SendApplicationBack.class);
	String SEND_BACK_DECISION = SystemConstant.getConstant("SEND_BACK_DECISION");
	String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
	String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
	String BPM_USER_ID = SystemConfig.getProperty("BPM_USER_ID");
	String BPM_PASSWORD = SystemConfig.getProperty("BPM_PASSWORD");
	
	@Override
	public Object processAction(){
		ProcessActionResponse processActionResponse = new ProcessActionResponse();
		try{
			FollowUpResultApplicationDataM followUpResultApplication = (FollowUpResultApplicationDataM)objectForm;
			String userId = followUpResultApplication.getUserId();
			String applicationGroupId = followUpResultApplication.getApplicationGroupId();
			logger.debug("applicationGroupId : "+applicationGroupId);
			logger.debug("userId : "+userId);			
			ApplicationGroupDataM applicationGroup = SubmitApplicationManager.loadApplicationGroup(applicationGroupId);
			FollowUpResultUtil.mapFollowUpData(applicationGroup,followUpResultApplication);
			applicationGroup.setDecisionAction(SEND_BACK_DECISION);
			applicationGroup.setUserId(userId);	
			LookupServiceCenter.getServiceCenterManager().saveApplication(applicationGroup,userId,false);
			ProcessActionResponse workflowResponse = workflowAction(applicationGroup);
			String workflowResultCode = workflowResponse.getResultCode();
			logger.debug("workflowResultCode : "+workflowResultCode);			
			processActionResponse.setResultCode(workflowResponse.getResultCode());
			processActionResponse.setErrorMsg(workflowResponse.getErrorMsg());			
		}catch(Exception e){
			logger.fatal("ERROR",e);
			processActionResponse.setResultCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processActionResponse.setErrorMsg(e.getLocalizedMessage());
		}		 
		return processActionResponse;
	}
	
	public ProcessActionResponse workflowAction(ApplicationGroupDataM applicationGroup){
		ProcessActionResponse processActionResponse = new ProcessActionResponse();
		try{
			logger.debug("BPM_HOST : "+BPM_HOST);
			logger.debug("BPM_PORT : "+BPM_PORT);		
			logger.debug("applicationGroup.getDecisionAction : "+applicationGroup.getDecisionAction());		
			WorkflowRequestDataM workflowRequest  = new WorkflowRequestDataM();
				workflowRequest.setApplicationGroup(applicationGroup);
				workflowRequest.setUniqueId(applicationGroup.getApplicationGroupId());
				workflowRequest.setUserId(applicationGroup.getUserId());	
				workflowRequest.setUsername(applicationGroup.getUserId());
				workflowRequest.setFormAction("");
				workflowRequest.setDecisionAction(applicationGroup.getDecisionAction());
				workflowRequest.setInstantId(String.valueOf(applicationGroup.getInstantId()));	
			WorkflowManager workflowManager = new WorkflowManager(BPM_HOST,BPM_PORT,BPM_USER_ID,BPM_PASSWORD);
			WorkflowResponseDataM workflowResponse = workflowManager.completeWorkflowTask(workflowRequest,null);			
			logger.debug("workflowResponse.getResultCode : "+workflowResponse.getResultCode());
			logger.debug("workflowResponse.getResultDesc : "+workflowResponse.getResultDesc());			
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
