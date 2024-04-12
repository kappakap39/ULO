package com.eaf.orig.ulo.app.view.form.displaymode;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayObjectHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;

public class DVFormDisplayObjectHR extends FormDisplayObjectHelper{
	private static transient Logger logger = Logger.getLogger(DVFormDisplayObjectHR.class);
	@Override
	public String getObjectFormMode(String objectId, String objectType,Object objectElement) {
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		PersonalInfoDataM personalInfo=  PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		String displayMode=HtmlUtil.EDIT;
		if(!Util.empty(personalInfo)){
			VerificationResultDataM verificationResult= personalInfo.getVerificationResult();
			if(!Util.empty(verificationResult)){
				String resultCode= verificationResult.getVerHrResultCode();
				if(null!=resultCode && resultCode.equals(SystemConstant.getConstant("VALIDATION_STATUS_WAIVED"))){
					displayMode=HtmlUtil.VIEW;
				}
			}
		}
		
		logger.debug("DVFormDisplayObject displayMode >>"+displayMode);
		return displayMode;
	}

}
