package com.eaf.orig.ulo.app.view.util.fico;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.verification.VerifyUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.fico.FicoApplication;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM.CallerScreen;

public class FicoVerifyCustomer extends ProcessActionHelper {
	private static transient Logger logger = Logger.getLogger(FicoVerifyCustomer.class);
	String VALIDATION_STATUS_COMPLETED_NOT_PASS = SystemConstant.getConstant("VALIDATION_STATUS_COMPLETED_NOT_PASS");
	String VER_CUS_COMPLETE_VERIFY_COMPLETED = SystemConstant.getConstant("VER_CUS_COMPLETE_VERIFY_COMPLETED");
	String FINAL_APP_DECISION_REJECT = SystemConstant.getConstant("FINAL_APP_DECISION_REJECT");
	String FICO_DECISION_POINT_DV1 = SystemConstant.getConstant("FICO_DECISION_POINT_DV1");
	String FICO_DECISION_POINT_DC = SystemConstant.getConstant("FICO_DECISION_POINT_DC");
	public Object processAction() {
		FicoApplication ficoApplication = new FicoApplication();
		EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");		
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
		PersonalInfoDataM personalApplicant= PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		VerificationResultDataM verificationResult = personalApplicant.getVerificationResult();		
		if(null == verificationResult){
			verificationResult = new VerificationResultDataM();
			personalApplicant.setVerificationResult(verificationResult);
		}
		String verCusResultCode =  VerifyUtil.VerifyResult.getVerifyCustomerResult(applicationGroup);
		String verCusComplete = verificationResult.getVerCusComplete();
		logger.debug("verCusResultCode : "+verCusResultCode);
		logger.debug("verCusComplete : "+verCusComplete);		
		if(VALIDATION_STATUS_COMPLETED_NOT_PASS.equals(verCusResultCode) && VER_CUS_COMPLETE_VERIFY_COMPLETED.equals(verCusComplete)){
			FicoRequest ficoRequest = new FicoRequest();
				ficoRequest.setApplicationGroup(applicationGroup);
				ficoRequest.setFicoFunction(FICO_DECISION_POINT_DV1);	
				ficoRequest.setRoleId(FormControl.getFormRoleId(request));
			FicoResponse ficoResponse = FicoApplicationUtil.requestFico(ficoRequest,CallerScreen.CUST_VER_POPUP);
			String ficoResponseResult = ficoResponse.getResultCode();
			logger.debug("ficoResponseResult : "+ficoResponseResult);
			ficoApplication.setResultCode(ficoResponseResult);	
			ficoApplication.setResultDesc(ficoResponse.getResultDesc());
			if(FicoApplicationUtil.ResultCode.SUCCESS.equals(ficoResponseResult)){
				verificationResult.setFicoFlag(MConstant.FLAG.YES);
				String decisionAction = FicoApplicationUtil.getAppGroupActionDecision(applicationGroup);
				logger.debug("decisionAction : "+decisionAction);					
				if(!Util.empty(decisionAction) && FINAL_APP_DECISION_REJECT.equals(decisionAction)){
					String documentCompleteFlag = applicationGroup.getDocCompletedFlag();
					String documentChecklistFlag = applicationGroup.getDocumentChecklistFlag();
					logger.debug("documentCompleteFlag : "+documentCompleteFlag);
					logger.debug("documentChecklistFlag : "+documentChecklistFlag);
					if(MConstant.FLAG.NO.equals(documentCompleteFlag) || MConstant.FLAG.YES.equals(documentChecklistFlag)){
						FicoRequest dcFicoRequest = new FicoRequest();
							dcFicoRequest.setApplicationGroup(applicationGroup);
							dcFicoRequest.setFicoFunction(FICO_DECISION_POINT_DC);		
							dcFicoRequest.setRoleId(FormControl.getFormRoleId(request));
						FicoResponse dcFicoResponse = FicoApplicationUtil.requestFico(dcFicoRequest,CallerScreen.CUST_VER_POPUP);
						String dcFicoResponseResult = dcFicoResponse.getResultCode();
						logger.debug("dcFicoResponseResult : "+dcFicoResponseResult);
						ficoApplication.setResultCode(dcFicoResponseResult);	
						ficoApplication.setResultDesc(dcFicoResponse.getResultDesc());
						if(FicoApplicationUtil.ResultCode.SUCCESS.equals(dcFicoResponseResult)){
							ficoApplication.setDecision(FINAL_APP_DECISION_REJECT);
						}
					}
				}else{
					ficoApplication.setDecision(decisionAction);
				}
			}
		}		
		return ficoApplication;
	}
}
