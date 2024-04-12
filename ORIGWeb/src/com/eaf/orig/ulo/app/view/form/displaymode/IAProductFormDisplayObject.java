package com.eaf.orig.ulo.app.view.form.displaymode;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayObjectHelper;
//import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormEffects;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class IAProductFormDisplayObject extends FormDisplayObjectHelper {
	private static transient Logger logger = Logger.getLogger(IAProductFormDisplayObject.class);
//	String COVERPAGE_TYPE_VETO = SystemConstant.getConstant("COVERPAGE_TYPE_VETO");	
	@Override
	public void init(String objectId, String objectType, FormEffects formEffect) {
		super.init(objectId, objectType, formEffect);
	}	
	@Override
	public String getObjectFormMode(String objectId, String objectType,Object objectElement) {
		String displayMode = FormEffects.Effects.DISABLED;
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getPersonalInfos();		
		if(!Util.empty(personalInfos)){
			if(PersonalInfoUtil.isPersonalApplicant(personalInfos)){
				logger.debug("IS_VETO >> "+applicationGroup.isVeto());
//				logger.debug("CoverpageType >> "+applicationGroup.getCoverpageType());
				if(!applicationGroup.isVeto()){
					displayMode =  FormEffects.Effects.ENABLE;
				}else{
					displayMode = FormEffects.Effects.HIDE;
				}
			}else{
				displayMode = FormEffects.Effects.HIDE;
			}
		}else{
			displayMode =  FormEffects.Effects.ENABLE;
		}
		return displayMode;
	}
	

}
