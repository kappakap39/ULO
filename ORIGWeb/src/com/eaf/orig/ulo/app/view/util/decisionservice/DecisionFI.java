package com.eaf.orig.ulo.app.view.util.decisionservice;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.view.util.kpl.KPLUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.decison.DecisionApplication;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM.CallerScreen;

public class DecisionFI extends ProcessActionHelper {
	private static transient Logger logger = Logger.getLogger(DecisionFI.class);
	String DECISION_SERVICE_POINT_FI =SystemConstant.getConstant("DECISION_SERVICE_POINT_FI");
	
	//KPL Additional
	String DECISION_SERVICE_FOLLOW_UP = SystemConstant.getConstant("DECISION_SERVICE_FOLLOW_UP");
	String DECISION_SERVICE_APPPROVE = SystemConstant.getConstant("DECISION_SERVICE_APPPROVE");
	
	public Object processAction() {
		logger.debug(" Decision FI.processAction()..");
		DecisionApplication decisionApplication = new DecisionApplication();
		try{
			ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
			UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
			String callEscalateFlag =applicationGroup.getCalledEscalateFlag();
			DecisionServiceRequestDataM     decisionRequest = new DecisionServiceRequestDataM();
			decisionRequest.setApplicationGroup(applicationGroup);
			decisionRequest.setDecisionPoint(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_FI);
			decisionRequest.setUserId(userM.getUserNo());
			decisionRequest.setRoleId(FormControl.getFormRoleId(request));
			String transactionId = (String)request.getSession().getAttribute("transactionId");
			decisionRequest.setTransactionId(transactionId);
			logger.debug("applicationGroup.getCallEscalateFlag() >> "+applicationGroup.getCalledEscalateFlag());	
			decisionRequest.setCallerScreen(MConstant.FLAG_N.equals(callEscalateFlag)?CallerScreen.CA_SUMMARY_UPDATE_OR:null);
			
			DecisionServiceResponse decisionResponse = DecisionApplicationUtil.requestDecisionService(decisionRequest);
					
			String responseResult = decisionResponse.getResultCode();
			logger.debug("responseResult >> "+responseResult);
			decisionApplication.setResultCode(responseResult);	
			decisionApplication.setResultDesc(decisionResponse.getResultDesc());
			decisionApplication.setErrorType(decisionResponse.getErrorType());
			if(DecisionApplicationUtil.ResultCode.SUCCESS.equals(responseResult)){			
				decisionApplication.setDecisionId(DECISION_SERVICE_POINT_FI);
				logger.debug("DECISION_DECISION_POINT_FI >> "+DECISION_SERVICE_POINT_FI);
				logger.debug("DecisionAction >> "+applicationGroup.getDecisionAction());		
				if(MConstant.FLAG_N.equals(applicationGroup.getCalledEscalateFlag())){
					decisionApplication.setCallEscalateFlag(MConstant.FLAG_Y);
				}else{
					decisionApplication.setCallEscalateFlag(MConstant.FLAG_N);
				}
				logger.debug("decisionApplication.getCallEscalateFlag() >> "+decisionApplication.getCallEscalateFlag());
				
				//KPL Additional - Saving Plus Logic
				if(KPLUtil.isKPL(applicationGroup))
				{
					ApplicationDataM kplApp = KPLUtil.getKPLApplication(applicationGroup);
					if(DECISION_SERVICE_APPPROVE.equals(kplApp.getRecommendDecision())
						 && KPLUtil.isSavingPlus(kplApp.getSavingPlusFlag()))
						{
							if(!KPLUtil.haveKbankSavingDoc(applicationGroup))
							{
								logger.debug("SavingPlusFlag checked + not have KbankSavingDoc = SEND TO PEGA");
								KPLUtil.addFollowSavingPlusDocReason(applicationGroup); //create SavingPlus follow-up detail
								applicationGroup.setDecisionAction("FUSP");
								decisionApplication.setSavingPlusFlag(MConstant.FLAG.ACTIVE);
							}
							else
							{
								//Sent back to DV
								applicationGroup.setDecisionAction(DECISION_SERVICE_APPPROVE);
							}
					}
				}
			}
			if(DecisionApplicationUtil.isApplyTypeError(applicationGroup.getApplyTypeStatus())){
				decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.BUSINESS_EXCEPTION);
				decisionApplication.setResultDesc(MessageErrorUtil.getText("ERROR_APPLY_TYPE"));
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.SYSTEM_EXCEPTION);
			decisionApplication.setResultDesc(e.getLocalizedMessage());
		}
		return decisionApplication;
	}

}
