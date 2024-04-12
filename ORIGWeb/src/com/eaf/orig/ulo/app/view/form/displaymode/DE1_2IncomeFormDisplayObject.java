package com.eaf.orig.ulo.app.view.form.displaymode;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayObjectHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormEffects;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;

public class DE1_2IncomeFormDisplayObject extends FormDisplayObjectHelper {
	private static transient Logger logger = Logger.getLogger(DE1_2IncomeFormDisplayObject.class);
	private String PERSONAL_TYPE_APPLICANT =SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	private String VALIDATION_STATUS_WAIVED =SystemConstant.getConstant("VALIDATION_STATUS_WAIVED");
	
	@Override
	public void init(String objectId, String objectType, FormEffects formEffect) {
		// TODO Auto-generated method stub
		super.init(objectId, objectType, formEffect);
	}

	@Override
	public String getObjectFormMode(String objectId, String objectType,Object objectElement) {
		// TODO Auto-generated method stub
		String displayMode = HtmlUtil.EDIT;
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		PersonalInfoDataM personalInfo=  applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		if(!Util.empty(personalInfo)){
			
			VerificationResultDataM verificationResult= personalInfo.getVerificationResult();
			if(!Util.empty(verificationResult)){
				String resultCode= verificationResult.getSummaryIncomeResultCode();
				if(null!=resultCode && resultCode.equals(VALIDATION_STATUS_WAIVED)){
					displayMode=HtmlUtil.VIEW;	
					logger.debug(" find  >> VALIDATION_STATUS_WAIVED");
				}
				else {
					if(Util.empty(personalInfo.getDisplayEditBTN()) || personalInfo.getDisplayEditBTN().equals(MConstant.FLAG_N)){
						displayMode =HtmlUtil.EDIT;
					}
					else if(personalInfo.getDisplayEditBTN().equals(MConstant.FLAG_Y)){
						displayMode = HtmlUtil.VIEW;
					}
				}
			}

		}
		logger.debug("DE1_2IncomeFormDisplayMode displayMode >> "+displayMode);
		return displayMode;
	}
	
	
	
}
