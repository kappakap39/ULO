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

public class ReceiveIncomeMethod extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(ReceiveIncomeMethod.class);
	
	public String getElement(HttpServletRequest request, Object objectElement) {
		return "/orig/ulo/subform/question/ReceiveIncomeMethod.jsp";
	}
	
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}


	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		String RECEIVE_INCOME_METHOD_QUESTION = SystemConstant.getConstant("RECEIVE_INCOME_METHOD_QUESTION");
		String RECEIVE_INCOME_METHOD = request.getParameter("RECEIVE_INCOME_METHOD_"+RECEIVE_INCOME_METHOD_QUESTION);
		String RECEIVE_INCOME_BANK = request.getParameter("RECEIVE_INCOME_BANK_"+RECEIVE_INCOME_METHOD_QUESTION);
		String RESULT_CHECK = request.getParameter("RESULT_CHECK_"+RECEIVE_INCOME_METHOD_QUESTION);
		logger.debug("RECEIVE_INCOME_METHOD >> "+RECEIVE_INCOME_METHOD);
		logger.debug("RECEIVE_INCOME_BANK >> "+RECEIVE_INCOME_BANK);
		logger.debug("RESULT_CHECK_RECEIVE_INCOME_METHOD_QUESTION >> "+RESULT_CHECK);
		EntityFormHandler	EntityForm	=	(EntityFormHandler)request.getSession().getAttribute("EntityForm");
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)EntityForm.getObjectForm();
		if(!Util.empty(personalInfo)){
				personalInfo.setReceiveIncomeMethod(RECEIVE_INCOME_METHOD);
				personalInfo.setReceiveIncomeBank(RECEIVE_INCOME_BANK);
		}

		IdentifyQuestionDataM identifyQuestion =(IdentifyQuestionDataM)objectElement;
		//set IdentifyQuestion
		identifyQuestion.setCustomerAnswer(RECEIVE_INCOME_BANK);
		identifyQuestion.setResult(RESULT_CHECK);
		return null;
	}
}
