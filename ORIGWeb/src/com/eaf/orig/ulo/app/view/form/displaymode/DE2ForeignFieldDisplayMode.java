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

public class DE2ForeignFieldDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(DE2ForeignFieldDisplayMode.class);
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String CIDTYPE_IDCARD = SystemConstant.getConstant("CIDTYPE_IDCARD");
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
		if(SystemConstant.lookup("JOBSTATE_APPROVE",applicationGroup.getJobState()) && !CIDTYPE_IDCARD.equals(personalInfo.getCidType())){
			if(!isHasDataPreviousRole(applicationGroup)){
				displayMode =  HtmlUtil.EDIT;
			}else{
				displayMode =  HtmlUtil.VIEW;
			}
		}else if(SystemConstant.lookup("JOBSTATE_REJECT",applicationGroup.getJobState()) && !CIDTYPE_IDCARD.equals(personalInfo.getCidType())){
			displayMode =  HtmlUtil.EDIT;
		}
		}
		return displayMode;
	}
	
	public boolean isHasDataPreviousRole(ApplicationGroupDataM applicationGroup){	
		 
		
		/*boolean isHasDataPreviousRole = false;
		String fieldNameType = fieldConfigId;
		String uniqueId = "";
		String uniqueLevel = "";
		ArrayList<CompareDataM> compares = new ArrayList<>();
		if(objectElement instanceof ApplicationGroupDataM){
			compares = CompareSensitiveFieldUtil.getPrevCompareDataMByFieldNameType(applicationGroup, fieldNameType);
			uniqueId = applicationGroup.getApplicationGroupId();
			uniqueLevel = CompareDataM.UniqueLevel.APPLICATION_GROUP;
		}else if(objectElement instanceof PersonalInfoDataM){
			PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectElement;
			compares = CompareSensitiveFieldUtil.getPrevCompareDataMByPersonalInfo(applicationGroup, fieldNameType, personalInfo);
			uniqueId = personalInfo.getPersonalId();
			uniqueLevel = CompareDataM.UniqueLevel.PERSONAL;
		}else if(objectElement instanceof AddressDataM){
			AddressDataM address = (AddressDataM)objectElement;
			compares = CompareSensitiveFieldUtil.getPrevCompareDataMByFieldNameType(applicationGroup, fieldNameType);
			uniqueId = address.getAddressId();
			uniqueLevel = CompareDataM.UniqueLevel.ADDRESS;
		}
		
		logger.debug("fieldNameType >> "+fieldNameType);
		logger.debug("uniqueId >> "+uniqueId);
		logger.debug("uniqueLevel >> "+uniqueLevel);
		if(!Util.empty(compares)){
			for(CompareDataM compare : compares){
				if(uniqueId.equals(compare.getUniqueId()) && uniqueLevel.equals(compare.getUniqueLevel())){
					isHasDataPreviousRole = true;
				}
			}
		}*/
		return CompareSensitiveFieldUtil.isPreviousCompareData(applicationGroup,fieldConfigId,objectElement);
	}
}
