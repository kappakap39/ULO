package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class DE2NoApplicationDisplayMode extends FormDisplayModeHelper {
	private static transient Logger logger = Logger.getLogger(DE2AddSupplementaryFormDisplayMode.class);
	String ADD_CARD_NO_APPLICATION_ROLE = SystemConstant.getConstant("ADD_CARD_NO_APPLICATION_ROLE");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
//	String COVERPAGE_TYPE_VETO = SystemConstant.getConstant("COVERPAGE_TYPE_VETO");
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		String displayMode=HtmlUtil.VIEW;
		logger.debug("IS_VETO : "+applicationGroup.isVeto());
		String roleId = FormControl.getFormRoleId(request);
		if(ADD_CARD_NO_APPLICATION_ROLE.equals(roleId)){
//			if(!Util.empty(applicationGroup.getUsingPersonalInfo())){
//				for(PersonalInfoDataM personalInfo : applicationGroup.getUsingPersonalInfo()){
//					if(!Util.empty(personalInfo) && !Util.empty(personalInfo.getVerificationResult()) && !Util.empty(personalInfo.getVerificationResult().getPolicyRules())){
//						displayMode = HtmlUtil.EDIT;
//						break;
//					}else{
//						displayMode = HtmlUtil.VIEW;
//					}
//				}
//			}
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
			if(!Util.empty(personalInfo)){
				if(!Util.empty(personalInfo) && !Util.empty(personalInfo.getVerificationResult()) && !Util.empty(personalInfo.getVerificationResult().getPolicyRules())){
					displayMode = HtmlUtil.EDIT;
				}else{
					displayMode = HtmlUtil.VIEW;
				}
			}
		}
		
		return displayMode;
	}
}
