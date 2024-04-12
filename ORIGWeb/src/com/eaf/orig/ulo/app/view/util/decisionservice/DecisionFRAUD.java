package com.eaf.orig.ulo.app.view.util.decisionservice;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.dao.utility.UtilityDAO;
import com.eaf.orig.shared.dao.utility.UtilityDAOImpl;
import com.eaf.orig.ulo.app.view.util.uloOrigApp.UloOrigAppProxy;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.decison.DecisionApplication;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM;
import com.ibm.websphere.ce.cm.DuplicateKeyException;
import com.orig.bpm.workflow.handle.WorkflowAction;

public class DecisionFRAUD extends ProcessActionHelper {
	private static transient Logger logger = Logger.getLogger(DecisionFRAUD.class);
	String DECISION_SERVICE_POINT_DE1 =SystemConstant.getConstant("DECISION_SERVICE_POINT_DE1");
	String FINAL_APP_DECISION_REJECT = SystemConstant.getConstant("FINAL_APP_DECISION_REJECT");
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String JOBSTATE_REQUIRE_VERIFY_INCOME = SystemConstant.getConstant("JOBSTATE_REQUIRE_VERIFY_INCOME");
	String ACTION_SUBMIT = SystemConstant.getConstant("ACTION_SUBMIT");
	
	String FRAUD = SystemConstant.getConstant("FRAUD");
	String NO_FRAUD = SystemConstant.getConstant("NO_FRAUD");
	String RETURN = SystemConstant.getConstant("RETURN");
	String DECISION_ACTION_PASS = SystemConstant.getConstant("DECISION_ACTION_PASS");
	String DECISION_ACTION_FAIL = SystemConstant.getConstant("DECISION_ACTION_FAIL");

	String WIP_JOBSTATE_DE1_1 = SystemConfig.getGeneralParam("WIP_JOBSTATE_DE1_1");
	String WIP_JOBSTATE_DE1_2 = SystemConfig.getGeneralParam("WIP_JOBSTATE_DE1_2");
	String WIP_JOBSTATE_DE2 = SystemConfig.getGeneralParam("WIP_JOBSTATE_DE2");
	String WIP_JOBSTATE_DV = SystemConfig.getGeneralParam("WIP_JOBSTATE_DV");
	String WIP_JOBSTATE_CA = SystemConfig.getGeneralParam("WIP_JOBSTATE_CA");
	String WIP_JOBSTATE_VT = SystemConfig.getGeneralParam("WIP_JOBSTATE_VT");
	
	String FRAUD_DECISION_SERVICE_POINT_DE1_1 = SystemConstant.getConstant("FRAUD_DECISION_SERVICE_POINT_DE1_1"); 
	String FRAUD_DECISION_SERVICE_POINT_DE1_2 = SystemConstant.getConstant("FRAUD_DECISION_SERVICE_POINT_DE1_2"); 
	String FRAUD_DECISION_SERVICE_POINT_DE2 = SystemConstant.getConstant("FRAUD_DECISION_SERVICE_POINT_DE2"); 
	String FRAUD_DECISION_SERVICE_POINT_DV = SystemConstant.getConstant("FRAUD_DECISION_SERVICE_POINT_DV"); 
	String FRAUD_DECISION_SERVICE_POINT_CA = SystemConstant.getConstant("FRAUD_DECISION_SERVICE_POINT_CA"); 
	String FRAUD_DECISION_SERVICE_POINT_VT = SystemConstant.getConstant("FRAUD_DECISION_SERVICE_POINT_VT"); 
	
	private String BUTTON_ACTION_SUBMIT = SystemConstant.getConstant("BUTTON_ACTION_SUBMIT");
	private String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	private String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	private int SUBMIT_IA_BLOCK_TIME = SystemConstant.getIntConstant("SUBMIT_IA_BLOCK_TIME");
	
	private String ERROR_SUBMIT_CLOSE_INTERVAL_1 = MessageErrorUtil.getText("ERROR_SUBMIT_CLOSE_INTERVAL_1");
	private String ERROR_SUBMIT_CLOSE_INTERVAL_2 = MessageErrorUtil.getText("ERROR_SUBMIT_CLOSE_INTERVAL_2");
	private String ERROR_SUBMIT_APP_WAIT_FOR_APPROVE = MessageErrorUtil.getText("ERROR_SUBMIT_APP_WAIT_FOR_APPROVE");
	
