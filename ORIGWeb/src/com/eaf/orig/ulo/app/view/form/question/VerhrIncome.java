package com.eaf.orig.ulo.app.view.form.question;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;

public class VerhrIncome  extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(VerhrIncome.class);
	String VER_HR_INCOME_QUESTION = SystemConstant.getConstant("VER_HR_INCOME_QUESTION");
	String VER_HR_RESULT_QUESTION = SystemConstant.getConstant("VER_HR_RESULT_QUESTION");
	String PERMANENT_STAFF_QUESTION = SystemConstant.getConstant("PERMANENT_STAFF_QUESTION");
	String VER_HR_RESULT_QUESTION_DEPTH_TRUE = SystemConstant.getConstant("VER_HR_RESULT_QUESTION_DEPTH_TRUE");
	String PERMANENT_STAFF_Y = SystemConstant.getConstant("PERMANENT_STAFF_Y");
	
	public String getElement(HttpServletRequest request, Object objectElement) {
		return "/orig/ulo/subform/question/VerhrIncome.jsp";
	}
	
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}

	public String processElement(HttpServletRequest request,Object objectElement) {
		logger.debug("processElement >> "+VerhrIncome.class);
		IdentifyQuestionDataM identifyQuestion =(IdentifyQuestionDataM)objectElement;
		String VER_HR_INCOME = request.getParameter("VER_HR_INCOME_"+VER_HR_INCOME_QUESTION);
		String RESULT_CHECK = request.getParameter("RESULT_CHECK_"+VER_HR_INCOME_QUESTION);
		EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)EntityForm.getObjectForm();
		
		if(!Util.empty(personalInfo)){
			personalInfo.setHrIncome(FormatUtil.toBigDecimal(VER_HR_INCOME));
		}
		
		VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
		IdentifyQuestionDataM VER_HR_RESULT = verificationResult.getIndentifyQuesitionNo(VER_HR_RESULT_QUESTION);
		IdentifyQuestionDataM PERMANENT_STAFF = verificationResult.getIndentifyQuesitionNo(PERMANENT_STAFF_QUESTION);
		if(!Util.empty(VER_HR_RESULT) && !Util.empty(PERMANENT_STAFF)){
			if(!VER_HR_RESULT_QUESTION_DEPTH_TRUE.equals(VER_HR_RESULT.getCustomerAnswer()) || !PERMANENT_STAFF_Y.equals(PERMANENT_STAFF.getCustomerAnswer())){
				personalInfo.setHrIncome(null);
				identifyQuestion.setCustomerAnswer(null);
				return null;
			}
		}
		
		//set IdentifyQuestion
		identifyQuestion.setCustomerAnswer(VER_HR_INCOME);
		identifyQuestion.setResult(RESULT_CHECK);
		
		return null;
	}
}
