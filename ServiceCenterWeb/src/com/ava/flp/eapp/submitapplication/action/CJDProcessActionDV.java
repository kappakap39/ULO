package com.ava.flp.eapp.submitapplication.action;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.performance.TraceController;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.rest.controller.eapp.action.EAppAction;
import com.eaf.service.rest.model.ServiceResponse;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;

public class CJDProcessActionDV extends ProcessActionHelper {

	private Logger logger = Logger.getLogger(CJDProcessActionDV.class);
	private String DECISION_ACTION_DV = SystemConstant.getConstant("DECISION_ACTION_DV");
	private String CALL_ACTION_PROCEED = SystemConstant.getConstant("CALL_ACTION_PROCEED");
	String WIP_JOBSTATE_DUPLICATE_APP = SystemConfig.getGeneralParam("WIP_JOBSTATE_DUPLICATE_APP");
	
	@Override
	public Object processAction() {
		ProcessResponse processResponse = new ProcessResponse();
		processResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
		
		try {
			ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
			
			String transactionId = applicationGroup.getTransactionId();
			TraceController trace = new TraceController(this.getClass().getName(),transactionId);
			logger.debug("process dv for " + applicationGroup.getApplicationGroupNo());
			String userId = applicationGroup.getUserId();
			String jobState = applicationGroup.getJobState();
			
			EAppAction.saveApplication(applicationGroup, userId, "DV");
			
			trace.create("CJDProcessActionDV");
			
			//if case submit from im set user to system
			if(CALL_ACTION_PROCEED.equals(applicationGroup.getSourceAction())){
				applicationGroup.setUserId(SystemConstant.getConstant("SYSTEM_USER"));
			}
			
			//check job state not dup call bpm
			if(!WIP_JOBSTATE_DUPLICATE_APP.contains(jobState)){
				applicationGroup.setDecisionAction(DECISION_ACTION_DV);
				WorkflowResponseDataM workflowResponse = EAppAction.workflowAction(applicationGroup);
				if(BpmProxyConstant.RestAPIResult.SUCCESS.equals(workflowResponse.getResultCode())){
					processResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
				}else{
					processResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
					processResponse.setErrorData(EAppAction.error(ErrorData.ErrorType.SYSTEM_ERROR, workflowResponse.getResultCode(), workflowResponse.getErrorMsg()));
				}
			}
			
			trace.end("CJDProcessActionDV");
			trace.trace();
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			processResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processResponse.setErrorData(EAppAction.error(e));
		}
		return processResponse;
	}
	
}
