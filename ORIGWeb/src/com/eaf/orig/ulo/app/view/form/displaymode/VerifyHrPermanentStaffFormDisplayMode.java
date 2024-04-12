package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;

public class VerifyHrPermanentStaffFormDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(VerifyHrPermanentStaffFormDisplayMode.class);
	private String VER_HR_RESULT_QUESTION = SystemConstant.getConstant("VER_HR_RESULT_QUESTION");
	private String VER_HR_RESULT_QUESTION_DEPTH_TRUE = SystemConstant.getConstant("VER_HR_RESULT_QUESTION_DEPTH_TRUE");
	
	public String displayMode(Object objectElement, HttpServletRequest request) {
		logger.debug("VerifyHrPermanentStaffFormDisplayMode.displayMode");
		EntityFormHandler EntityForm =(EntityFormHandler) request.getSession().getAttribute("EntityForm");
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)EntityForm.getObjectForm();
		if(!Util.empty(personalInfo)){
			VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
			if(!Util.empty(verificationResult)){
				IdentifyQuestionDataM VER_HR_RESULT = verificationResult.getIndentifyQuesitionNo(VER_HR_RESULT_QUESTION);
				if(!Util.empty(VER_HR_RESULT)){
					if(!VER_HR_RESULT_QUESTION_DEPTH_TRUE.equals(VER_HR_RESULT.getCustomerAnswer())){
						return HtmlUtil.VIEW;
					}
				}
			}
		}
		return HtmlUtil.EDIT;
	}
	
}
