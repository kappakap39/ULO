package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
//import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
//import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;

public class NormalPersonalInfoDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(IAPersonalInfoSubformDisplayMode.class);
//	private String COVERPAGE_TYPE_VETO = SystemConstant.getConstant("COVERPAGE_TYPE_VETO");
	private String CIDTYPE_IDCARD = SystemConstant.getConstant("CIDTYPE_IDCARD");
	private String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		logger.debug("IAPersonalInfoSubformDisplayMode .....");
		ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		String CID_TYPE = personalInfo.getCidType();
		String displayMode = HtmlUtil.EDIT;
		logger.debug("IS_VETO : "+applicationGroup.isVeto());
//		logger.debug("CoverpageType "+applicationGroup.getCoverpageType());
		if((applicationGroup.isVeto()) && (!Util.empty(CID_TYPE) && !CIDTYPE_IDCARD.equals(CID_TYPE))){
//		if((applicationGroup.isVeto())){
			displayMode =  HtmlUtil.VIEW;
		}
		return displayMode;
	}
}