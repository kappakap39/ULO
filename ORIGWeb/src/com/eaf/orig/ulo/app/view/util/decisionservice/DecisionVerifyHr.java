package com.eaf.orig.ulo.app.view.util.decisionservice;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.decison.DecisionApplication;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM.CallerScreen;
import com.google.gson.Gson;

public class DecisionVerifyHr extends ProcessActionHelper {
	private static transient Logger logger = Logger.getLogger(DecisionVerifyHr.class);
	String PERSONAL_RELATION_APPLICATION_LEVEL =SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String VER_HR_RESULT_QUESTION = SystemConstant.getConstant("VER_HR_RESULT_QUESTION");
	String VER_HR_RESULT_RESIGN = SystemConstant.getConstant("VER_HR_RESULT_RESIGN");
	String VER_HR_RESULT_HR_NOT_DATA = SystemConstant.getConstant("VER_HR_RESULT_HR_NOT_DATA");
	String FINAL_APP_DECISION_REJECT =SystemConstant.getConstant("FINAL_APP_DECISION_REJECT");
	String DECISION_SERVICE_POINT_DV1 =SystemConstant.getConstant("DECISION_SERVICE_POINT_DV1");
	@Override
	public Object processAction() {
		logger.debug("<<<<<<<<<<<<< CAll DECISION VerifyHr >>>>>>>>>>>> ");		
		DecisionApplication decisionApplication = new DecisionApplication();
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		try {
			if(!Util.empty(personalInfo)){
				DecisionServiceRequestDataM reqApplication = new DecisionServiceRequestDataM();
				reqApplication.setApplicationGroup(applicationGroup);
				reqApplication.setDecisionPoint(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_DV1);
				reqApplication.setRoleId(FormControl.getFormRoleId(request));
				reqApplication.setUserId(userM.getUserName());
				 					
				logger.debug("personal >>> "+personalInfo);
				if(!Util.empty(personalInfo)){
					VerificationResultDataM verificationResult= personalInfo.getVerificationResult();
					logger.debug("verificationResult >> "+verificationResult);
					if(!Util.empty(verificationResult)){
						IdentifyQuestionDataM identVerHrResultQuestion = verificationResult.getIndentifyQuesitionNo(VER_HR_RESULT_QUESTION);
						if(!Util.empty(identVerHrResultQuestion)){
							String QuesitionNoAns =identVerHrResultQuestion.getCustomerAnswer();
							logger.debug("QuesitionNoAns >> "+QuesitionNoAns);
							if(VER_HR_RESULT_RESIGN.equals(QuesitionNoAns) || VER_HR_RESULT_HR_NOT_DATA.equals(QuesitionNoAns)){
								reqApplication.setCallerScreen(CallerScreen.HR_VER_POPUP);
								String transactionId = (String)request.getSession().getAttribute("transactionId");
								reqApplication.setTransactionId(transactionId);
								DecisionServiceResponse responseData = DecisionApplicationUtil.requestDecisionService(reqApplication);	
								  
								String responseResult = responseData.getResultCode();
								logger.debug("responseResult >> "+responseResult);
								decisionApplication.setResultCode(responseResult);		
								decisionApplication.setResultDesc(responseData.getResultDesc());
								decisionApplication.setErrorType(responseData.getErrorType());
								if(DecisionApplicationUtil.ResultCode.SUCCESS.equals(responseResult)){
									Gson gson = new Gson();
									logger.debug("decisionVerifyHr Response >>> "+gson.toJson(responseResult));							
									logger.debug("applicationGroup.getDecisionAction() >>> "+applicationGroup.getDecisionAction());							
									String decisionAction = DecisionApplicationUtil.getAppGroupActionDecision(applicationGroup);
									logger.debug("decisionAction >> "+decisionAction);
									if(!Util.empty(decisionAction) && FINAL_APP_DECISION_REJECT.equals(decisionAction)){
										decisionApplication.setDecision(FINAL_APP_DECISION_REJECT);
									}
								}
							}
						}
					}
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
