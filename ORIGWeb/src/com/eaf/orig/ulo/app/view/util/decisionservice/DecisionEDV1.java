package com.eaf.orig.ulo.app.view.util.decisionservice;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.decison.DecisionApplication;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM.CallerScreen;

public class DecisionEDV1 extends ProcessActionHelper {
	private static transient Logger logger = Logger.getLogger(DecisionEDV1.class);
	String VALIDATION_STATUS_COMPLETED = SystemConstant.getConstant("VALIDATION_STATUS_COMPLETED");
	String FINAL_APP_DECISION_REJECT = SystemConstant.getConstant("FINAL_APP_DECISION_REJECT");
	String DECISION_SERVICE_POINT_DV1 =SystemConstant.getConstant("DECISION_SERVICE_POINT_DV1");	
	
	public Object processAction() {
		DecisionApplication decisionApplication = new DecisionApplication();
		logger.debug("<<<<<<<<<<<<< CAll DECISION VerifyWebsite >>>>>>>>>>>> ");
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		PersonalInfoDataM personalApplicant= PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		try{
			if(!Util.empty(personalApplicant)){
				VerificationResultDataM verificationResultM = personalApplicant.getVerificationResult();
				if(!Util.empty(verificationResultM)){
					if(VALIDATION_STATUS_COMPLETED.equals(verificationResultM.getVerWebResultCode())) {
						//Document SLA
						String documentCompleteFlag = applicationGroup.getDocCompletedFlag();
						logger.debug("documentCompleteFlag : "+documentCompleteFlag);
						if(MConstant.FLAG.NO.equals(documentCompleteFlag)){
							String docSLAtype = "";
							if(!Util.empty(applicationGroup.getDocumentCheckLists())){
								for(DocumentCheckListDataM doc : applicationGroup.getDocumentCheckLists()){	
									if(!Util.empty(doc.getDocTypeId()) && doc.getDocTypeId().equals(SystemConstant.getConstant("INCOME_DOC"))){
										logger.debug("DocType#######"+doc.getDocTypeId());
										docSLAtype =SystemConstant.getConstant("DOC_SLA_TYPE04");
										logger.debug("LoopFor04##############");
										break;
								}else{
										docSLAtype =SystemConstant.getConstant("DOC_SLA_TYPE01");
									}
								}		
							}
							if(!Util.empty(docSLAtype)){
								applicationGroup.setDocumentSLAType(docSLAtype);
							}
						}
						
						DecisionServiceRequestDataM reqApplication = new DecisionServiceRequestDataM();
						reqApplication.setApplicationGroup(applicationGroup);
						reqApplication.setDecisionPoint(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_EDV1);
						reqApplication.setRoleId(FormControl.getFormRoleId(request));
						reqApplication.setUserId(userM.getUserName());
						reqApplication.setCallerScreen(CallerScreen.WEB_VER_POPUP);
						String transactionId = (String)request.getSession().getAttribute("transactionId");
						reqApplication.setTransactionId(transactionId);
						DecisionServiceResponse responseData = DecisionApplicationUtil.requestDecisionService(reqApplication);	
						  
						String responseResult = responseData.getResultCode();
						logger.debug("responseResult >> "+responseResult);
						decisionApplication.setResultCode(responseResult);
						decisionApplication.setResultDesc(responseData.getResultDesc());
						decisionApplication.setErrorType(responseData.getErrorType());
						if(DecisionApplicationUtil.ResultCode.SUCCESS.equals(responseResult)){
							String decisionAction = DecisionApplicationUtil.getAppGroupActionDecision(applicationGroup);
							logger.debug("DecisionAction "+decisionAction);						
							if(!Util.empty(decisionAction) && FINAL_APP_DECISION_REJECT.equals(decisionAction)){
								decisionApplication.setDecision(FINAL_APP_DECISION_REJECT);
							}else{
								String EXECUTE_KEY_PROGRAM_NEW_INCOME = SystemConstant.getConstant("EXECUTE_KEY_PROGRAM_NEW_INCOME");	
								if(applicationGroup.foundReExecuteKeyProgramFlag(EXECUTE_KEY_PROGRAM_NEW_INCOME)){
									applicationGroup.setDecisionLog(ApplicationGroupDataM.DecisionLog.REQUIRE_VERIFY_INCOME);
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
