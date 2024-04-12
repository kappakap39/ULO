package com.eaf.orig.ulo.app.view.util.decisionservice;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.view.util.kpl.KPLUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.decison.DecisionApplication;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM;

public class _DecisionDE2 extends ProcessActionHelper  {
	private static transient Logger logger = Logger.getLogger(_DecisionDE2.class);
	String DECISION_SERVICE_POINT_DE2 =SystemConstant.getConstant("DECISION_SERVICE_POINT_DE2");
	String DECISION_SERVICE_POINT_POST_APPROVAL =SystemConstant.getConstant("DECISION_SERVICE_POINT_POST_APPROVAL");
	String[] JOBSTATE_REJECT = SystemConstant.getArrayConstant("JOBSTATE_REJECT");

	public Object processAction() {
		logger.debug("decision DE2.processAction()..");
		ArrayList<String> JOBSTATE_REJECTS = new ArrayList<String>(Arrays.asList(JOBSTATE_REJECT));
		DecisionApplication decisionApplication = new DecisionApplication();
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		String JOB_STATE = applicationGroup.getJobState();
		logger.debug(">>>JOB_STATE>>"+JOB_STATE);
		logger.debug(">>>JOBSTATE_REJECT>>"+JOBSTATE_REJECT.toString());
		boolean isKPL = KPLUtil.isKPL(applicationGroup);
		try{
			//#Fix CR085-4 call every case
//			if(JOBSTATE_REJECTS.contains(JOB_STATE)){
			//Fix Defect 1964 KPL Should skip calling ODM - POST_APPROVE at Station DE2
			if(!isKPL || (isKPL && JOBSTATE_REJECTS.contains(applicationGroup.getJobState())))
			{
				UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
				DecisionServiceRequestDataM requestDecision = new DecisionServiceRequestDataM();
				requestDecision.setApplicationGroup(applicationGroup);
				requestDecision.setDecisionPoint(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_POST);
				requestDecision.setRoleId(FormControl.getFormRoleId(request));
				requestDecision.setUserId(userM.getUserName());
				String transactionId = (String)request.getSession().getAttribute("transactionId");
				requestDecision.setTransactionId(transactionId);
				DecisionServiceResponse responseData= DecisionApplicationUtil.requestDecisionService(requestDecision);	
				String responseResult = responseData.getResultCode();
				logger.debug("responseResult >> "+responseResult);
				decisionApplication.setResultCode(responseResult);
				decisionApplication.setResultDesc(responseData.getResultDesc());
				decisionApplication.setErrorType(responseData.getErrorType());
				decisionApplication.setDecisionId(DECISION_SERVICE_POINT_POST_APPROVAL);
			}
//			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.SYSTEM_EXCEPTION);
			decisionApplication.setResultDesc(e.getLocalizedMessage());
		}
		return decisionApplication;
	}

}
