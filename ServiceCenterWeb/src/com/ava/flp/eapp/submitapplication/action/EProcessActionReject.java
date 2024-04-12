package com.ava.flp.eapp.submitapplication.action;

import org.apache.log4j.Logger;

import com.ava.flp.eapp.submitapplication.model.ESubmitApplicationObject;
import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.performance.TraceController;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.common.api.ComplexClassExclusionStrategy;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.module.model.PostApprovalDataM;
import com.eaf.service.rest.controller.eapp.action.EAppAction;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;

public class EProcessActionReject extends ProcessActionHelper {

	private Logger logger = Logger.getLogger(EProcessActionReject.class);
	private final static String WM_ACTION_POSTAPPROVAL = SystemConstant.getConstant("WM_ACTION_POSTAPPROVAL");
	private final static String FINAL_APP_DECISION_REJECT = SystemConstant.getConstant("FINAL_APP_DECISION_REJECT");
	private final static String JOBSTATE_REJECTED = SystemConstant.getConstant("JOBSTATE_REJECTED");
	private final static String APPLICATION_STATIC_REJECTED = SystemConstant.getConstant("APPLICATION_STATIC_REJECTED");
	
	@Override
	public Object processAction() {
		ProcessResponse processResponse = new ProcessResponse();
		processResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
		try{
			ESubmitApplicationObject submitEAppObject = (ESubmitApplicationObject)objectForm;
//			ApplicationGroup eApplicationGroup = submitEAppObject.getApplicationGroup();
//			ApplicationGroupDataM uloApplicationGroup = new EAppModelMapper().mapUloModel(null, eApplicationGroup);
			ApplicationGroupDataM uloApplicationGroup = (ApplicationGroupDataM) submitEAppObject.getApplicationGroup();
			String userId = submitEAppObject.getUserId();
			
			String transactionId = uloApplicationGroup.getTransactionId();
			TraceController trace = new TraceController(this.getClass().getName(),transactionId);
			logger.debug("process reject for " + uloApplicationGroup.getApplicationGroupNo());
			
			trace.create("EProcessReject");
			
			EAppAction.mapBranchData(uloApplicationGroup);
			EAppAction.mapSaleData(uloApplicationGroup);
			EAppAction.mapCashTransferAccountType(uloApplicationGroup);
			EAppAction.mapCardAdditionalInfo(uloApplicationGroup);

			trace.create("callDecision");
			uloApplicationGroup.setUserId(uloApplicationGroup.getSourceUserId());
			ProcessActionResponse decisionResponse = EAppAction.requestDecision(uloApplicationGroup, DecisionServiceUtil.DECISION_POINT.DECISION_POINT_EDV2, userId);
			String decisionResultCode = decisionResponse.getResultCode();						
			logger.debug("decisionResultCode : "+decisionResultCode);	
			processResponse.setStatusCode(decisionResponse.getResultCode());
			trace.end("callDecision");			
			if(ServiceResponse.Status.SUCCESS.equals(decisionResultCode)){
				uloApplicationGroup.setJobState(JOBSTATE_REJECTED);
				uloApplicationGroup.setApplicationStatus(APPLICATION_STATIC_REJECTED);

				EAppAction.saveApplication(uloApplicationGroup, userId, "REJECTED");
				EAppAction.mapCisData(uloApplicationGroup, userId);
				
				if(!Util.empty(uloApplicationGroup.getInstantId())){
					logger.debug("getDecisionAction() : " + uloApplicationGroup.getDecisionAction());
					WorkflowResponseDataM workflowResponse = EAppAction.workflowAction(uloApplicationGroup);
					if(BpmProxyConstant.RestAPIResult.SUCCESS.equals(workflowResponse.getResultCode())){
						processResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
					}else{
						processResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
						processResponse.setErrorData(EAppAction.error(ErrorData.ErrorType.SYSTEM_ERROR, workflowResponse.getResultCode(), workflowResponse.getErrorMsg()));
					}
				}
				
				if(ServiceResponse.Status.SUCCESS.equals(processResponse.getStatusCode())){
					trace.create("addWorkManagerTask");
					
					PostApprovalDataM postApproval = new PostApprovalDataM();
					postApproval.setApplicationGroupNo(uloApplicationGroup.getApplicationGroupNo());
					postApproval.setRecommendDecision(FINAL_APP_DECISION_REJECT);
					
					String contentMsg = "";
					Gson gson = new GsonBuilder().setExclusionStrategies(new ComplexClassExclusionStrategy()).create();
					try{
						contentMsg = gson.toJson(postApproval);
					}catch(Exception e){
						logger.fatal("ERROR ",e);
						if(null != postApproval){
							contentMsg = postApproval.toString();
						}
					}
					
					processResponse = EAppAction.AddTaskToWorkManager(uloApplicationGroup, WM_ACTION_POSTAPPROVAL, contentMsg);
					
					trace.end("addWorkManagerTask");
				}
			}else{
				processResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
				processResponse.setErrorData(EAppAction.error(ErrorData.ErrorType.SYSTEM_ERROR, decisionResponse.getResultCode(), decisionResponse.getErrorMsg()));
			}
			
			trace.end("EProcessReject");
			trace.trace();
		}
		catch(Exception e){
			logger.fatal("ERROR",e);
			processResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processResponse.setErrorData(EAppAction.error(e));
		}
		return processResponse;
	}
}
