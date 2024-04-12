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

public class VerifyHrIncomeFormDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(VerifyHrIncomeFormDisplayMode.class);
	private String VER_HR_RESULT_QUESTION = SystemConstant.getConstant("VER_HR_RESULT_QUESTION");
	private String PERMANENT_STAFF_QUESTION = SystemConstant.getConstant("PERMANENT_STAFF_QUESTION");
	private String VER_HR_RESULT_QUESTION_DEPTH_TRUE = SystemConstant.getConstant("VER_HR_RESULT_QUESTION_DEPTH_TRUE");
	private String PERMANENT_STAFF_Y = SystemConstant.getConstant("PERMANENT_STAFF_Y");
	
	public String displayMode(Object objectElement, HttpServletRequest request) {
		EntityFormHandler EntityForm =(EntityFormHandler) request.getSession().getAttribute("EntityForm");
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)EntityForm.getObjectForm();
		if(!Util.empty(personalInfo)){
			VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
			if(!Util.empty(verificationResult)){
				IdentifyQuestionDataM VER_HR_RESULT = verificationResult.getIndentifyQuesitionNo(VER_HR_RESULT_QUESTION);
				IdentifyQuestionDataM PERMANENT_STAFF = verificationResult.getIndentifyQuesitionNo(PERMANENT_STAFF_QUESTION);
				if(!Util.empty(VER_HR_RESULT) && !Util.empty(PERMANENT_STAFF)){
					if(!VER_HR_RESULT_QUESTION_DEPTH_TRUE.equals(VER_HR_RESULT.getCustomerAnswer()) || !PERMANENT_STAFF_Y.equals(PERMANENT_STAFF.getCustomerAnswer()) ){
						return HtmlUtil.VIEW;
					}
				}
			}
		}
		return HtmlUtil.EDIT;
	}
}
