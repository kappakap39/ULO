package com.eaf.orig.ulo.app.view.util.fico;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.fico.FicoApplication;
import com.google.gson.Gson;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM.CallerScreen;

public class FicoVerifyHr extends ProcessActionHelper {
	private static transient Logger logger = Logger.getLogger(FicoVerifyHr.class);
	String PERSONAL_RELATION_APPLICATION_LEVEL =SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String VER_HR_RESULT_QUESTION = SystemConstant.getConstant("VER_HR_RESULT_QUESTION");
	String VER_HR_RESULT_RESIGN = SystemConstant.getConstant("VER_HR_RESULT_RESIGN");
	String VER_HR_RESULT_HR_NOT_DATA = SystemConstant.getConstant("VER_HR_RESULT_HR_NOT_DATA");
	String FINAL_APP_DECISION_REJECT =SystemConstant.getConstant("FINAL_APP_DECISION_REJECT");
	String FICO_DECISION_POINT_DV1 =SystemConstant.getConstant("FICO_DECISION_POINT_DV1");
	@Override
	public Object processAction() {
		logger.debug("<<<<<<<<<<<<< CAll FICO FicoVerifyHr >>>>>>>>>>>> ");
		FicoApplication ficoApplication = new FicoApplication();
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		if(!Util.empty(personalInfo)){
			FicoRequest ficoRequest = new FicoRequest();
				ficoRequest.setApplicationGroup(applicationGroup);
				ficoRequest.setFicoFunction(FICO_DECISION_POINT_DV1);	
				ficoRequest.setRoleId(FormControl.getFormRoleId(request));
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
							FicoResponse ficoResponse = FicoApplicationUtil.requestFico(ficoRequest,CallerScreen.HR_VER_POPUP);
							String ficoResponseResult = ficoResponse.getResultCode();
							logger.debug("ficoResponseResult >> "+ficoResponseResult);
							ficoApplication.setResultCode(ficoResponseResult);		
							ficoApplication.setResultDesc(ficoResponse.getResultDesc());
							if(FicoApplicationUtil.ResultCode.SUCCESS.equals(ficoResponseResult)){
								Gson gson = new Gson();
//								logger.debug("FicoVerifyHr Response >>> "+gson.toJson(ficoResponse));							
								logger.debug("applicationGroup.getDecisionAction() >>> "+applicationGroup.getDecisionAction());							
								String decisionAction = FicoApplicationUtil.getAppGroupActionDecision(applicationGroup);
								logger.debug("decisionAction >> "+decisionAction);
								if(!Util.empty(decisionAction) && FINAL_APP_DECISION_REJECT.equals(decisionAction)){
									ficoApplication.setDecision(FINAL_APP_DECISION_REJECT);
								}
							}
						}
					}
				}
			}
		}		
		return ficoApplication;
	}

}
