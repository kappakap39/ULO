package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class DE1_1NormalBIRTH_DATEDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(DE1_1NormalBIRTH_DATEDisplayMode.class);
	private static String CIDTYPE_PASSPORT = SystemConstant.getConstant("CIDTYPE_PASSPORT");
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		logger.debug("IAPersonalInfoSubformDisplayMode .....");
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)this.objectElement;
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		if(applicationGroup==null){
			applicationGroup= (ApplicationGroupDataM)FormControl.getObjectForm(request);		
		}
		boolean existSrcOfDataCis = applicationGroup.existSrcOfDataNotRemove(CompareDataM.SoruceOfData.CIS);
		String displayMode = HtmlUtil.EDIT;
		logger.debug("IS_VETO : "+applicationGroup.isVeto());
		logger.debug("existSrcOfDataCis : "+existSrcOfDataCis);
		logger.debug("personalInfo.getBirthDate()) >>"+personalInfo.getBirthDate());
		logger.debug("personalInfo.getCidType()) >>"+personalInfo.getCidType());
		if(CIDTYPE_PASSPORT.equals(personalInfo.getCidType()) && !Util.empty(personalInfo.getBirthDate())){
			if((applicationGroup.isVeto() || existSrcOfDataCis)){
				displayMode =  HtmlUtil.VIEW;
			}
		}else if(!CIDTYPE_PASSPORT.equals(personalInfo.getCidType())){
			if((applicationGroup.isVeto() || existSrcOfDataCis)){
				displayMode =  HtmlUtil.VIEW;
			}
		}
		return displayMode;
	}
}
