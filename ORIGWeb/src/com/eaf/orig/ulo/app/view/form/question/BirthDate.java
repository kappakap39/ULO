package com.eaf.orig.ulo.app.view.form.question;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class BirthDate extends ElementHelper implements ElementInf  {
	private static transient Logger logger = Logger.getLogger(BirthDate.class);

	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		String personal_id =(String)objectElement;
		return "/orig/ulo/subform/question/BirthDate.jsp?PERSONAL_ID="+personal_id;
	}
	
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}

	@Override
	public String processElement(HttpServletRequest request,
			Object objectElement) {
		logger.debug("processElement >> "+BirthDate.class);
		String personalId = (String)objectElement;
		EntityFormHandler	EntityForm	=	(EntityFormHandler)request.getSession().getAttribute("EntityForm");
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
		
		PersonalInfoDataM personalInfo	 = applicationGroup.getPersonalInfoId(personalId);
		String BIRTH_DATE_CE = request.getParameter("BIRTH_DATE_CE_"+personalInfo.getPersonalId());
		String BIRTH_DATE_BE = request.getParameter("BIRTH_DATE_BE_"+personalInfo.getPersonalId());
		
		if(!Util.empty(personalInfo)){
				personalInfo.setBirthDate(FormatUtil.toDate(BIRTH_DATE_CE, FormatUtil.EN));
		}
		
		return null;
	}
	
	
}
