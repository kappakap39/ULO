package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.HRVerificationDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;

public class VerifyHrVerHRResultFormDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(VerifyHrVerHRResultFormDisplayMode.class);
	String PHONE_VER_STATUS_Y = SystemConstant.getConstant("PHONE_VER_STATUS_Y");
	
	public String displayMode(Object objectElement, HttpServletRequest request) {
		logger.debug("VerifyHrVerHRResultFormDisplayMode.displayMode");
		String displayMode = HtmlUtil.VIEW;
		EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
		
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)EntityForm.getObjectForm();
		VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
		HRVerificationDataM hrVerification = verificationResult.getLastHrVerification();
		if(!Util.empty(hrVerification)){
			String STATUS = hrVerification.getPhoneVerStatus();
			logger.debug("hrVerification.getPhoneVerStatus() : "+STATUS);
			if(PHONE_VER_STATUS_Y.equals(STATUS)){
				displayMode = HtmlUtil.EDIT;
			}
		}
		
		logger.debug("DISPLAY : "+displayMode);
		return displayMode;
	}
}
