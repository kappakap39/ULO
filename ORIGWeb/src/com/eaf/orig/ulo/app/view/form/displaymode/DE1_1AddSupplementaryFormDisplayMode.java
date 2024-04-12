package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
//import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class DE1_1AddSupplementaryFormDisplayMode extends FormDisplayModeHelper {
	private static transient Logger logger = Logger.getLogger(DE1_1AddSupplementaryFormDisplayMode.class);
//	String COVERPAGE_TYPE_VETO = SystemConstant.getConstant("COVERPAGE_TYPE_VETO");
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		String displayMode=HtmlUtil.VIEW;
		logger.debug("IS_VETO : "+applicationGroup.isVeto());
//		logger.debug("COVERPAGE_TYPE : "+applicationGroup.getCoverpageType());
		if(applicationGroup.isSupplementaryApplicant() && !applicationGroup.isVeto()){
			displayMode=HtmlUtil.EDIT;
		}
		logger.debug("DE1_1AddSupplementaryFormDisplayMode add sup personalType displayMode >>"+displayMode);
		return displayMode;
	}
}
