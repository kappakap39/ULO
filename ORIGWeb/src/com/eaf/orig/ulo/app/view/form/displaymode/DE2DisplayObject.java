package com.eaf.orig.ulo.app.view.form.displaymode;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayObjectHelper;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormEffects;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class DE2DisplayObject extends FormDisplayObjectHelper {
	private static transient Logger logger = Logger.getLogger(DE2DisplayObject.class);
	private String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	@Override
	public void init(String objectId, String objectType, FormEffects formEffect) {
		// TODO Auto-generated method stub
		super.init(objectId, objectType, formEffect);
	}

	@Override
	public String getObjectFormMode(String objectId, String objectType,Object objectElement) {
		String displayMode = FormEffects.Effects.HIDE;
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		logger.debug("JobState >> "+applicationGroup.getJobState());
		logger.debug("objectId"+objectId);
		if(SystemConstant.lookup("JOBSTATE_APPROVE",applicationGroup.getJobState())){
			if(!CompareSensitiveFieldUtil.isPreviousCompareData(applicationGroup, objectId, objectElement)){
				displayMode =  FormEffects.Effects.SHOW;
			}else{
				displayMode =  FormEffects.Effects.HIDE;
			}
		}else if(SystemConstant.lookup("JOBSTATE_REJECT",applicationGroup.getJobState())){
			displayMode =  FormEffects.Effects.SHOW;
		}
		return displayMode;
	}
}
