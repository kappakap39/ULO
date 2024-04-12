package com.eaf.orig.ulo.app.view.form.displaymode;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageSplitDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class DE2FormPOSITION_CODEDisplayMode extends DE2FormDisplayMode{
	private static transient Logger logger = Logger.getLogger(DE2FormPOSITION_CODEDisplayMode.class);
	private String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	private String GOVERNMENT_OFFICER = SystemConstant.getConstant("GOVERNMENT_OFFICER");
	private String PROFESSION_TYPE_JUDGE = SystemConstant.getConstant("PROFESSION_TYPE_JUDGE");
	private String FIELD_ID_IMAGE_GOVERNMENT = SystemConstant.getConstant("FIELD_ID_IMAGE_GOVERNMENT");
	private String IM_PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("IM_PERSONAL_TYPE_APPLICANT");
	
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		if(null == applicationGroup){
			applicationGroup = new ApplicationGroupDataM();
		}
		String displayMode = HtmlUtil.VIEW;
		boolean imageGov = false;
		PersonalInfoDataM personalApplicant = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		String OCCUPATION_CODE = personalApplicant.getOccupation();
		String PROFESSION_CODE = personalApplicant.getProfession();
		logger.debug("applicationGroupId >> "+applicationGroup.getApplicationGroupId());
		logger.debug("OCCUPATION_CODE >> "+OCCUPATION_CODE);
		
		ArrayList<ApplicationImageSplitDataM> docListArr = applicationGroup.getImageSplits();
		for(ApplicationImageSplitDataM docList : docListArr){
			String IMType = docList.getApplicantTypeIM();
			logger.debug("docList.getDocType() >> "+docList.getDocType());
			if(IM_PERSONAL_TYPE_APPLICANT.equals(IMType) && FIELD_ID_IMAGE_GOVERNMENT.equals(docList.getDocType())){
				imageGov = true;
			}
		}
		
		if(SystemConstant.lookup("JOBSTATE_APPROVE",applicationGroup.getJobState())){
			
			if(GOVERNMENT_OFFICER.equals(OCCUPATION_CODE) && imageGov && !isHasDataPreviousRole(applicationGroup)){
				displayMode = HtmlUtil.EDIT;
			}else if(GOVERNMENT_OFFICER.equals(OCCUPATION_CODE) && PROFESSION_TYPE_JUDGE.equals(PROFESSION_CODE) && !isHasDataPreviousRole(applicationGroup)){
				displayMode = HtmlUtil.EDIT;
			}else{
				displayMode =  HtmlUtil.VIEW;
			}
			//#FUT : 221.1
//			if(GOVERNMENT_OFFICER.equals(OCCUPATION_CODE)  && !PROFESSION_TYPE_JUDGE.equals(PROFESSION_CODE)){
//				displayMode =  HtmlUtil.EDIT;
//			}else{
//				displayMode =  HtmlUtil.VIEW;
//			}
		}else if(SystemConstant.lookup("JOBSTATE_REJECT",applicationGroup.getJobState())){
			if(GOVERNMENT_OFFICER.equals(OCCUPATION_CODE) && imageGov){
				displayMode = HtmlUtil.EDIT;
			}else if(GOVERNMENT_OFFICER.equals(OCCUPATION_CODE) && PROFESSION_TYPE_JUDGE.equals(PROFESSION_CODE)){
				displayMode = HtmlUtil.EDIT;
			}else{
				displayMode =  HtmlUtil.VIEW;
			}
//			if(GOVERNMENT_OFFICER.equals(OCCUPATION_CODE) && !PROFESSION_TYPE_JUDGE.equals(PROFESSION_CODE)){
//				displayMode =  HtmlUtil.EDIT;
//			}else{
//				displayMode =  HtmlUtil.VIEW;
//			}
		}
		
		logger.debug("objectElement : "+objectElement);
		logger.debug("displayMode : "+displayMode);
		return displayMode;
	}

}
