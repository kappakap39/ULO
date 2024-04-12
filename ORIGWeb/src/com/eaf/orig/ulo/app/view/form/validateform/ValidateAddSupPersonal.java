package com.eaf.orig.ulo.app.view.form.validateform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ValidateFormHelper;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.message.MessageUtil;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class ValidateAddSupPersonal extends ValidateFormHelper implements ValidateFormInf {
	private static transient Logger logger = Logger.getLogger(ValidateAddSupPersonal.class);
	private static String APPLICATION_TYPE_ADD_SUP = SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP");
	private static String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	private String COMPLETE_FLAG_FIRST_LOAD_PERSONAL = SystemConstant.getConstant("COMPLETE_FLAG_FIRST_LOAD_PERSONAL");
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		return ValidateFormInf.VALIDATE_YES;
	}

	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object objectForm) {
		FormErrorUtil formError = new FormErrorUtil();
		int MAX_SUP = Integer.parseInt(SystemConfig.getGeneralParam("MAX_ADD_SUP_APPLICANT"));
		// Get Application Group from SESSION
		ORIGFormHandler ORIGForm = (ORIGFormHandler) request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		// Get Personal Info by Type PERSONAL_TYPE_SUPPLEMENTARY
		List<PersonalInfoDataM> personalInfos = applicationGroup
				.getPersonalInfos(SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY"));
		
	
		ArrayList<ApplicationDataM> appItem = applicationGroup.getApplicationsProduct(PRODUCT_CRADIT_CARD);
		if(!APPLICATION_TYPE_ADD_SUP.equals(applicationGroup.getApplicationType()) && Util.empty(appItem)){
			formError.error(MessageUtil.getText(request, "MAIN_NO_CARD_FOR_ADD_SUP"));
		}
		for(PersonalInfoDataM personalInfo : personalInfos){
			if(COMPLETE_FLAG_FIRST_LOAD_PERSONAL.equals(personalInfo.getCompleteData())){
				MAX_SUP++;
				break;
			}
		}
		int subPersonalCount = personalInfos.size();
		logger.debug("subPersonalCount : " + subPersonalCount);
		if (subPersonalCount >= MAX_SUP) {
			formError.error(MessageUtil.getText(request, "SUP_PERSONAL_EXCEED"));
		}
		return formError.getFormError();
	}
}
