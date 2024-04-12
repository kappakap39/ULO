package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class OccupationPositionLevelDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(OccupationPositionLevelDisplayMode.class);
	private String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	private String GOVERNMENT_OFFICER = SystemConstant.getConstant("GOVERNMENT_OFFICER");
	
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		String DISPLAY_MODE = HtmlUtil.EDIT;
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		PersonalInfoDataM personalApplicant = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		String OCCUPATION_CODE = personalApplicant.getOccupation();
		if(GOVERNMENT_OFFICER.equals(OCCUPATION_CODE)){
			DISPLAY_MODE = HtmlUtil.VIEW;
		}
		return DISPLAY_MODE;
	}

}
