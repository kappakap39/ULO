package com.eaf.orig.ulo.app.view.form.subform.verifyhr;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.model.app.HRVerificationDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;

public class ContactCustomerHRSubForm extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(ContactCustomerHRSubForm.class);
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		FormErrorUtil formError = new FormErrorUtil();
		PersonalInfoDataM personalInfo = (PersonalInfoDataM) appForm;
		VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
		if(!Util.empty(verificationResult)){
			ArrayList<HRVerificationDataM> hrVerifications = verificationResult.getHrVerifications();
			if(Util.empty(hrVerifications)){
				formError.error(MessageErrorUtil.getText(request,"ERROR_INVALID_CONTACT"));
			}
		}else{
			formError.error(MessageErrorUtil.getText(request,"ERROR_INVALID_CONTACT"));
		}
		
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}

}
