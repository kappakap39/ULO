package com.eaf.service.rest.controller.fraud.result;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ava.flp.eapp.fraudresult.model.FraudResultM;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.service.lookup.LookupServiceCenter;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.module.model.FullFraudInfoDataM;
import com.eaf.service.rest.controller.eapp.action.EAppAction;
import com.eaf.service.rest.model.ServiceResponse;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;

@RestController
@RequestMapping("/service/fraudresult")
public class FraudResultController {

	private Logger logger = Logger.getLogger(FraudResultController.class);
    private String DECISION_ACTION_APPROVE = SystemConstant.getConstant("DECISION_ACTION_APPROVE");
    private String DECISION_ACTION_REJECT = SystemConstant.getConstant("DECISION_ACTION_REJECT");
    private String FINAL_APP_DECISION_APPROVE = SystemConstant.getConstant("FINAL_APP_DECISION_APPROVE");
    private String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
	
	@RequestMapping(value="/submit",method={RequestMethod.PUT, RequestMethod.POST})
    public @ResponseBody ResponseEntity<ProcessResponse> getFraudResult(@RequestBody FraudResultM fraudResultM){
		ProcessResponse response = new ProcessResponse();
		response.setStatusCode(ServiceResponse.Status.SUCCESS);
		logger.debug("" + fraudResultM.toString());
		
		ArrayList<String> validJobStateFullFraud = SystemConstant.getArrayListConstant("VALID_JOBSTATE_FULLFRAUD");
		
		try {
			String applicationNo = fraudResultM.getApplicationNo();
			String fraudResult = fraudResultM.getFraudResult();
			logger.debug("applicationNo : " + applicationNo);
			logger.debug("fraudResult : " + fraudResult);

			String userId = SystemConstant.getConstant("SYSTEM_USER");
			String serviceReqRespId = ServiceUtil.generateServiceReqResId();
			String applicationGroupId = ORIGDAOFactory.getApplicationGroupDAO().getApplicationGroupIdByApplicationGroupNo(applicationNo);
			ApplicationGroupDataM applicationGroup = ORIGDAOFactory.getApplicationGroupDAO().loadOrigApplicationGroupM(applicationGroupId);
			
			if (null != applicationGroup && null != applicationGroup.getJobState() && !Util.empty(validJobStateFullFraud) && validJobStateFullFraud.contains(applicationGroup.getJobState()) ) {
				applicationGroup.setFullFraudFlag(getFullFraudFlag(fraudResult));
				
				applicationGroup.setUserId(applicationGroup.getSourceUserId());
				ProcessActionResponse decisionResponse = EAppAction.requestDecision(applicationGroup, DecisionServiceUtil.DECISION_POINT.DECISION_POINT_EFA, userId);
				String decisionResultCode = decisionResponse.getResultCode();						
				logger.debug("decisionResultCode : "+decisionResultCode);	
				response.setStatusCode(decisionResponse.getResultCode());
				
				if(applicationGroup.foundRecommendDecision(FINAL_APP_DECISION_APPROVE)){
					applicationGroup.setDecisionAction(DECISION_ACTION_APPROVE);		
				}else{
					applicationGroup.setDecisionAction(DECISION_ACTION_REJECT);		
				}
				logger.debug("applicationGroup.getDecisionAction() : "+applicationGroup.getDecisionAction());
				
				if(ServiceResponse.Status.SUCCESS.equals(decisionResultCode)){
					EAppAction.saveApplication(applicationGroup, userId, "FRAUD_RESULT");
					
					if( applicationGroup.foundRecommendDecisionByProduct(PRODUCT_K_PERSONAL_LOAN,FINAL_APP_DECISION_APPROVE)){
						FullFraudInfoDataM fraudInfo = new FullFraudInfoDataM();
						fraudInfo.setApplicationGroupId( applicationGroupId );
						fraudInfo.setApplicationGroupNo( applicationGroup.getApplicationGroupNo() );
						fraudInfo.setProduct( fraudResultM.getProduct() );
						fraudInfo.setResult( fraudResult );
						fraudInfo.setUserId( userId );
						fraudInfo.setRequestId( serviceReqRespId );
						
						LookupServiceCenter.getServiceCenterManager().saveApplicationAndLoanSetup(applicationGroup,userId,false,fraudInfo);
					}
					
					logger.debug("getDecisionAction >> "+applicationGroup.getDecisionAction());
					logger.debug("getLastDecision >> "+applicationGroup.getLastDecision());
					
					WorkflowResponseDataM workflowResponse = EAppAction.workflowAction(applicationGroup);
					if(BpmProxyConstant.RestAPIResult.SUCCESS.equals(workflowResponse.getResultCode())){
						response.setStatusCode(ServiceResponse.Status.SUCCESS);
					}else{
						response.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
						response.setErrorData(EAppAction.error(ErrorData.ErrorType.SYSTEM_ERROR, workflowResponse.getErrorCode(), workflowResponse.getErrorMsg()));
					}
				}
			}
			else{
				String errorMsg = "JobState is not ready";
				response.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
				response.setErrorData(EAppAction.error(ErrorData.ErrorType.SYSTEM_ERROR, ServiceResponse.Status.SYSTEM_EXCEPTION, errorMsg));
			}
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			response.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			response.setErrorData(EAppAction.error(e));
		}
		return ResponseEntity.ok(response);
	}
	
	private String getFullFraudFlag(String fraudResult){
		if("K".equals(fraudResult)){
			return MConstant.FLAG_Y;
		}
		else if ("F".equals(fraudResult)){
			return MConstant.FLAG_N;
		}
		return null;
	}
}
