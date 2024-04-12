package com.eaf.orig.ulo.app.view.form.subform.verifycustomer;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PhoneVerificationDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;

public class ContactCustomerVerifySubForm extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(ContactCustomerVerifySubForm.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		logger.debug("ContactCustomerVerifySubForm .... ");
		String CONTACT_TIME = request.getParameter("CONTACT_TIME");		
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);	
		PersonalInfoDataM	personalinfo=PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		 if(!Util.empty(personalinfo)){
			 personalinfo.setContactTime(CONTACT_TIME);
		 }
	}

	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object appForm) {
		logger.debug("ContactCustomerVerifySubForm >> validateForm");
		FormErrorUtil formError = new FormErrorUtil();
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		PersonalInfoDataM personalinfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		VerificationResultDataM verificationResult = personalinfo.getVerificationResult();
		if(!Util.empty(verificationResult)){
			ArrayList<PhoneVerificationDataM> phoneVerifications = verificationResult.filterPhoneVerifications(applicationGroup.getLifeCycle());
			if(Util.empty(phoneVerifications)){
				formError.error(MessageErrorUtil.getText(request,"ERROR_INVALID_CONTACT"));
			}
		}
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}

}
