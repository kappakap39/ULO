package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
//import com.eaf.core.ulo.common.properties.SystemConstant;
//import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;

public class IAPersonalInfoSubformNotIdCardDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(IAPersonalInfoSubformDisplayMode.class);
//	private String COVERPAGE_TYPE_VETO = SystemConstant.getConstant("COVERPAGE_TYPE_VETO");
	private String CIDTYPE_IDCARD = SystemConstant.getConstant("CIDTYPE_IDCARD");
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		logger.debug("IAPersonalInfoSubformDisplayMode .....");
		ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
		EntityFormHandler	EntityForm	=	(EntityFormHandler)request.getSession().getAttribute("EntityForm");
		PersonalApplicationInfoDataM personalAppInfo = (PersonalApplicationInfoDataM)EntityForm.getObjectForm();
		PersonalInfoDataM personalInfo = new PersonalInfoDataM();		
		personalInfo = personalAppInfo.getPersonalInfo();
		String CID_TYPE = personalInfo.getCidType();
		String displayMode = HtmlUtil.EDIT;
		logger.debug("IS_VETO : "+applicationGroup.isVeto());
//		logger.debug("CoverpageType "+applicationGroup.getCoverpageType());
		if((applicationGroup.isVeto())
				&& (!Util.empty(CID_TYPE) && !CIDTYPE_IDCARD.equals(CID_TYPE))){
//		if(applicationGroup.isVeto()){
			displayMode =  HtmlUtil.VIEW;
		}
		return displayMode;
	}
}