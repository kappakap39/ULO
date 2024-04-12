package com.eaf.orig.ulo.app.view.form.displaymode;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayObjectHelper;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormEffects;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;

public class DocumentHeaderFlagDisplayObject extends FormDisplayObjectHelper{
	private static transient Logger logger = Logger.getLogger(DocumentHeaderFlagDisplayObject.class);
	private String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	private String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	private String DOC_COMPLETE_FLAG = SystemConstant.getConstant("DOC_COMPLETE_FLAG");
	
	@Override
	public String getObjectFormMode(String objectId, String objectType, Object objectElement) {
		String displayMode = FormEffects.Effects.HIDE;
		
		logger.debug("formRoleId>>"+formRoleId);
		logger.debug("objectRoleId>>"+objectRoleId);
		logger.debug("conditions size>>"+conditions.size());
		if(!Util.empty(formRoleId) && !Util.empty(conditions)){
			if(conditions.contains(formRoleId)){
				ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
				PersonalInfoDataM personalApplicant = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
				if(!Util.empty(personalApplicant)){
					VerificationResultDataM verResult = personalApplicant.getVerificationResult();
					if(!Util.empty(verResult)){
						logger.debug("DocumentCompleteFlag >> "+verResult.getDocCompletedFlag());
						if(!DOC_COMPLETE_FLAG.equals(verResult.getDocCompletedFlag())){
							displayMode = FormEffects.Effects.SHOW;
						}
					}
				}
				
				ArrayList<PersonalInfoDataM> personalSupplementaryList = applicationGroup.getPersonalInfos(PERSONAL_TYPE_SUPPLEMENTARY);
				if(!Util.empty(personalSupplementaryList)){
					for(PersonalInfoDataM PersonalSupplementary : personalSupplementaryList){
						VerificationResultDataM verResult = PersonalSupplementary.getVerificationResult();
						if(!Util.empty(verResult)){
							logger.debug("DocumentCompleteFlag >> "+verResult.getDocCompletedFlag());
							if(!DOC_COMPLETE_FLAG.equals(verResult.getDocCompletedFlag())){
								displayMode = FormEffects.Effects.SHOW;
							}
						}
					}
				}
			}
		}

		return displayMode;
	}
}
