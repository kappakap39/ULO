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

@SuppressWarnings("serial")
public class BusinessTypePopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(BusinessTypePopup.class);
	String BUSINESS_TYPE_OTHER = SystemConstant.getConstant("BUSINESS_TYPE_OTHER");
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {

		logger.debug("BusinessTypePopup.....");	
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)appForm;	
		String BUSINESS_TYPE_SELECT = request.getParameter("BUSINESS_TYPE_SELECT");
		String BUSINESS_TYPE_SELECT_OTHER = request.getParameter("BUSINESS_TYPE_SELECT_OTHER");
		logger.debug("BUSINESS_TYPE_SELECT >>"+BUSINESS_TYPE_SELECT);	
		logger.debug("BUSINESS_TYPE_SELECT_OTHER >>"+BUSINESS_TYPE_SELECT_OTHER);	
		personalInfo.setBusinessType(BUSINESS_TYPE_SELECT);
		if (BUSINESS_TYPE_OTHER.equals(BUSINESS_TYPE_SELECT)) {
			personalInfo.setBusinessTypeOther(BUSINESS_TYPE_SELECT_OTHER);
		} else {
			personalInfo.setBusinessTypeOther(null);
		}
		
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		String subformId = "BUSINESS_TYPE_POPUP";
		logger.debug("subformId >> "+subformId);
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)appForm;
		
		FormErrorUtil formError = new FormErrorUtil();
		formError.mandatory(subformId,"BUSINESS_TYPE_SELECT","BUSINESS_TYPE_SELECT",personalInfo.getBusinessType(),request);	
		if (BUSINESS_TYPE_OTHER.equals(personalInfo.getBusinessType())){
			if (Util.empty(personalInfo.getBusinessTypeOther())) {
				formError.error("BUSINESS_TYPE_SELECT_OTHER", MessageUtil.getText(request, "BUSSINESS_OTHER_REQUIRED"));
			}
		} else {
			personalInfo.setBusinessTypeOther(null);
		}
		
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}

}
