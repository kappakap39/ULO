package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class ThaibevOnlyDisplayMode extends FormDisplayModeHelper {
	private static transient Logger logger = Logger.getLogger(ThaibevOnlyDisplayMode.class);
	String APPLICATION_TEMPLATE_THAIBEV = SystemConstant.getConstant("APPLICATION_TEMPLATE_THAIBEV");
	
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		logger.debug("ThaibevOnlyDisplayMode.displayMode");
		String displayMode = HtmlUtil.VIEW;
		
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		if(Util.empty(applicationGroup)) 
		{
			applicationGroup = new ApplicationGroupDataM();
		}
		
		if(APPLICATION_TEMPLATE_THAIBEV.equals(applicationGroup.getApplicationTemplate()))
		{
			displayMode = HtmlUtil.EDIT;
		}
		
		return displayMode;
	}
}
