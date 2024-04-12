package com.eaf.orig.ulo.app.view.form.displaymode;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageSplitDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class DE2FormPositionDescDisplayMode extends DE2FormDisplayMode{
	private static transient Logger logger = Logger.getLogger(DE2FormPositionDescDisplayMode.class);
	private String GOVERNMENT_OFFICER = SystemConstant.getConstant("GOVERNMENT_OFFICER");
	private String PROFESSION_TYPE_JUDGE = SystemConstant.getConstant("PROFESSION_TYPE_JUDGE");
	private String FIELD_ID_IMAGE_GOVERNMENT = SystemConstant.getConstant("FIELD_ID_IMAGE_GOVERNMENT");
	private String IM_PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("IM_PERSONAL_TYPE_APPLICANT");
	@Override
	public String displayMode(Object objectElementData, HttpServletRequest request) {
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		if(null == applicationGroup){
			applicationGroup = new ApplicationGroupDataM();
		}
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectElement;
		if(Util.empty(personalInfo)){
			personalInfo = new PersonalInfoDataM();
		}
		String OCCUPATION_CODE = personalInfo.getOccupation();
		String PROFESSION_CODE = personalInfo.getProfession();
		String displayMode = HtmlUtil.EDIT;
		boolean imageGov = false;
		
		ArrayList<ApplicationImageSplitDataM> docListArr = applicationGroup.getImageSplits();
		for(ApplicationImageSplitDataM docList : docListArr){
			String IMType = docList.getApplicantTypeIM();
			logger.debug("docList.getDocType() >> "+docList.getDocType());
			if(IM_PERSONAL_TYPE_APPLICANT.equals(IMType) && FIELD_ID_IMAGE_GOVERNMENT.equals(docList.getDocType())){
				imageGov = true;
			}
		}
		logger.debug("APPLICATION_STATUS : "+applicationGroup.getJobState());
		if(SystemConstant.lookup("JOBSTATE_APPROVE",applicationGroup.getJobState())){
			if(!CompareSensitiveFieldUtil.isPreviousCompareData(applicationGroup, "OCCUPATION", personalInfo)){
				if(GOVERNMENT_OFFICER.equals(OCCUPATION_CODE) && imageGov){
					displayMode =  HtmlUtil.VIEW;
				}else if(GOVERNMENT_OFFICER.equals(OCCUPATION_CODE) && PROFESSION_TYPE_JUDGE.equals(PROFESSION_CODE) && CompareSensitiveFieldUtil.isPreviousCompareData(applicationGroup, "OCCUPATION", personalInfo)){
					displayMode = HtmlUtil.VIEW;
				}else{
					displayMode =  HtmlUtil.EDIT;
				}	
			}else{
				displayMode = HtmlUtil.VIEW;
			}
		}else if(SystemConstant.lookup("JOBSTATE_REJECT",applicationGroup.getJobState())){
			if(GOVERNMENT_OFFICER.equals(OCCUPATION_CODE) && imageGov){
				displayMode =  HtmlUtil.VIEW;
			}else if(GOVERNMENT_OFFICER.equals(OCCUPATION_CODE) && PROFESSION_TYPE_JUDGE.equals(PROFESSION_CODE)){
				displayMode = HtmlUtil.VIEW;
			}else{
				displayMode =  HtmlUtil.EDIT;
			}
		}
		logger.debug("displayMode : "+displayMode);
		return displayMode;
	}

}
