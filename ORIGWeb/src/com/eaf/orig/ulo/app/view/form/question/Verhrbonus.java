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

public class Verhrbonus extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(Verhrbonus.class);
	
	public String getElement(HttpServletRequest request, Object objectElement) {
		return "/orig/ulo/subform/question/Verhrbonus.jsp";
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
		String BONUS_QUESTION = SystemConstant.getConstant("BONUS_QUESTION");
		String VER_HR_BONUS = request.getParameter("VER_HR_BONUS_"+BONUS_QUESTION);
		String RESULT_CHECK = request.getParameter("RESULT_CHECK_"+BONUS_QUESTION);
		EntityFormHandler	EntityForm	=	(EntityFormHandler)request.getSession().getAttribute("EntityForm");
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)EntityForm.getObjectForm();
		logger.debug("VER_HR_BONUS >>> "+VER_HR_BONUS);
		if(!Util.empty(personalInfo)){
			
				personalInfo.setHrBonus(FormatUtil.toBigDecimal(VER_HR_BONUS));
			
		}

		IdentifyQuestionDataM identifyQuestion =(IdentifyQuestionDataM)objectElement;
		//set IdentifyQuestion
		identifyQuestion.setCustomerAnswer(VER_HR_BONUS);
		identifyQuestion.setResult(RESULT_CHECK);
		
		return null;
	}
	
}
