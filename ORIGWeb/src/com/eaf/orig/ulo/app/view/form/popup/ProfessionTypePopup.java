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

public class ProfessionTypePopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(ProfessionTypePopup.class);

	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {

		logger.debug("ProfessionTypePopup.....");	
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)appForm;
		if(Util.empty(personalInfo)){
			personalInfo = new PersonalInfoDataM();
		}
		String PROFESSION_OTHER = SystemConstant.getConstant("PROFESSION_OTHER");
		String PROFESSION_TYPE_SELECT = request.getParameter("PROFESSION_TYPE_SELECT");
		String PROFESSION_TYPE_SELECT_OTHER = request.getParameter("PROFESSION_TYPE_SELECT_OTHER");
		logger.debug("PROFESSION_TYPE_SELECT >>"+PROFESSION_TYPE_SELECT);	
		logger.debug("PROFESSION_TYPE_SELECT_OTHER >>"+PROFESSION_TYPE_SELECT_OTHER);	
		personalInfo.setProfession(PROFESSION_TYPE_SELECT);
		// Check Radio button. If not selected Other (99)
		if (PROFESSION_OTHER.equals(PROFESSION_TYPE_SELECT)) {
			personalInfo.setProfessionOther(Util.removeAllSpecialCharactors(PROFESSION_TYPE_SELECT_OTHER));
		} else {
			personalInfo.setProfessionOther(null);
		}
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		String subformId = "PROFESSION_TYPE_POPUP";
		logger.debug("subformId >> "+subformId);
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)appForm;
		if(Util.empty(personalInfo)){
			personalInfo = new PersonalInfoDataM();
		}
		
		FormErrorUtil formError = new FormErrorUtil();
		logger.debug("ValidateForm ["+subformId+"] ");
		logger.debug("  getProfession :" + personalInfo.getProfession());
		logger.debug("  getProfessionOther :" + personalInfo.getProfessionOther());
		// Validation
		formError.mandatory(subformId,"PROFESSION_TYPE_SELECT","PROFESSION_TYPE_SELECT",personalInfo.getProfession(),request);	
		if ("99".equals(personalInfo.getProfession())){
			if (Util.empty(personalInfo.getProfessionOther())) {
				formError.error("PROFESSION_TYPE_SELECT_OTHER", MessageUtil.getText(request, "PROFESSION_OTHER_REQUIRED"));
			}
		} else {
			personalInfo.setProfessionOther(null);
		}
		
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}

}
