package com.eaf.orig.ulo.app.view.util.decisionservice;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.decison.DecisionApplication;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM;

public class DecisionOccupation extends ProcessActionHelper {
	private static transient Logger logger = Logger.getLogger(DecisionOccupation.class);
	String VALIDATION_STATUS_COMPLETED = SystemConstant.getConstant("VALIDATION_STATUS_COMPLETED");
	String FINAL_APP_DECISION_REJECT = SystemConstant.getConstant("FINAL_APP_DECISION_REJECT");
	String DECISION_SERVICE_POINT_DE1 =SystemConstant.getConstant("DECISION_SERVICE_POINT_DE1");	
	public Object processAction() {
		DecisionApplication decisionApplication = new DecisionApplication();
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		PersonalInfoDataM personalInfo= PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		personalInfo.setDisplayEditBTN(MConstant.FLAG_N);
		
		DecisionServiceRequestDataM reqApplication = new DecisionServiceRequestDataM();
		reqApplication.setApplicationGroup(applicationGroup);
		reqApplication.setDecisionPoint(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_DE1);
		reqApplication.setRoleId(FormControl.getFormRoleId(request));
		reqApplication.setUserId(userM.getUserName());
		try {
			String transactionId = (String)request.getSession().getAttribute("transactionId");
			reqApplication.setTransactionId(transactionId);
			DecisionServiceResponse responseData = DecisionApplicationUtil.requestDecisionService(reqApplication);	
			String responseResult = responseData.getResultCode();
			logger.debug("responseResult >> "+responseResult);
			decisionApplication.setResultCode(responseResult);	
			decisionApplication.setResultDesc(responseData.getResultDesc());
			decisionApplication.setErrorType(responseData.getErrorType());
			if(DecisionApplicationUtil.ResultCode.SUCCESS.equals(responseResult)){
				logger.debug("DecisionAction "+applicationGroup.getDecisionAction());
				if(FINAL_APP_DECISION_REJECT.equals(applicationGroup.getDecisionAction())){
					decisionApplication.setDecision(FINAL_APP_DECISION_REJECT);
				} 
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.SYSTEM_EXCEPTION);	
			decisionApplication.setResultDesc(e.getLocalizedMessage());
		}
		return decisionApplication;
	}

}
