package com.eaf.orig.ulo.app.view.form.displaymode;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayObjectHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormEffects;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class DE2SupplementaryFormDisplayObject extends FormDisplayObjectHelper{
	private static transient Logger logger = Logger.getLogger(DE2SupplementaryFormDisplayObject.class);
	String ADD_CARD_NO_APPLICATION_ROLE = SystemConstant.getConstant("ADD_CARD_NO_APPLICATION_ROLE");
//	String COVERPAGE_TYPE_VETO = SystemConstant.getConstant("COVERPAGE_TYPE_VETO");	
	@Override
	public String getObjectFormMode(String objectId, String objectType,Object objectElement) {
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		String displayMode = FormEffects.Effects.HIDE;
		logger.debug("IS_VETO : "+applicationGroup.isVeto());
//		logger.debug("COVERPAGE_TYPE : "+applicationGroup.getCoverpageType());
		if(applicationGroup.isSupplementaryApplicant() && !applicationGroup.isVeto()){
			displayMode = FormEffects.Effects.ENABLE;
		}
		
		String roleId = FormControl.getFormRoleId(request);
		if(ADD_CARD_NO_APPLICATION_ROLE.equals(roleId)){
			if(!Util.empty(applicationGroup.getUsingPersonalInfo())){
				for(PersonalInfoDataM personalInfo : applicationGroup.getUsingPersonalInfo()){
					if(!Util.empty(personalInfo) && !Util.empty(personalInfo.getVerificationResult()) && !Util.empty(personalInfo.getVerificationResult().getPolicyRules())){
						displayMode = FormEffects.Effects.ENABLE;
						break;
					}else{
						displayMode = FormEffects.Effects.HIDE;
					}
				}
			}
		}

		return displayMode;
	}

}
