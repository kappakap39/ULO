package com.eaf.orig.ulo.app.view.util.fico;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.view.util.uloOrigApp.UloOrigAppProxy;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.fico.FicoApplication;
import com.orig.bpm.workflow.handle.WorkflowAction;

public class FicoFRAUD extends ProcessActionHelper {
	private static transient Logger logger = Logger.getLogger(FicoFRAUD.class);
	String FICO_DECISION_POINT_DE1 =SystemConstant.getConstant("FICO_DECISION_POINT_DE1");
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
	
	String FICO_FRAUD_DECISION_POINT_DE1_1 = SystemConstant.getConstant("FICO_FRAUD_DECISION_POINT_DE1_1"); 
	String FICO_FRAUD_DECISION_POINT_DE1_2 = SystemConstant.getConstant("FICO_FRAUD_DECISION_POINT_DE1_2"); 
	String FICO_FRAUD_DECISION_POINT_DE2 = SystemConstant.getConstant("FICO_FRAUD_DECISION_POINT_DE2"); 
	String FICO_FRAUD_DECISION_POINT_DV = SystemConstant.getConstant("FICO_FRAUD_DECISION_POINT_DV"); 
	String FICO_FRAUD_DECISION_POINT_CA = SystemConstant.getConstant("FICO_FRAUD_DECISION_POINT_CA"); 
	String FICO_FRAUD_DECISION_POINT_VT = SystemConstant.getConstant("FICO_FRAUD_DECISION_POINT_VT"); 
	
	public Object processAction()  {
		logger.debug("FicoFRAUD.processAction()..");
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		FicoApplication ficoApplication = new FicoApplication();
		UloOrigAppProxy origApp = new UloOrigAppProxy(); 
		try{
			String prevJobState = applicationGroup.getPrevJobState();
			String ficoId = getFicoId(prevJobState);
			String fraudActionType = origApp.getActionApplication(applicationGroup.getApplicationGroupId(), prevJobState);
			String DECISION_ACTION = applicationGroup.getFraudDecision();
			logger.debug("ficoId : "+ficoId);			
			logger.debug("prevJobState : "+prevJobState);	
			logger.debug("fraudActionType : "+fraudActionType);
			logger.debug("DECISION_ACTION before Fico : "+DECISION_ACTION);	
			if(!Util.empty(ficoId)){
				if(DECISION_ACTION_FAIL.equals(DECISION_ACTION) 
						|| (DECISION_ACTION_PASS.equals(DECISION_ACTION) && WorkflowAction.SUBMIT_AFTER_FRAUD.equals(fraudActionType))){				
					ficoApplication.setFicoId(ficoId);
					FicoResponse ficoResponse = processFicoAction(ficoApplication,applicationGroup);
					String ficoResponseResult = ficoResponse.getResultCode();
					logger.debug("ficoResponseResult : "+ficoResponseResult);
					ficoApplication.setResultCode(ficoResponseResult);	
					ficoApplication.setResultDesc(ficoResponse.getResultDesc());
				}
			}else{
				ficoApplication.setResultCode(FicoApplicationUtil.ResultCode.SUCCESS);	
			}
			applicationGroup.setFraudActionType(fraudActionType);
		}catch(ApplicationException e){
			logger.fatal("ERROR",e);
			ficoApplication.setResultCode(FicoApplicationUtil.ResultCode.SYSTEM_EXCEPTION);
			ficoApplication.setResultDesc(e.getLocalizedMessage());
		}
		return ficoApplication;
	}
	private FicoResponse processFicoAction(FicoApplication ficoApplication,ApplicationGroupDataM applicationGroup){		
		FicoRequest ficoRequest = new FicoRequest();
		ficoRequest.setApplicationGroup(applicationGroup);
		ficoRequest.setFicoFunction(ficoApplication.getFicoId());	
		ficoRequest.setRoleId(FormControl.getFormRoleId(request));
		FicoResponse ficoResponse = FicoApplicationUtil.requestFico(ficoRequest);
		return ficoResponse;
	}
	private String getFicoId(String prevJobState){
		if(WIP_JOBSTATE_DE1_1.contains(prevJobState)){
			return FICO_FRAUD_DECISION_POINT_DE1_1;
		}else if(WIP_JOBSTATE_DE1_2.contains(prevJobState)){
			return FICO_FRAUD_DECISION_POINT_DE1_2;
		}else if(WIP_JOBSTATE_DE2.contains(prevJobState)){
			return FICO_FRAUD_DECISION_POINT_DE2;
		}else if(WIP_JOBSTATE_DV.contains(prevJobState)){
			return FICO_FRAUD_DECISION_POINT_DV;
		}else if(WIP_JOBSTATE_CA.contains(prevJobState)){
			return FICO_FRAUD_DECISION_POINT_CA;
		}else if(WIP_JOBSTATE_VT.contains(prevJobState)){
			return FICO_FRAUD_DECISION_POINT_VT;
		}
		return null;
	}
}
