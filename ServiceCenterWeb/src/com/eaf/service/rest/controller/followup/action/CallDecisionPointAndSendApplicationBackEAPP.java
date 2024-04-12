package com.eaf.service.rest.controller.followup.action;

import java.util.LinkedList;
import java.util.Queue;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
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
import com.eaf.service.rest.controller.eapp.action.EAppAction;
import com.eaf.service.rest.controller.followup.FollowUpResultUtil;
import com.eaf.service.rest.model.ServiceResponse;

public class CallDecisionPointAndSendApplicationBackEAPP extends SendApplicationBack implements FollowupAction {

	private static Logger logger = Logger.getLogger(CallDecisionPointAndSendApplicationBackEAPP.class);
	String DECISION_SERVICE_POINT_DOC_COMPLETE = SystemConfig.getProperty("DECISION_SERVICE_POINT_DOC_COMPLETE");
	String DECISION_SERVICE_POINT_BUREAU_DOC = SystemConfig.getProperty("DECISION_SERVICE_POINT_BUREAU_DOC");
	String SEND_BACK_DECISION = SystemConstant.getConstant("SEND_BACK_DECISION");
	String WIP_JOBSTATE_WAIT_FOLLOW_FU = SystemConstant.getConstant("PARAM_CODE_WIP_JOBSTATE_WAIT_FOLLOW_FU");
	String WIP_JOBSTATE_WAIT_FOLLOW_DV = SystemConstant.getConstant("PARAM_CODE_WIP_JOBSTATE_WAIT_FOLLOW_DV");	
	
	@Override
	public ProcessActionResponse processAction(FollowUpResultApplicationDataM followUpResultApplication,ApplicationGroupDataM applicationGroup){
		ProcessActionResponse processActionResponse = new ProcessActionResponse();
		try{
	
			String userId = followUpResultApplication.getUserId();
			//String applicationGroupId = followUpResultApplication.getApplicationGroupId();
					
			
			FollowUpResultUtil.mapFollowUpData(applicationGroup,followUpResultApplication);
			applicationGroup.setUserId(userId);
			applicationGroup.setOverSlaDocumentType(followUpResultApplication.getDocumentSLAType());
			ProcessActionResponse decisionResponse = requestDecision(applicationGroup);
			String decisionResultCode = decisionResponse.getResultCode();						
			logger.debug("decisionResultCode : "+decisionResultCode);	
			logger.debug("applicationGroup.getLastDecision : "+applicationGroup.getLastDecision());			
		
			if(ServiceResponse.Status.SUCCESS.equals(decisionResultCode)){				
				applicationGroup.setDecisionAction(SEND_BACK_DECISION);
				applicationGroup.setUserId(userId);	
				LookupServiceCenter.getServiceCenterManager().saveApplication(applicationGroup,userId,false);
				ProcessActionResponse workflowResponse = workflowAction(applicationGroup);
				String workflowResultCode = workflowResponse.getResultCode();
				logger.debug("workflowResultCode : "+workflowResultCode);			
				processActionResponse.setResultCode(workflowResponse.getResultCode());
				processActionResponse.setErrorMsg(workflowResponse.getErrorMsg());			
				
			}else{
				processActionResponse.setResultCode(decisionResponse.getResultCode());
				processActionResponse.setErrorMsg(decisionResponse.getErrorMsg());	
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
		return  EAppAction.requestDecision(applicationGroup, DecisionServiceUtil.DECISION_POINT.DECISION_POINT_VERIFICATION, SystemConfig.getProperty("SYSTEM_FOLLOW_UP_USER_ID")
				, DecisionServiceUtil.FROM_FOLLOW_ACTION);
		
//		String URL = SystemConfig.getProperty("CALL_DECISION_SERVICE_URL");
//		ProcessActionResponse processActionResponse = new ProcessActionResponse();
//		String jobState = applicationGroup.getJobState();
//		logger.debug("jobState>>"+jobState);
//		try{
//			Queue<String>  decisionPoints =  new LinkedList<String>();
//			if(SystemConfig.containsGeneralParam(WIP_JOBSTATE_WAIT_FOLLOW_FU,jobState)){
//				decisionPoints.add(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_BUREAU_DOC);
//				
//			}else if(SystemConfig.containsGeneralParam(WIP_JOBSTATE_WAIT_FOLLOW_DV,jobState)){
//				decisionPoints.add(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_DOC_COMPLETE);
//				decisionPoints.add(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_INCOME);
//			}
//			if(!ServiceUtil.empty(decisionPoints)){
//				ServiceCenterProxy proxy = new ServiceCenterProxy();
//				for(String decisionPoint : decisionPoints){
//					logger.debug("decisionPoint>>"+decisionPoint);
//					DecisionServiceRequestDataM requestDecision = new DecisionServiceRequestDataM();
//					requestDecision.setApplicationGroup(applicationGroup);
//					requestDecision.setDecisionPoint(decisionPoint);
//					requestDecision.setUserId(SystemConfig.getProperty("SYSTEM_FOLLOW_UP_USER_ID"));
//					
//					ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
//						serviceRequest.setServiceId(DecisionServiceProxy.serviceId);
//						serviceRequest.setUserId(applicationGroup.getUserId());
//						serviceRequest.setEndpointUrl(URL);
//						serviceRequest.setUniqueId(applicationGroup.getApplicationGroupId());
//						serviceRequest.setRefId(applicationGroup.getApplicationGroupNo());
//						serviceRequest.setObjectData(requestDecision);
//						
//					ServiceResponseDataM serivceResponse = proxy.requestService(serviceRequest);
//					if(ServiceResponse.Status.SUCCESS.equals(serivceResponse.getStatusCode())){
//						DecisionServiceResponseDataM decisionResponse =(DecisionServiceResponseDataM)serivceResponse.getObjectData();
//						if(ServiceResponse.Status.SUCCESS.equals(serivceResponse.getStatusCode())){
//							processActionResponse.setResultCode(ServiceResponse.Status.SUCCESS);
//						}else{
//							processActionResponse.setResultCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
//							processActionResponse.setErrorMsg(ServiceResponse.StatusDesc.ERROR+":when  get object data");
//						}
//					}else{
//						processActionResponse.setResultCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
//						processActionResponse.setErrorMsg(ServiceResponse.StatusDesc.ERROR);
//						break;
//					}
//				}
//			}		
//		}catch(Exception e){
//			logger.fatal("ERROR ",e);
//			processActionResponse.setResultCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
//			processActionResponse.setErrorMsg(e.getLocalizedMessage());
//		}
//		return processActionResponse;
	}
	
}
