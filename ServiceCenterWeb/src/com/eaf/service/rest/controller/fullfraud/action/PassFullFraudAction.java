package com.eaf.service.rest.controller.fullfraud.action;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.app.dao.CapportTransactionDAO;
import com.eaf.orig.ulo.app.dao.LoanSetupDAO;
import com.eaf.orig.ulo.app.dao.LoanSetupStampDutyDAO;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CapportTransactionDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.LoanSetupDataM;
import com.eaf.orig.ulo.model.app.LoanSetupStampDutyDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM.PersonalType;
import com.eaf.orig.ulo.service.app.submit.dao.SubmitApplicationManager;
import com.eaf.orig.ulo.service.lookup.LookupServiceCenter;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM;
import com.eaf.service.common.model.decisions.DecisionServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.module.manual.DecisionServiceProxy;
import com.eaf.service.module.model.FullFraudInfoDataM;
import com.eaf.service.rest.model.ServiceResponse;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;
import com.orig.bpm.workflow.handle.WorkflowManager;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class PassFullFraudAction extends ProcessActionHelper{
	private static Logger logger = Logger.getLogger(PassFullFraudAction.class);
	String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
	String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
	String BPM_USER_ID = SystemConfig.getProperty("BPM_USER_ID");
	String BPM_PASSWORD = SystemConfig.getProperty("BPM_PASSWORD");
	
	@Override
	public Object processAction(){
		String errorMsg;
		ProcessActionResponse processActionResponse = new ProcessActionResponse();
		try{
			FullFraudInfoDataM fullFraudInfo = (FullFraudInfoDataM)objectForm;
			String userId = fullFraudInfo.getUserId();
			String applicationGroupId = fullFraudInfo.getApplicationGroupId();
			logger.debug("applicationGroupId : "+applicationGroupId);
			// Initial model from database
			ApplicationGroupDataM applicationGroup =  SubmitApplicationManager.loadApplicationGroup(applicationGroupId);
			if (ServiceCache.lookup("JOBSTATE_PENDING_FULLFRAUD", applicationGroup.getJobState())) {
				// Amend values before calling ODM
				applicationGroup.setUserId(userId);
				//applicationGroup.setDecisionAction(DECISION_ACTION_APPROVE);
				applicationGroup.setFullFraudFlag("N");
				//applicationGroup.setFraudDecision(SystemConstant.getConstant("NO_FRAUD"));
				// Call ODM
				ProcessActionResponse decisionResponse = requestDecision(applicationGroup);
				// Get response
				String decisionResultCode = decisionResponse.getResultCode();					
				logger.debug("decisionResultCode : "+decisionResultCode);	
				logger.debug("applicationGroup.getLastDecision : "+applicationGroup.getLastDecision());			
				processActionResponse.setResultCode(decisionResponse.getResultCode());
				processActionResponse.setErrorMsg(decisionResponse.getErrorMsg());			
				if(ServiceResponse.Status.SUCCESS.equals(decisionResultCode)){
					// Save data back to database
					LookupServiceCenter.getServiceCenterManager().saveApplicationAndLoanSetup(applicationGroup,userId,false,fullFraudInfo);
					// Move flow to next stage
					ProcessActionResponse workflowResponse  = workflowAction(applicationGroup);
					String workflowResultCode = workflowResponse.getResultCode();
					logger.debug("workflowResultCode : "+workflowResultCode);				
					processActionResponse.setResultCode(workflowResponse.getResultCode());
					processActionResponse.setErrorMsg(workflowResponse.getErrorMsg());
				}
				else
				{
					logger.debug("requestDecision return with error code : " + decisionResultCode);				
					processActionResponse.setResultCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
					if(decisionResponse != null)
					{processActionResponse.setErrorMsg(decisionResponse.getErrorMsg());}
				}
			}
			else {
				errorMsg = "ERROR: Job state " + applicationGroup.getJobState() + " is invalid";
				logger.fatal(errorMsg);
				processActionResponse.setResultCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
				processActionResponse.setErrorMsg(errorMsg);
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
		String URL = SystemConfig.getProperty("CALL_DECISION_SERVICE_URL");
		ProcessActionResponse processActionResponse = new ProcessActionResponse();
		try{
			DecisionServiceRequestDataM requestDecision = new DecisionServiceRequestDataM();
			requestDecision.setApplicationGroup(applicationGroup);
			requestDecision.setDecisionPoint(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_POST);
			requestDecision.setUserId(applicationGroup.getUserId());
			
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(DecisionServiceProxy.serviceId);
				serviceRequest.setUserId(applicationGroup.getUserId());
				serviceRequest.setEndpointUrl(URL);
				serviceRequest.setUniqueId(applicationGroup.getApplicationGroupId());
				serviceRequest.setRefId(applicationGroup.getApplicationGroupNo());
				serviceRequest.setObjectData(requestDecision);
				
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			ServiceResponseDataM serivceResponse = proxy.requestService(serviceRequest);
			if(ServiceResponse.Status.SUCCESS.equals(serivceResponse.getStatusCode())){
				DecisionServiceResponseDataM decisionResponse =(DecisionServiceResponseDataM)serivceResponse.getObjectData();
				if(ServiceResponse.Status.SUCCESS.equals(serivceResponse.getStatusCode())){
					processActionResponse.setResultCode(ServiceResponse.Status.SUCCESS);
				}else{
					processActionResponse.setResultCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
					processActionResponse.setErrorMsg(ServiceResponse.StatusDesc.ERROR+":when  get object data");
				}
			}else{
				processActionResponse.setResultCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
				if(serivceResponse != null && serivceResponse.getErrorInfo() != null)
				{processActionResponse.setErrorMsg(serivceResponse.getErrorInfo().getErrorDesc());}
				else
				{processActionResponse.setErrorMsg(ServiceResponse.StatusDesc.ERROR);}
			}
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			processActionResponse.setResultCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processActionResponse.setErrorMsg(e.getLocalizedMessage());
		}
		return processActionResponse;
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

			logger.debug("workflowRequest.decisionAction >> "+workflowRequest.getDecisionAction());
			
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
