package com.eaf.orig.ulo.app.view.form.question;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;


public class DataProvider extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(DataProvider.class);
	
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		return "/orig/ulo/subform/question/DataProvider.jsp";
	}

	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}

	@Override
	public String processElement(HttpServletRequest request, Object objectElement) {
		String INFORMANT_NAME_QUESTION = SystemConstant.getConstant("INFORMANT_NAME_QUESTION");
		String DATA_PROVIDER = request.getParameter("DATA_PROVIDER_"+INFORMANT_NAME_QUESTION);
		String RESULT_CHECK = request.getParameter("RESULT_CHECK_"+INFORMANT_NAME_QUESTION);
		EntityFormHandler	EntityForm	=	(EntityFormHandler)request.getSession().getAttribute("EntityForm");
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)EntityForm.getObjectForm();
		logger.debug("DATA_PROVIDER >>"+DATA_PROVIDER);
		logger.debug("RESULT_CHECK >>"+RESULT_CHECK);
		IdentifyQuestionDataM identifyQuestion =(IdentifyQuestionDataM)objectElement;
		//set IdentifyQuestion
		identifyQuestion.setCustomerAnswer(DATA_PROVIDER);
		identifyQuestion.setResult(RESULT_CHECK);
	
		return null;
	}
	
}