	public Object processAction()  {
		logger.debug("decision FRAUD.processAction()..");
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		DecisionApplication decisionApplication = new DecisionApplication();
		UloOrigAppProxy origApp = new UloOrigAppProxy();
		String buttonAction = request.getParameter("buttonAction");
		logger.debug("buttonAction : "+buttonAction);
		try{
			if(BUTTON_ACTION_SUBMIT.equals(buttonAction))
			{
				String appGroupNo = applicationGroup.getApplicationGroupNo();
				String idNo = "";
				if(applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT) != null)
				{
					idNo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT).getIdno();
				}
				else if(applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY) != null)
				{
					idNo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY).getIdno();
				}
				
				logger.debug("appGroupNo = " + appGroupNo);
				logger.debug("idNo = " + idNo);
				
				UtilityDAO utilDao = new UtilityDAOImpl();
				utilDao.deleteOldSubmitIATimestamp(appGroupNo, idNo, SUBMIT_IA_BLOCK_TIME);
				try
				{	
					utilDao.insertSubmitIATimestamp(appGroupNo, idNo);
				}
				catch(Exception e)
				{
					if(e instanceof DuplicateKeyException)
					{
						decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.BUSINESS_EXCEPTION);
						decisionApplication.setResultDesc(ERROR_SUBMIT_CLOSE_INTERVAL_1 + SUBMIT_IA_BLOCK_TIME + ERROR_SUBMIT_CLOSE_INTERVAL_2);
						return decisionApplication;
					}
					else
					{
						throw e;
					}
				}
			}
			
			UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
			String prevJobState = applicationGroup.getPrevJobState();
			String decisionPoint = getDecisionPoint(prevJobState,applicationGroup);
			String fraudActionType = origApp.getActionApplication(applicationGroup.getApplicationGroupId(), prevJobState);
			String DECISION_ACTION = applicationGroup.getFraudDecision();
			logger.debug("decisionPoint : "+decisionPoint);			
			logger.debug("prevJobState : "+prevJobState);	
			logger.debug("fraudActionType : "+fraudActionType);
			logger.debug("DECISION_ACTION before Fico : "+DECISION_ACTION);	
			String responseResult = "";
			boolean isCallDecision = false;
			if(!Util.empty(decisionPoint)){
				if(DECISION_ACTION_FAIL.equals(DECISION_ACTION) 
						|| (DECISION_ACTION_PASS.equals(DECISION_ACTION) && WorkflowAction.SUBMIT_AFTER_FRAUD.equals(fraudActionType))){				
					decisionApplication.setDecisionId(decisionPoint);
					DecisionServiceResponse decisionResponse = processDecisionAction(decisionPoint,userM.getUserNo(),applicationGroup);
					
					responseResult = decisionResponse.getResultCode();
					logger.debug("responseResult : "+responseResult);
					decisionApplication.setResultCode(responseResult);	
					decisionApplication.setResultDesc(decisionResponse.getResultDesc());
					decisionApplication.setErrorType(decisionResponse.getErrorType());
					isCallDecision = true;
				}
			}else{
				decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.SUCCESS);	
			}
			if(isCallDecision && !DecisionApplicationUtil.ResultCode.SUCCESS.equals(responseResult)) {
				/* change to use method from DecisionApplicationUtil						
					String idNo = "";
				if(applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT) != null) {
					idNo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT).getIdno();
				} else if(applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY) != null) {
					idNo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY).getIdno();
				}
				UtilityDAO utilDao = new UtilityDAOImpl();
				utilDao.deleteErrorSubmitIATimestamp(idNo);
				*/
				DecisionApplicationUtil.deleteLockSubmitIATimestamp(applicationGroup);
			}
			applicationGroup.setFraudActionType(fraudActionType);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.SYSTEM_EXCEPTION);
			decisionApplication.setResultDesc(e.getLocalizedMessage());
		}
		return decisionApplication;
	}
	private DecisionServiceResponse processDecisionAction(String decisionPoint,String userId,ApplicationGroupDataM applicationGroup){		
		DecisionServiceResponse decisionResponse  = new DecisionServiceResponse();
		try {
			DecisionServiceRequestDataM decisionRequest = new DecisionServiceRequestDataM();
			decisionRequest.setApplicationGroup(applicationGroup);
			decisionRequest.setDecisionPoint(decisionPoint);
			decisionRequest.setUserId(userId);
			decisionRequest.setRoleId(FormControl.getFormRoleId(request));	
			String transactionId = (String)request.getSession().getAttribute("transactionId");
			decisionRequest.setTransactionId(transactionId);
			 decisionResponse = DecisionApplicationUtil.requestDecisionService(decisionRequest);
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			decisionResponse.setResultCode(DecisionApplicationUtil.ResultCode.SYSTEM_EXCEPTION);
		}
		
		return decisionResponse;
	}
	private String getDecisionPoint(String prevJobState,ApplicationGroupDataM applicationGrop){
		if(DECISION_ACTION_FAIL.equals(applicationGrop.getFraudDecision())){
			return DecisionServiceUtil.DECISION_POINT.DECISION_POINT_DC;
		}else{
			if(WIP_JOBSTATE_DE1_1.contains(prevJobState)){
				return DecisionServiceUtil.DECISION_POINT.DECISION_POINT_PB1;
			}else if(WIP_JOBSTATE_DE1_2.contains(prevJobState)){
				return DecisionServiceUtil.DECISION_POINT.DECISION_POINT_DE1;
			}else if(WIP_JOBSTATE_DE2.contains(prevJobState)){
				return DecisionServiceUtil.DECISION_POINT.DECISION_POINT_FI;
			}else if(WIP_JOBSTATE_DV.contains(prevJobState)){
				return DecisionServiceUtil.DECISION_POINT.DECISION_POINT_DV2;
			}else if(WIP_JOBSTATE_CA.contains(prevJobState)){
				return DecisionServiceUtil.DECISION_POINT.DECISION_POINT_FI;
			}else if(WIP_JOBSTATE_VT.contains(prevJobState)){
				return DecisionServiceUtil.DECISION_POINT.DECISION_POINT_FI;
			}
		}
		return null;
	}

}
