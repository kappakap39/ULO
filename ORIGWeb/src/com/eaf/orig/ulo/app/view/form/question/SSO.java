package com.eaf.orig.ulo.app.view.form.question;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class SSO extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(SSO.class);
	
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		// TODO Auto-generated method stub

		return "/orig/ulo/subform/question/SSO.jsp";
	}

	
	@Override
	public String getDisplayType() {
		// TODO Auto-generated method stub
		return ElementInf.DISPLAY_JSP;
	}


	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		// TODO Auto-generated method stub
		logger.debug("processElement >> "+Verhrbonus.class);
		String SSO_QUESTION = SystemConstant.getConstant("SSO_QUESTION");
		String SSO_QUESTION_VALUE = request.getParameter("SSO_QUESTION_"+SSO_QUESTION);
		String RESULT_CHECK = request.getParameter("RESULT_CHECK_"+SSO_QUESTION);
		EntityFormHandler	EntityForm	=	(EntityFormHandler)request.getSession().getAttribute("EntityForm");
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)EntityForm.getObjectForm();
		logger.debug("SSO_QUESTION >>"+SSO_QUESTION_VALUE);
		logger.debug("RESULT_CHECK >>"+RESULT_CHECK);
		
		IdentifyQuestionDataM identifyQuestion =(IdentifyQuestionDataM)objectElement;
		//set IdentifyQuestion
		identifyQuestion.setCustomerAnswer(SSO_QUESTION_VALUE);
		identifyQuestion.setResult(RESULT_CHECK);
		
		return null;
	}
	
}
