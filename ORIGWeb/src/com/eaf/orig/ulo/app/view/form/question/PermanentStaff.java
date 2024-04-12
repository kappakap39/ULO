package com.eaf.orig.ulo.app.view.form.question;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;

public class PermanentStaff extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(PermanentStaff.class);
	String VER_HR_RESULT_QUESTION = SystemConstant.getConstant("VER_HR_RESULT_QUESTION");
	String VER_HR_RESULT_QUESTION_DEPTH_TRUE = SystemConstant.getConstant("VER_HR_RESULT_QUESTION_DEPTH_TRUE");
		
	public String getElement(HttpServletRequest request, Object objectElement) {
		return "/orig/ulo/subform/question/PermanentStaff.jsp";
	}
	
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}

	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		logger.debug("processElement >> "+PermanentStaff.class);
		String PERMANENT_STAFF_QUESTION = SystemConstant.getConstant("PERMANENT_STAFF_QUESTION");
		String PERMANENT_STAFF = request.getParameter("PERMANENT_STAFF_"+PERMANENT_STAFF_QUESTION);
		
		logger.debug("PERMANENT_STAFF >>> "+PERMANENT_STAFF);
		EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)EntityForm.getObjectForm();
		VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
		IdentifyQuestionDataM VER_HR_RESULT = verificationResult.getIndentifyQuesitionNo(VER_HR_RESULT_QUESTION);
		IdentifyQuestionDataM identifyQuestion = (IdentifyQuestionDataM)objectElement;
		if(!Util.empty(VER_HR_RESULT)){
			if(!VER_HR_RESULT_QUESTION_DEPTH_TRUE.equals(VER_HR_RESULT.getCustomerAnswer())){
				personalInfo.setPermanentStaff(null);
				identifyQuestion.setCustomerAnswer(null);
				return null;
			}
		}
		
		if(!Util.empty(personalInfo)){
			personalInfo.setPermanentStaff(PERMANENT_STAFF);
		}
		
		identifyQuestion.setCustomerAnswer(PERMANENT_STAFF);
		return null;
	}
	
}
