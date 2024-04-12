package com.eaf.orig.ulo.app.view.form.question.answerofquestion;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.orig.ulo.app.view.util.uloOrigApp.dao.UloOrigAppDAO;
import com.eaf.orig.ulo.app.view.util.uloOrigApp.dao.UloOrigAppDAOImpl;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class CCSupplementaryResult extends FormHelper implements FormAction  {
	private static transient Logger logger = Logger.getLogger(CCSupplementaryResult.class);
	private	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	@Override
	public String processForm() {
		return super.processForm();
	}

	@Override
	public Object getObjectForm() {
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		HashMap<String, String> hSupCardAmount  = new HashMap<String, String>();
		try {
			String PERSONAL_ID = personalInfo.getPersonalId();
			logger.debug("PERSONAL_ID>>"+PERSONAL_ID);
			UloOrigAppDAO dao = new UloOrigAppDAOImpl();
			hSupCardAmount = dao.getCountSupCardInfoByPersonalId(PERSONAL_ID);
			
			logger.debug("hSupCardAmount>>"+hSupCardAmount);
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return hSupCardAmount;
	}

}
