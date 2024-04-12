package com.eaf.orig.ulo.app.view.form.question;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;


public class EnName extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(EnName.class);

	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		String personal_id=(String)objectElement;
		return "/orig/ulo/subform/question/EnName.jsp?PERSONAL_ID="+personal_id;
	}
	
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}

	@Override
	public String processElement(HttpServletRequest request, Object objectElement) {
		EntityFormHandler	EntityForm	=	(EntityFormHandler)request.getSession().getAttribute("EntityForm");
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
		String personalId = (String)objectElement;
		
		PersonalInfoDataM	 personalInfo = applicationGroup.getPersonalInfoId(personalId);
	
			String EN_TITLE_CODE = request.getParameter("EN_TITLE_CODE"+"_"+personalInfo.getPersonalId());
			String EN_FIRST_NAME = request.getParameter("EN_FIRST_NAME"+"_"+personalInfo.getPersonalId());
			String EN_MID_NAME = request.getParameter("EN_MID_NAME"+"_"+personalInfo.getPersonalId());
			String EN_LAST_NAME = request.getParameter("EN_LAST_NAME"+"_"+personalInfo.getPersonalId());
			
			logger.debug("EN_TITLE_CODE >> "+EN_TITLE_CODE);
			logger.debug("EN_FIRST_NAME >> "+EN_FIRST_NAME);
			logger.debug("EN_MID_NAME >> "+EN_MID_NAME);
			logger.debug("EN_LAST_NAME >> "+EN_LAST_NAME);
					personalInfo.setEnTitleCode(EN_TITLE_CODE);
					personalInfo.setEnFirstName(EN_FIRST_NAME);
					personalInfo.setEnMidName(EN_MID_NAME);
					personalInfo.setEnLastName(EN_LAST_NAME);
					
		return null;
	}
	
	
}
