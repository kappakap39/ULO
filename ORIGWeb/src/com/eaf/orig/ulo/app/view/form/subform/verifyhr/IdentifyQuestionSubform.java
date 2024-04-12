package com.eaf.orig.ulo.app.view.form.subform.verifyhr;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.properties.ImplementControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.HRVerificationDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;

public class IdentifyQuestionSubform extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(IdentifyQuestionSubform.class);
	String PHONE_VER_STATUS_Y = SystemConstant.getConstant("PHONE_VER_STATUS_Y");
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		logger.debug("IdentifyQuestionSubform.setProperties");
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)appForm;
		VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
		if(Util.empty(verificationResult)){
			verificationResult = new VerificationResultDataM();
		}		
		ArrayList<IdentifyQuestionDataM> identifyQuestions = verificationResult.getIndentifyQuestions();
		if(!Util.empty(identifyQuestions)){
			ArrayList<ElementInf> elementInfs = ImplementControl.getElements(MConstant.IMPLEMENT_TYPE.VERIFY_QUESTION_HR);
			for(ElementInf elementInf : elementInfs){
				for(IdentifyQuestionDataM identifyQuestion : identifyQuestions){
					String QUESTION_NO = identifyQuestion.getQuestionNo();
					if(elementInf.getImplementId().equals(QUESTION_NO)){
						if(null != elementInf){
							elementInf.processElement(request, identifyQuestion);
						}
					}
				}
			}
		}
	}

	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object appForm) {
		FormErrorUtil formError = new FormErrorUtil();
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)appForm;
		VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
		if(Util.empty(verificationResult)){
			verificationResult = new VerificationResultDataM();
		}
		ArrayList<IdentifyQuestionDataM> identifyQuestions = verificationResult.getIndentifyQuestions();
		HRVerificationDataM hrVerification = verificationResult.getLastHrVerification();
		if(Util.empty(hrVerification)){
			hrVerification = new HRVerificationDataM();
		}
		String STATUS = hrVerification.getPhoneVerStatus();
		logger.debug("hrVerification.getPhoneVerStatus() : "+STATUS);
		if(PHONE_VER_STATUS_Y.equals(STATUS)){
			if(!Util.empty(identifyQuestions)){
				for(IdentifyQuestionDataM identifyQuestion : identifyQuestions){
					String QUESTION_NO = identifyQuestion.getQuestionNo();
					logger.debug("QUESTION_NO : "+QUESTION_NO);
					ElementInf elementInf = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.VERIFY_QUESTION_HR, QUESTION_NO);
					if(null != elementInf){
						elementInf.setObjectRequest(verificationResult);
						formError.addAll(elementInf.validateElement(request,identifyQuestion));
					}
				}
			}
		}
		
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}

}
