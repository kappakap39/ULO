package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
//import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class IdNoDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(IdNoDisplayMode.class);
//	String COVERPAGE_TYPE_VETO = SystemConstant.getConstant("COVERPAGE_TYPE_VETO");	
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		logger.debug("IdNoDisplayMode.displayMode");
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		if(null == applicationGroup){
			applicationGroup = new ApplicationGroupDataM();
		}
		String displayMode = HtmlUtil.EDIT;
		logger.debug("IS_VETO : "+applicationGroup.isVeto());
//		logger.debug("CoverpageType "+applicationGroup.getCoverpageType());
//		if(applicationGroup.isVeto() || COVERPAGE_TYPE_VETO.equals(applicationGroup.getCoverpageType())){
		if(applicationGroup.isVeto()){
			displayMode = HtmlUtil.VIEW;
		}
		return displayMode;
	}
}
