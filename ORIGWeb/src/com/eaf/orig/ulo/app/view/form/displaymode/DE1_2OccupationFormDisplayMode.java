package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class DE1_2OccupationFormDisplayMode  extends FormDisplayModeHelper {
	private static transient Logger logger = Logger.getLogger(VerifyHrVerHRResultFormDisplayMode.class);
	private String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		String displayMode = HtmlUtil.EDIT;
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		if(Util.empty(personalInfo.getDisplayEditBTN()) || personalInfo.getDisplayEditBTN().equals(MConstant.FLAG_N)){
			displayMode = HtmlUtil.VIEW;
		}else if(personalInfo.getDisplayEditBTN().equals(MConstant.FLAG_Y)){
			displayMode = HtmlUtil.EDIT;
		}
		logger.debug("DE1_2OccupationFormDisplayMode displayMode >> "+displayMode);
		return displayMode;
	}
}
