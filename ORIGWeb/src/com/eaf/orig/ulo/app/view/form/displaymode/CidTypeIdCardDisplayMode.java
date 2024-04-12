package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.FormControl;
//import com.eaf.core.ulo.common.properties.SystemConstant;
//import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class CidTypeIdCardDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(CidTypeIdCardDisplayMode.class);
//	String COVERPAGE_TYPE_VETO = SystemConstant.getConstant("COVERPAGE_TYPE_VETO");
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		logger.debug("IAPersonalInfoSubformDisplayMode .....");
		ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);		
		String displayMode = HtmlUtil.EDIT;
		logger.debug("IS_VETO : "+applicationGroup.isVeto());
//		logger.debug("CoverpageType "+applicationGroup.getCoverpageType());
//		if(applicationGroup.isVeto() || COVERPAGE_TYPE_VETO.equals(applicationGroup.getCoverpageType())){
		if(applicationGroup.isVeto()){
			displayMode =  HtmlUtil.VIEW;
		}
		return displayMode;
	}
}