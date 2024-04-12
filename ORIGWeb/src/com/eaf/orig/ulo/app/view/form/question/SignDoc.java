package com.eaf.orig.ulo.app.view.form.question;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;


public class SignDoc extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(SignDoc.class);

	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		return "/orig/ulo/subform/question/SignDoc.jsp";
	}
	
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}

	@Override
	public String processElement(HttpServletRequest request, Object objectElement) {
		String AUTHORIZED_SIGNATORY_QUESTION = SystemConstant.getConstant("AUTHORIZED_SIGNATORY_QUESTION");
		String SIGN_DOC = request.getParameter("SIGN_DOC_"+AUTHORIZED_SIGNATORY_QUESTION);
		String RESULT_CHECK = request.getParameter("RESULT_CHECK_"+AUTHORIZED_SIGNATORY_QUESTION);
		EntityFormHandler	EntityForm	=	(EntityFormHandler)request.getSession().getAttribute("EntityForm");
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)EntityForm.getObjectForm();
		logger.debug("SIGN_DOC >>"+SIGN_DOC);
		logger.debug("RESULT_CHECK >>"+RESULT_CHECK);
		IdentifyQuestionDataM identifyQuestion =(IdentifyQuestionDataM)objectElement;
		//set IdentifyQuestion
		identifyQuestion.setCustomerAnswer(SIGN_DOC);
		identifyQuestion.setResult(RESULT_CHECK);
	
		return null;
	}
	
}
