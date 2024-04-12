package com.eaf.orig.ulo.app.view.form.displaymode;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.FormControl;
//import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class IAProductFormDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(IAProductFormDisplayMode.class);
//	String COVERPAGE_TYPE_VETO = SystemConstant.getConstant("COVERPAGE_TYPE_VETO");	
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		logger.debug("IAProductFormDisplayMode .....");		
		String displayMode = HtmlUtil.VIEW;
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)FormControl.getObjectForm(request);
		ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getPersonalInfos();		
		if(!Util.empty(personalInfos)){
			if(PersonalInfoUtil.isPersonalApplicant(personalInfos)){
				logger.debug("IS_VETO >> "+applicationGroup.isVeto());
//				logger.debug("CoverpageType >> "+applicationGroup.getCoverpageType());
				if(!applicationGroup.isVeto()){
					displayMode =  HtmlUtil.EDIT;
				}else{
					displayMode = HtmlUtil.VIEW;
				}
			}else{
				displayMode = HtmlUtil.VIEW;
			}
		}else{
			displayMode =  HtmlUtil.EDIT;
		}
		return displayMode;
	}

}
