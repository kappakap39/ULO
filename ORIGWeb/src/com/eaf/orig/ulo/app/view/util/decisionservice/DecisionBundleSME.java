package com.eaf.orig.ulo.app.view.util.decisionservice;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.decison.DecisionApplication;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM.CallerScreen;

public class DecisionBundleSME extends ProcessActionHelper {
	private static transient Logger logger = Logger.getLogger(DecisionBundleSME.class);
	String FINAL_APP_DECISION_REJECT = SystemConstant.getConstant("FINAL_APP_DECISION_REJECT");
	String DECISION_SERVICE_POINT_INCOME =SystemConstant.getConstant("DECISION_SERVICE_POINT_INCOME");	
	public Object processAction() {
		logger.debug("<<<<<<<<<<<<< CAll  decision BundleSME >>>>>>>>>>>> ");
		EntityFormHandler EntityForm = (EntityFormHandler) request.getSession().getAttribute("EntityForm");
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM) EntityForm.getObjectForm();		
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		DecisionApplication decisionApplication = new DecisionApplication();
		decisionApplication.setDecisionId(DECISION_SERVICE_POINT_INCOME);
		try {
			DecisionServiceRequestDataM requestDecision = new DecisionServiceRequestDataM();
			requestDecision.setApplicationGroup(applicationGroup);
			requestDecision.setDecisionPoint(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_INCOME);
			requestDecision.setRoleId(FormControl.getFormRoleId(request));
			requestDecision.setUserId(userM.getUserName());
			requestDecision.setCallerScreen(CallerScreen.SME_POPUP);
			String transactionId = (String)request.getSession().getAttribute("transactionId");
			requestDecision.setTransactionId(transactionId);
			DecisionServiceResponse responseData = DecisionApplicationUtil.requestDecisionService(requestDecision);
			
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
