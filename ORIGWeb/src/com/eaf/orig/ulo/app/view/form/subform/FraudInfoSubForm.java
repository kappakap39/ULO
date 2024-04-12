package com.eaf.orig.ulo.app.view.form.subform;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.orig.bpm.workflow.handle.WorkflowAction;

public class FraudInfoSubForm extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(FraudInfoSubForm.class);
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String FRAUD = SystemConstant.getConstant("FRAUD_FLAG");
	String NO_FRAUD = SystemConstant.getConstant("NO_FRAUD");
	String RETURN = SystemConstant.getConstant("RETURN");
	String DECISION_ACTION_PASS = SystemConstant.getConstant("DECISION_ACTION_PASS");
	String DECISION_ACTION_FAIL = SystemConstant.getConstant("DECISION_ACTION_FAIL");
	String VER_HR_RESULT_QUESTION =SystemConstant.getConstant("VER_HR_RESULT_QUESTION");
	String VER_HR_RESULT_QUESTION_DEPTH_TRUE = SystemConstant.getConstant("VER_HR_RESULT_QUESTION_DEPTH_TRUE");
	String VER_HR_RESULT_CODE_COMPLETE = SystemConstant.getConstant("VALIDATION_STATUS_COMPLETED");
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)appForm;			
		String FRAUD_REMARK = request.getParameter("FRAUD_REMARK");
		String FINAL_DECISION = request.getParameter("FINAL_DECISION");
		String FRAUD_APP_FLAG = request.getParameter("FRAUD_APP_FLAG");
		String FRAUD_COMPANY_FLAG = request.getParameter("FRAUD_COMPANY_FLAG");
		
		logger.debug("FRAUD_REMARK >> "+FRAUD_REMARK);	
		logger.debug("FINAL_DECISION >> "+FINAL_DECISION);
		logger.debug("FRAUD_APP_FLAG >> "+FRAUD_APP_FLAG);
		logger.debug("FRAUD_COMPANY_FLAG >> "+FRAUD_COMPANY_FLAG);		
		applicationGroup.setFraudDecision(DECISION_ACTION_PASS);
		if(FRAUD.equals(FINAL_DECISION)){
			applicationGroup.setFraudDecision(DECISION_ACTION_FAIL);
		}else if(NO_FRAUD.equals(FINAL_DECISION)){
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
			if(!Util.empty(personalInfo) && !Util.empty(personalInfo.getVerificationResult())){
				VerificationResultDataM  verfifyResult = personalInfo.getVerificationResult();
				IdentifyQuestionDataM IdentifyQuestion = verfifyResult.getIndentifyQuesitionNo(VER_HR_RESULT_QUESTION);			
				verfifyResult.setVerHrResultCode(VER_HR_RESULT_CODE_COMPLETE);
				if(!Util.empty(IdentifyQuestion)){
					IdentifyQuestion.setCustomerAnswer(VER_HR_RESULT_QUESTION_DEPTH_TRUE);
				}		
			}
		}
		applicationGroup.setFormAction(WorkflowAction.SUBMIT_FRUAD);
		applicationGroup.setFraudRemark(FRAUD_REMARK);
		applicationGroup.setLastDecision(FINAL_DECISION);
		applicationGroup.setFraudFinalDecision(FINAL_DECISION);
		applicationGroup.setFraudApplicantFlag(FRAUD_APP_FLAG);
		applicationGroup.setFraudCompanyFlag(FRAUD_COMPANY_FLAG);
		logger.debug(">>>>>applicationGroup.getFraudDecision>>>"+applicationGroup.getFraudDecision());
	}
	
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		String subformId ="FRAUD_INFO_SUBFORM";
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		FormErrorUtil formError = new FormErrorUtil();
		formError.mandatory(subformId, "FINAL_DECISION", applicationGroup.getLastDecision(), request);
		if(FRAUD.equals(applicationGroup.getLastDecision()) && Util.empty(applicationGroup.getFraudApplicantFlag())
		   && Util.empty(applicationGroup.getFraudCompanyFlag())){
			formError.mandatory(subformId, "PROTENTIAL_NEGATIVE_APPLICATION", "", request);
		}
		return formError.getFormError();
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
}
