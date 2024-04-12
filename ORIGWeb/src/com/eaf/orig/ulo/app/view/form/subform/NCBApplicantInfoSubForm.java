package com.eaf.orig.ulo.app.view.form.subform;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;


public class NCBApplicantInfoSubForm extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(NCBApplicantInfoSubForm.class);
	private String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String subformId = "NCB_APPLICANT_INFO_SUBFORM";

	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		
		String CONSENT_DATE = request.getParameter("DATE_CONSENT");
		String TH_BIRTH_DATE_CONSENT = request.getParameter("TH_BIRTH_DATE_CONSENT");
		String EN_BIRTH_DATE_CONSENT = request.getParameter("EN_BIRTH_DATE_CONSENT");
		String PLACE_CONSENT = request.getParameter("PLACE_CONSENT");
		String WITNESS_CONSENT = request.getParameter("WITNESS_CONSENT");
		String ID_NO_CONSENT = request.getParameter("ID_NO_CONSENT");
		
		logger.debug("TH_BIRTH_DATE_CONSENT >>"+TH_BIRTH_DATE_CONSENT);
		logger.debug("EN_BIRTH_DATE_CONSENT >>"+EN_BIRTH_DATE_CONSENT);
		logger.debug("PLACE_CONSENT >>"+PLACE_CONSENT);
		logger.debug("WITNESS_CONSENT >>"+WITNESS_CONSENT);
		logger.debug("ID_NO_CONSENT >> "+ID_NO_CONSENT );
		logger.debug("CONSENT_DATE >> "+CONSENT_DATE );

	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		return null;
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
		 
}