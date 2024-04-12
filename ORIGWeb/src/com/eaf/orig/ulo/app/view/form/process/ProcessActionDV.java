package com.eaf.orig.ulo.app.view.form.process;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.im.rest.common.model.ServiceResponse;
import com.eaf.orig.ulo.app.view.form.verification.VerifyUtil;
import com.eaf.orig.ulo.control.util.DocumentCheckListUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.orig.bpm.workflow.handle.WorkflowAction;

public class ProcessActionDV extends ProcessActionHelper {
	private static transient Logger logger = Logger.getLogger(ProcessActionDV.class);
	String STATUS_COMPLETED_NOT_PASS = SystemConstant.getConstant("VALIDATION_STATUS_COMPLETED_NOT_PASS");
	String VER_CUS_COMPLETE_CANCEL = SystemConstant.getConstant("VER_CUS_COMPLETE_CANCEL");
	String VER_HR_RESULT_QUESTION = SystemConstant.getConstant("VER_HR_RESULT_QUESTION");
	String VER_HR_RESULT_QUESTION_DEPTH_CHECKING = SystemConstant.getConstant("VER_HR_RESULT_QUESTION_DEPTH_CHECKING");	
	String BUTTON_ACTION_SUBMIT = SystemConstant.getConstant("BUTTON_ACTION_SUBMIT");
	@Override
	public HashMap<String, Object> validateAction(HttpServletRequest request,Object objectForm) {
		FormErrorUtil formError = new FormErrorUtil();		
		String buttonAction = getButtonAction();
		logger.debug("buttonAction : "+buttonAction);
		if(BUTTON_ACTION_SUBMIT.equals(buttonAction)){
			ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
			PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);	
			VerificationResultDataM  verfifyResult = personalInfo.getVerificationResult();			
			boolean isReasonApplicantDocFollowUp = DocumentCheckListUtil.isReasonApplicantDocumentFollowUp(applicationGroup);
			boolean isReasonSupplementatyDocFollowUp = DocumentCheckListUtil.isReasonSupplementaryDocumentFollowUp(applicationGroup);
			String verWebResultCode = verfifyResult.getVerWebResultCode();
			String verHrResultCode = verfifyResult.getVerHrResultCode();
			String verCusResultCode = VerifyUtil.VerifyResult.getVerifyCustomerResult(applicationGroup);
			logger.debug("isReasonApplicantDocFollowUp : "+isReasonApplicantDocFollowUp);
			logger.debug("isReasonSupplementatyDocFollowUp : "+isReasonSupplementatyDocFollowUp);
			logger.debug("verWebResultCode : "+verWebResultCode);
			logger.debug("verHrResultCode : "+verHrResultCode);
			logger.debug("verCusResultCode : "+verCusResultCode);
			if(!VerifyUtil.isVerificationStatusWait(verWebResultCode) && !VerifyUtil.isVerificationStatusWait(verHrResultCode) 
			   && !(VerifyUtil.isVerificationStatusWait(verCusResultCode) && (isReasonApplicantDocFollowUp|| isReasonSupplementatyDocFollowUp))
			   && (isReasonApplicantDocFollowUp|| isReasonSupplementatyDocFollowUp) ){					
				formError.error(LabelUtil.getText(request, "SEND_TO_FU_DV"));
			}else{					
				 String ERROR_VERIFY = VerifyUtil.validationVerification(applicationGroup);
				 logger.debug("ERROR_VERIFY : "+ERROR_VERIFY);
				 if(VerifyUtil.ERROR_TYPE_HR_SEND_TO_FRAUD.equals(ERROR_VERIFY)){
					 formError.error(MessageErrorUtil.getText(request,"NOT_ALLOW_TO_SUBMIT_DV"));
				 }
			}
		}
		return formError.getFormError();
	}
	@Override
	public Object preProcessAction() {
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		if(Util.empty(personalInfo)){
			personalInfo = new PersonalInfoDataM();
		}
		VerificationResultDataM  verfifyResult = personalInfo.getVerificationResult();
		if(Util.empty(verfifyResult)){
			verfifyResult = new VerificationResultDataM();
		}
		IdentifyQuestionDataM verHrResultQuestion = verfifyResult.getIndentifyQuesitionNo(VER_HR_RESULT_QUESTION);
		if(Util.empty(verHrResultQuestion)){
			verHrResultQuestion = new IdentifyQuestionDataM();
		}
		String verifyCustomerResultCode = VerifyUtil.VerifyResult.getVerifyCustomerResult(applicationGroup);
		String verCusComplete = verfifyResult.getVerCusComplete();
		logger.debug("verifyCustomerResultCode : "+verifyCustomerResultCode);
		logger.debug("verCusComplete : "+verCusComplete);
		if(STATUS_COMPLETED_NOT_PASS.equals(verifyCustomerResultCode) 
				&& VER_CUS_COMPLETE_CANCEL.equals(verfifyResult.getVerCusComplete())){
			applicationGroup.setFormAction(WorkflowAction.CANCEL_APPLICATION);
		}else if(STATUS_COMPLETED_NOT_PASS.equals(verfifyResult.getVerHrResultCode()) 
				&& (!Util.empty(verHrResultQuestion) && VER_HR_RESULT_QUESTION_DEPTH_CHECKING.equals(verHrResultQuestion.getCustomerAnswer()))){
			applicationGroup.setFormAction(WorkflowAction.SUBMIT_FRUAD);
		}
		logger.debug("Form Action : "+applicationGroup.getFormAction());
		return null;
	}
	@Override
	public Object processAction() {
	 	ProcessActionResponse processActionResponse = new ProcessActionResponse();
		try{
			processActionResponse.setResultCode(ServiceResponse.Status.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			processActionResponse.setResultCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processActionResponse.setResultDesc(e.getLocalizedMessage());
		}
		return processActionResponse;
	}
	@Override
	public Object postProcessAction() {
		return null;
	}
}