package com.eaf.orig.ulo.app.view.form.displaymode;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageSplitDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class OccupationPositionDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(OccupationPositionLevelDisplayMode.class);
	private String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	private String GOVERNMENT_OFFICER = SystemConstant.getConstant("GOVERNMENT_OFFICER");
	private String PROFESSION_TYPE_JUDGE = SystemConstant.getConstant("PROFESSION_TYPE_JUDGE");
	private String FIELD_ID_IMAGE_GOVERNMENT = SystemConstant.getConstant("FIELD_ID_IMAGE_GOVERNMENT");
	private String IM_PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("IM_PERSONAL_TYPE_APPLICANT");
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		String DISPLAY_MODE = HtmlUtil.EDIT;
		boolean imageGov = false;
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		PersonalInfoDataM personalApplicant = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		String OCCUPATION_CODE = personalApplicant.getOccupation();
		String PROFESSION_CODE = personalApplicant.getProfession();
		ArrayList<ApplicationImageSplitDataM> docListArr = applicationGroup.getImageSplits();
		for(ApplicationImageSplitDataM docList : docListArr){
			String IMType = docList.getApplicantTypeIM();
			
			if(IM_PERSONAL_TYPE_APPLICANT.equals(IMType) && FIELD_ID_IMAGE_GOVERNMENT.equals(docList.getDocType())){
				imageGov = true;
			}
		}
		if(GOVERNMENT_OFFICER.equals(OCCUPATION_CODE) && imageGov){
			DISPLAY_MODE = HtmlUtil.VIEW;
		}else if(GOVERNMENT_OFFICER.equals(OCCUPATION_CODE) && PROFESSION_TYPE_JUDGE.equals(PROFESSION_CODE)){
			DISPLAY_MODE = HtmlUtil.VIEW;
		}
		logger.debug("imageGov >> "+imageGov + "DISPLAY_MODE >> "+DISPLAY_MODE);
		return DISPLAY_MODE;
	}

}
