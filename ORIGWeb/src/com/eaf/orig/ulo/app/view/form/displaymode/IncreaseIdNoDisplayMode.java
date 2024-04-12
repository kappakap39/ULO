package com.eaf.orig.ulo.app.view.form.displaymode;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class IncreaseIdNoDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(IncreaseIdNoDisplayMode.class);
//	String COVERPAGE_TYPE_VETO = SystemConstant.getConstant("COVERPAGE_TYPE_VETO");
	String FLIP_TYPE_INC = SystemConstant.getConstant("FLIP_TYPE_INC");
	
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		logger.debug("IdNoDisplayMode.displayMode");
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		if(null == applicationGroup){
			applicationGroup = new ApplicationGroupDataM();
		}
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
		String displayMode = HtmlUtil.VIEW;
		logger.debug("IS_VETO : "+applicationGroup.isVeto());
//		logger.debug("CoverpageType "+applicationGroup.getCoverpageType());
		logger.debug("applications.size() "+applications.size());
		logger.debug("applicationGroup.getFlipType()"+applicationGroup.getFlipType());
		if(!applicationGroup.isVeto()){
			displayMode = HtmlUtil.EDIT;
		}		
		if(applications.size() > 0) {
			displayMode =  HtmlUtil.VIEW;
		}else {
			displayMode =  HtmlUtil.EDIT;
		}			
		if(!Util.empty(applicationGroup.getFlipType()) && FLIP_TYPE_INC.equals(applicationGroup.getFlipType())) {
			displayMode =  HtmlUtil.EDIT;
		}
		return displayMode;
	}
}
