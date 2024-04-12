package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class DE2ForeignExDateFieldDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(DE2ForeignFieldDisplayMode.class);
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String CIDTYPE_IDCARD = SystemConstant.getConstant("CIDTYPE_IDCARD");
	String VISA_TYPE_F = SystemConstant.getConstant("VISA_TYPE_F");
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		if(Util.empty(applicationGroup)){
			applicationGroup = new ApplicationGroupDataM();
		}
		PersonalInfoDataM personalInfo = null;
		Object objectForm = FormControl.getMasterObjectForm(request);
		if(objectForm instanceof ApplicationGroupDataM){
			ApplicationGroupDataM applicationGroupDataM = (ApplicationGroupDataM)objectForm;
			personalInfo = applicationGroupDataM.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		}else if(objectForm instanceof PersonalApplicationInfoDataM){
		PersonalApplicationInfoDataM personalApplicationInfoDataM = (PersonalApplicationInfoDataM)objectForm;
			personalInfo = personalApplicationInfoDataM.getPersonalInfo();
		}
		
		String displayMode = HtmlUtil.VIEW;
		logger.debug("JobState >> "+applicationGroup.getJobState());
		logger.debug("fieldConfigId >> "+fieldConfigId);
		if(!Util.empty(personalInfo)){
		if(SystemConstant.lookup("JOBSTATE_APPROVE",applicationGroup.getJobState()) && !CIDTYPE_IDCARD.equals(personalInfo.getCidType()) && !VISA_TYPE_F.equals(personalInfo.getVisaType())){
			if(!isHasDataPreviousRole(applicationGroup)){
				displayMode =  HtmlUtil.EDIT;
			}else{
				displayMode =  HtmlUtil.VIEW;
			}
		}else if(SystemConstant.lookup("JOBSTATE_REJECT",applicationGroup.getJobState()) && !CIDTYPE_IDCARD.equals(personalInfo.getCidType()) && !VISA_TYPE_F.equals(personalInfo.getVisaType())){
			displayMode =  HtmlUtil.EDIT;
		}
		}
		return displayMode;
	}
	
	public boolean isHasDataPreviousRole(ApplicationGroupDataM applicationGroup){	
		return CompareSensitiveFieldUtil.isPreviousCompareData(applicationGroup,fieldConfigId,objectElement);
	}
}
