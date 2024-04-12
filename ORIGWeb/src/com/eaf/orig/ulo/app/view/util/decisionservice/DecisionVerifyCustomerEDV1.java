package com.eaf.orig.ulo.app.view.util.decisionservice;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.view.form.verification.VerifyUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.decison.DecisionApplication;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM.CallerScreen;

public class DecisionVerifyCustomerEDV1 extends ProcessActionHelper {
	private static transient Logger logger = Logger.getLogger(DecisionVerifyCustomerEDV1.class);
	String VALIDATION_STATUS_COMPLETED_NOT_PASS = SystemConstant.getConstant("VALIDATION_STATUS_COMPLETED_NOT_PASS");
	String VER_CUS_COMPLETE_VERIFY_COMPLETED = SystemConstant.getConstant("VER_CUS_COMPLETE_VERIFY_COMPLETED");
	String FINAL_APP_DECISION_REJECT = SystemConstant.getConstant("FINAL_APP_DECISION_REJECT");
	String DECISION_SERVICE_POINT_DV1 = SystemConstant.getConstant("DECISION_SERVICE_POINT_DV1");
	String CACHE_FOLLOW_UP_DECISION_RESULT = SystemConstant.getConstant("CACHE_FOLLOW_UP_DECISION_RESULT");
	public Object processAction() {
		DecisionApplication decisionApplication = new DecisionApplication();
		decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.SUCCESS);
//		EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");		
//		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
//		PersonalInfoDataM personalApplicant= PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
//		VerificationResultDataM verificationResult = personalApplicant.getVerificationResult();		
//		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
//		if(null == verificationResult){
//			verificationResult = new VerificationResultDataM();
//			personalApplicant.setVerificationResult(verificationResult);
//		}
//		String verCusResultCode =  VerifyUtil.VerifyResult.getVerifyCustomerResult(applicationGroup);
//		String verCusComplete = verificationResult.getVerCusComplete();
//		logger.debug("verCusResultCode : "+verCusResultCode);
//		logger.debug("verCusComplete : "+verCusComplete);	
//		try {
//			if(VALIDATION_STATUS_COMPLETED_NOT_PASS.equals(verCusResultCode) && VER_CUS_COMPLETE_VERIFY_COMPLETED.equals(verCusComplete)){
//
//					
//				//Document SLA
//				String documentCompleteFlag = applicationGroup.getDocCompletedFlag();
//				logger.debug("documentCompleteFlag : "+documentCompleteFlag);
//				if(MConstant.FLAG.NO.equals(documentCompleteFlag)){
//					String docSLAtype = "";
//					for(DocumentCheckListDataM doc : applicationGroup.getDocumentCheckLists()){
//						if(!Util.empty(doc.getDocTypeId()) && doc.getDocTypeId().equals(SystemConstant.getConstant("INCOME_DOC"))){
//							logger.debug("DocType#######"+doc.getDocTypeId());
//							docSLAtype =SystemConstant.getConstant("DOC_SLA_TYPE04");
//							logger.debug("LoopFor04##############");
//							break;
//					}else{
//							docSLAtype =SystemConstant.getConstant("DOC_SLA_TYPE01");
//						}
//					}
//					
//					if(!Util.empty(docSLAtype)){
//						applicationGroup.setDocumentSLAType(docSLAtype);
//					}
//				}
//
//				
//				DecisionServiceRequestDataM reqApplication = new DecisionServiceRequestDataM();
//				reqApplication.setApplicationGroup(applicationGroup);
//				reqApplication.setDecisionPoint(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_EDV1);
//				reqApplication.setRoleId(FormControl.getFormRoleId(request));
//				reqApplication.setUserId(userM.getUserName());
//				reqApplication.setCallerScreen(CallerScreen.CUST_VER_POPUP);
//				String transactionId = (String)request.getSession().getAttribute("transactionId");
//				reqApplication.setTransactionId(transactionId);
//				DecisionServiceResponse responseData = DecisionApplicationUtil.requestDecisionService(reqApplication);	
//				String responseResult = responseData.getResultCode();
//				logger.debug("decision ResponseResult : "+responseResult);
//				decisionApplication.setResultCode(responseResult);	
//				decisionApplication.setResultDesc(responseData.getResultDesc());
//				decisionApplication.setErrorType(responseData.getErrorType());
//				if(DecisionApplicationUtil.ResultCode.SUCCESS.equals(responseResult)){
//					verificationResult.setFicoFlag(MConstant.FLAG.YES);
//					String decisionAction = DecisionApplicationUtil.getAppGroupActionDecision(applicationGroup);
//					logger.debug("decisionAction : "+decisionAction);	
//					decisionApplication.setDecision(decisionAction);
//					/*if(!Util.empty(decisionAction) && FINAL_APP_DECISION_REJECT.equals(decisionAction)){
//						String documentCompleteFlag = applicationGroup.getDocCompletedFlag();
//						String documentChecklistFlag = applicationGroup.getDocumentChecklistFlag();
//						logger.debug("documentCompleteFlag : "+documentCompleteFlag);
//						logger.debug("documentChecklistFlag : "+documentChecklistFlag);
//						if(MConstant.FLAG.NO.equals(documentCompleteFlag) || MConstant.FLAG.YES.equals(documentChecklistFlag)){
//							
//							DecisionServiceRequestDataM reqApplicationDc = new DecisionServiceRequestDataM();
//							reqApplicationDc.setApplicationGroup(applicationGroup);
//							reqApplicationDc.setDecisionPoint(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_DC);
//							reqApplicationDc.setRoleId(FormControl.getFormRoleId(request));
//							reqApplicationDc.setUserId(userM.getUserName());
//							reqApplicationDc.setCallerScreen(CallerScreen.CUST_VER_POPUP);
//							 
//							DecisionServiceResponseDataM responseDataDC = DecisionApplicationUtil.requestDecisionService(reqApplicationDc);								
//							String dcResponseResult = responseDataDC.getResultCode();
//							logger.debug("dcResponseResult : "+dcResponseResult);
//							ficoApplication.setResultCode(dcResponseResult);	
//							ficoApplication.setResultDesc(responseDataDC.getResultDesc());
//							if(DecisionApplicationUtil.ResultCode.SUCCESS.equals(dcResponseResult)){
//								ficoApplication.setDecision(FINAL_APP_DECISION_REJECT);
//							}
//						}
//					}else{
//						ficoApplication.setDecision(decisionAction);
//					}*/
//				}
//			}
//		}catch(Exception e){
//			logger.fatal("ERROR",e);
//			decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.SYSTEM_EXCEPTION);	
//			decisionApplication.setResultDesc(e.getLocalizedMessage());
//		}
		return decisionApplication;
	}

}
