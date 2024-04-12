package com.eaf.orig.ulo.app.view.form.popup;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.message.MessageUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class OccupationPopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(OccupationPopup.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		logger.debug("OccupationPopup.....");		
		String OCCUPATION_OTHER = SystemConstant.getConstant("OCCUPATION_OTHER");
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)appForm;			
		String OCCUPATION_SELECT = request.getParameter("OCCUPATION_SELECT");
		String OCCUPATION_SELECT_OTHER = request.getParameter("OCCUPATION_SELECT_OTHER");
		logger.debug("OCCUPATION_SELECT >>"+OCCUPATION_SELECT);	
		logger.debug("OCCUPATION_SELECT_OTHER >>"+OCCUPATION_SELECT_OTHER);
		personalInfo.setOccupation(OCCUPATION_SELECT);
		if (OCCUPATION_OTHER.equals(OCCUPATION_SELECT)) {
			personalInfo.setOccpationOther(OCCUPATION_SELECT_OTHER);
		} else {
			personalInfo.setOccpationOther(null);
		}
		
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		String subformId = "OCCUPATION_POPUP";
		logger.debug("subformId >> "+subformId);
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)appForm;
		
		FormErrorUtil formError = new FormErrorUtil();
		formError.mandatory(subformId,"OCCUPATION_SELECT","OCCUPATION_SELECT",personalInfo.getOccupation(),request);	
		if ("99".equals(personalInfo.getOccupation())){
			if (Util.empty(personalInfo.getOccpationOther())) {
				formError.error("OCCUPATION_SELECT_OTHER", MessageUtil.getText(request, "OCCUAPATION_OTHER_REQUIRED"));
			}
		} else {
			personalInfo.setProfessionOther(null);
		}
		
		return formError.getFormError();
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request){		
		return false;
	}
}
