package com.eaf.orig.ulo.app.view.form.verification;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;

public class Commercialregistration extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(Commercialregistration.class);	
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {	
		return "/orig/ulo/subform/verification/CommercialregistrationSubForm.jsp";
	}
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		String REGISTRATION_NO = request.getParameter("REGISTRATION_NO");
		String REQUEST_NO = request.getParameter("REQUEST_NO");
		String REGISTRATION_DATE = request.getParameter("REGISTRATION_DATE");
		String VERIFY_RESULT = request.getParameter("VERIFY_RESULT");
		PersonalInfoDataM personalInfo =  (PersonalInfoDataM)objectElement;
		 personalInfo.setBusRegistId(REGISTRATION_NO);
		 personalInfo.setBusRegistReqNo(REQUEST_NO);
		 personalInfo.setBusRegistDate(FormatUtil.toDate(REGISTRATION_DATE, FormatUtil.TH));			 
		 VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
		 if(Util.empty(verificationResult)){
			 verificationResult = new VerificationResultDataM();	
			 personalInfo.setVerificationResult(verificationResult);
		 }
		 verificationResult.setBusRegistVerResult(VERIFY_RESULT);
		return null;
	}
	
	@Override
	public HashMap<String,Object> validateElement(HttpServletRequest request, Object objectElement) {	
		String subformId="VERIFICATION_CUSTOMER_INFO_SUBFORM";
		FormErrorUtil formError = new FormErrorUtil();
		PersonalInfoDataM personalInfo  = (PersonalInfoDataM)objectElement;
		VerificationResultDataM verificionResult = personalInfo.getVerificationResult();
		if(Util.empty(verificionResult)){
			verificionResult = new VerificationResultDataM();
		}
		formError.mandatory(subformId, "REGISTRATION_NO", personalInfo.getBusRegistId(), request);
		formError.mandatory(subformId, "REQUEST_NO", personalInfo.getBusRegistReqNo(), request);
		formError.mandatory(subformId, "REGISTRATION_DATE", personalInfo.getBusRegistDate(), request);
		formError.mandatory(subformId, "VERIFY_RESULT",verificionResult.getBusRegistVerResult(),request);
		return formError.getFormError();
	}
}

