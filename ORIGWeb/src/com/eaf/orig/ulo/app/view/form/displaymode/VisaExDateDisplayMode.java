package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class VisaExDateDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(VisaExDateDisplayMode.class);
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String CIDTYPE_IDCARD = SystemConstant.getConstant("CIDTYPE_IDCARD");
	String VISA_TYPE_F = SystemConstant.getConstant("VISA_TYPE_F");
//	CIDTYPE_IDCARD=01
//	CIDTYPE_PASSPORT=02
//	PERSONAL_TYPE_APPLICANT=A
//	PERSONAL_TYPE_SUPPLEMENTARY=S
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		logger.debug("VisaExDateDisplayMode .....");
		ApplicationGroupDataM applicationGroupDataM = FormControl.getOrigObjectForm(request);
//		PersonalApplicationInfoDataM personalapplicationinfoDataM= (PersonalApplicationInfoDataM)FormControl.getObjectForm(request);
//		PersonalInfoDataM personalInfo = personalapplicationinfoDataM.getPersonalInfo();
		PersonalInfoDataM personalInfo = null;
		if(FormControl.getObjectForm(request) instanceof ApplicationGroupDataM){
			ApplicationGroupDataM applicationGroup= (ApplicationGroupDataM)FormControl.getObjectForm(request);
			personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		}else if(FormControl.getObjectForm(request) instanceof PersonalApplicationInfoDataM){
			PersonalApplicationInfoDataM personalapplicationinfoDataM= (PersonalApplicationInfoDataM)FormControl.getObjectForm(request);
			personalInfo = personalapplicationinfoDataM.getPersonalInfo();
			ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
		}
//		boolean existSrcOfDataCis = applicationGroup.existSrcOfData(CompareDataM.SoruceOfData.CIS);
		String displayMode = HtmlUtil.VIEW;
		logger.debug("IS_VETO : "+applicationGroupDataM.isVeto());
//		logger.debug("existSrcOfDataCis : "+existSrcOfDataCis);
		if(!CIDTYPE_IDCARD.equals(personalInfo.getCidType()) && !VISA_TYPE_F.equals(personalInfo.getVisaType())){
			displayMode =  HtmlUtil.EDIT;
		}else if(Util.empty(personalInfo.getCidType())){
			displayMode =  HtmlUtil.VIEW;
		}
		return displayMode;
	}
}