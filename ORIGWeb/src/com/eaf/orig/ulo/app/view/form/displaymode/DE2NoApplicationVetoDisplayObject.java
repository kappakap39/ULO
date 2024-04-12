package com.eaf.orig.ulo.app.view.form.displaymode;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayObjectHelper;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormEffects;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class DE2NoApplicationVetoDisplayObject extends FormDisplayObjectHelper {
	private static transient Logger logger = Logger.getLogger(VetoDisplayObject.class);
	String ADD_CARD_NO_APPLICATION_ROLE = SystemConstant.getConstant("ADD_CARD_NO_APPLICATION_ROLE");
//	String COVERPAGE_TYPE_VETO = SystemConstant.getConstant("COVERPAGE_TYPE_VETO");
	@Override
	public void init(String objectId, String objectType, FormEffects formEffect) {
		// TODO Auto-generated method stub
		super.init(objectId, objectType, formEffect);
	}

	@Override
	public String getObjectFormMode(String objectId, String objectType,Object objectElement) {
		String displayMode = FormEffects.Effects.ENABLE;
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		logger.debug("IS_VETO : "+applicationGroup.isVeto());
//		logger.debug("CoverpageType "+applicationGroup.getCoverpageType());
//		if(applicationGroup.isVeto() || COVERPAGE_TYPE_VETO.equals(applicationGroup.getCoverpageType())){
		if(applicationGroup.isVeto()){
			displayMode = FormEffects.Effects.HIDE;
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
		
		logger.debug("VetoDisplayObject >> "+displayMode);
		return displayMode;
	}
	

}
