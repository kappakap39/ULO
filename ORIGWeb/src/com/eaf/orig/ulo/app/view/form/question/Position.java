package com.eaf.orig.ulo.app.view.form.question;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class Position extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(Position.class);
	
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		return "/orig/ulo/subform/question/Position.jsp";
	}
	
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}

	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		String POSITION_QUESTION = SystemConstant.getConstant("POSITION_QUESTION");
		String POSITION = request.getParameter("POSITION_"+POSITION_QUESTION);

		String RESULT_CHECK = request.getParameter("RESULT_CHECK_"+POSITION_QUESTION);
	
		logger.debug("POSITION >>"+POSITION);
		EntityFormHandler	EntityForm	=	(EntityFormHandler)request.getSession().getAttribute("EntityForm");
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)EntityForm.getObjectForm();
	
		if(!Util.empty(personalInfo)){
				personalInfo.setPositionCode(POSITION);
		}

		IdentifyQuestionDataM identifyQuestion =(IdentifyQuestionDataM)objectElement;
		identifyQuestion.setCustomerAnswer(POSITION);
		identifyQuestion.setResult(RESULT_CHECK);
		
		return null;
	}

	@Override
	public HashMap<String,Object> validateElement(HttpServletRequest request, Object objectElement) {
		FormErrorUtil formError = new FormErrorUtil();
		String subformId = "IDENTIFY_QUESTION_SUBFORM";	
		String POSITION_QUESTION = SystemConstant.getConstant("POSITION_QUESTION");
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectElement;
		if(null != personalInfo){
			formError.mandatory(subformId,"POSITION_"+POSITION_QUESTION,personalInfo.getPositionCode(),request);
		}
		return formError.getFormError();
	}

}
