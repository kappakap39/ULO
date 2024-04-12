package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class DE2FormOccupationPositionCodeDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(DE2FormOccupationPositionCodeDisplayMode.class);
	private String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	private String GOVERNMENT_OFFICER = SystemConstant.getConstant("GOVERNMENT_OFFICER");
	
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		if(null == applicationGroup){
			applicationGroup = new ApplicationGroupDataM();
		}
		String displayMode = HtmlUtil.EDIT;
		logger.debug("APPLICATION_STATUS : "+applicationGroup.getJobState());
		if(SystemConstant.lookup("JOBSTATE_APPROVE",applicationGroup.getJobState())){
			if(Util.empty(objectElement)){
				displayMode =  HtmlUtil.EDIT;
			}else{
				displayMode =  HtmlUtil.VIEW;
			}
		}else if(SystemConstant.lookup("JOBSTATE_REJECT",applicationGroup.getJobState())){
			if(Util.empty(objectElement)){
				PersonalInfoDataM personalApplicant = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
				String OCCUPATION_CODE = personalApplicant.getOccupation();
				logger.debug("applicationGroupId >> "+applicationGroup.getApplicationGroupId());
				logger.debug("OCCUPATION_CODE >> "+OCCUPATION_CODE);
				if(GOVERNMENT_OFFICER.equals(OCCUPATION_CODE)){
					displayMode = HtmlUtil.EDIT;
				}
			}
		}
		logger.debug("objectElement : "+objectElement);
		logger.debug("displayMode : "+displayMode);
		return displayMode;
	}

}
