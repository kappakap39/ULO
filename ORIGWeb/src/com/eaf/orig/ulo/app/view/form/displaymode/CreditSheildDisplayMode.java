package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class CreditSheildDisplayMode extends FormDisplayModeHelper{
	Logger logger = Logger.getLogger(CreditSheildDisplayMode.class);

	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		logger.debug("CreditSheildDisplayMode.class DISPLAY MODE");
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		String templateId = applicationGroup.getApplicationTemplate();
		logger.debug("TEMPLATE : "+templateId);
		String DISPLAY_MODE = HtmlUtil.EDIT;
		String CREDITSHIELD_TEMPLATE = SystemConstant.getConstant("DISABLE_CREDITSHIELD_APPLICATION_TEMPLATE");
		if(CREDITSHIELD_TEMPLATE.equals(templateId)){
			DISPLAY_MODE = HtmlUtil.VIEW;
		}
		return DISPLAY_MODE;
	}
	
}
