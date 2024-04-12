package com.eaf.orig.ulo.app.view.form.question;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;


public class ThName extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(ThName.class);
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		// TODO Auto-generated method stub

		return "/orig/ulo/subform/question/ThName.jsp";
	}

	
	@Override
	public String getDisplayType() {
		// TODO Auto-generated method stub
		return ElementInf.DISPLAY_JSP;
	}


	@Override
	public String processElement(HttpServletRequest request,
			Object objectElement) {
		// TODO Auto-generated method stub
		
		String TH_TITLE_CODE = request.getParameter("TH_TITLE_CODE");
		String TH_FIRST_NAME = request.getParameter("TH_FIRST_NAME");
		String TH_MID_NAME = request.getParameter("TH_MID_NAME");
		String TH_LAST_NAME = request.getParameter("TH_LAST_NAME");
		
		EntityFormHandler	EntityForm	=	(EntityFormHandler)request.getSession().getAttribute("EntityForm");
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		
		if(!Util.empty(personalInfo)){
				personalInfo.setEnTitleCode(TH_TITLE_CODE);
				personalInfo.setEnFirstName(TH_FIRST_NAME);
				personalInfo.setEnMidName(TH_MID_NAME);
				personalInfo.setEnLastName(TH_LAST_NAME);
		}

		IdentifyQuestionDataM identifyQuestion =(IdentifyQuestionDataM)objectElement;
		identifyQuestion.setCustomerAnswer(TH_FIRST_NAME);
		
		return null;
	}
	
	
}
