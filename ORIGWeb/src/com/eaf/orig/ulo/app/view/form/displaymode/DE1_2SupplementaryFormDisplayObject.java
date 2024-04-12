package com.eaf.orig.ulo.app.view.form.displaymode;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayObjectHelper;
//import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormEffects;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class DE1_2SupplementaryFormDisplayObject extends FormDisplayObjectHelper{
	private static transient Logger logger = Logger.getLogger(DE1_2SupplementaryFormDisplayObject.class);
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
		logger.debug("DE1_2SupplementaryFormDisplayObject add sup button displayMode >>"+displayMode);
		return displayMode;
	}

}
