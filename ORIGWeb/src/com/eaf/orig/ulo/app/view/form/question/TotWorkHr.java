package com.eaf.orig.ulo.app.view.form.question;


import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class TotWorkHr extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(TotWorkHr.class);
	String Q23 = SystemConstant.getConstant("TOTAL_WORK_HR_QUESTION");
	String VER_HR_RESULT_QUESTION = SystemConstant.getConstant("VER_HR_RESULT_QUESTION");
	String PERMANENT_STAFF_QUESTION = SystemConstant.getConstant("PERMANENT_STAFF_QUESTION");
	String VER_HR_RESULT_QUESTION_DEPTH_TRUE = SystemConstant.getConstant("VER_HR_RESULT_QUESTION_DEPTH_TRUE");
	String PERMANENT_STAFF_Y = SystemConstant.getConstant("PERMANENT_STAFF_Y");
	
	public String getElement(HttpServletRequest request, Object objectElement) {
		return "/orig/ulo/subform/question/TotWorkHr.jsp";
	}
	
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}

	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		logger.debug("processElement >> "+TotWorkHr.class);
		String TOT_WORK_YEAR = request.getParameter("TOT_WORK_YEAR_"+Q23);
		String TOT_WORK_MONTH = request.getParameter("TOT_WORK_MONTH_"+Q23);
		String RESULT_CHECK = request.getParameter("RESULT_CHECK_"+Q23);
		logger.debug("TOT_WORK_YEAR >> "+TOT_WORK_YEAR);
		logger.debug("TOT_WORK_MONTH >> "+TOT_WORK_MONTH);
		logger.debug("RESULT_CHECK >> "+RESULT_CHECK);
		EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
		
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)EntityForm.getObjectForm();
//		VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
//		IdentifyQuestionDataM VER_HR_RESULT = verificationResult.getIndentifyQuesitionNo(VER_HR_RESULT_QUESTION);
//		IdentifyQuestionDataM PERMANENT_STAFF = verificationResult.getIndentifyQuesitionNo(PERMANENT_STAFF_QUESTION);
//		if(!Util.empty(VER_HR_RESULT) && !Util.empty(PERMANENT_STAFF)){
//			if(!VER_HR_RESULT_QUESTION_DEPTH_TRUE.equals(VER_HR_RESULT.getCustomerAnswer()) || !PERMANENT_STAFF_Y.equals(PERMANENT_STAFF.getCustomerAnswer())){
//				personalInfo.setTotWorkYear(BigDecimal.ZERO);
//				personalInfo.setTotWorkMonth(BigDecimal.ZERO);
//				return null;
//			}
//		}
				
		if(!Util.empty(personalInfo)){
			personalInfo.setTotWorkYear(FormatUtil.toBigDecimal(TOT_WORK_YEAR,true));
			personalInfo.setTotWorkMonth(FormatUtil.toBigDecimal(TOT_WORK_MONTH,true));
		}

		IdentifyQuestionDataM identifyQuestion = (IdentifyQuestionDataM)objectElement;
		identifyQuestion.setCustomerAnswer(TOT_WORK_YEAR+","+TOT_WORK_MONTH);
		identifyQuestion.setResult(RESULT_CHECK);
		//set IdentifyQuestion
		
		return null;
	}
	
	@Override
	public HashMap<String, Object> validateElement(HttpServletRequest request,Object objectElement) {
		String subformId = "IDENTIFY_QUESTION_SUBFORM";
		FormErrorUtil formError = new FormErrorUtil();
		formError.mandatory(subformId, "TOT_WORK_YEAR", null, request);
		return formError.getFormError();
	}
	
}
