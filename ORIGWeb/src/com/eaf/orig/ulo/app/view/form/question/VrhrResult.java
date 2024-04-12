package com.eaf.orig.ulo.app.view.form.question;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;

public class VrhrResult extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(VrhrResult.class);
	String VER_HR_RESULT_QUESTION = SystemConstant.getConstant("VER_HR_RESULT_QUESTION");
	
	public String getElement(HttpServletRequest request, Object objectElement) {
		return "/orig/ulo/subform/question/VrhrResult.jsp";
	}
		
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}

	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		String VER_HR_RESULT = request.getParameter("VER_HR_RESULT_"+VER_HR_RESULT_QUESTION);
		logger.debug("VER_HR_RESULT >> "+VER_HR_RESULT);
		IdentifyQuestionDataM identifyQuestion = (IdentifyQuestionDataM)objectElement;
		identifyQuestion.setCustomerAnswer(VER_HR_RESULT);
		return null;
	}
	
	@Override
	public HashMap<String, Object> validateElement(HttpServletRequest request,Object objectElement) {
		String subformId = "IDENTIFY_QUESTION_SUBFORM";
		FormErrorUtil formError = new FormErrorUtil();
		IdentifyQuestionDataM identifyQuestion = (IdentifyQuestionDataM)objectElement;
		formError.mandatory(subformId, "VER_HR_RESULT", identifyQuestion.getCustomerAnswer(), request);
		return formError.getFormError();
	}
	
}
