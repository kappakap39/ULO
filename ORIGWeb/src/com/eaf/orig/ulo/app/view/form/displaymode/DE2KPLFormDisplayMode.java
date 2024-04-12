package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class DE2KPLFormDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(DE2KPLFormDisplayMode.class);
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		if(Util.empty(applicationGroup)){
			applicationGroup = new ApplicationGroupDataM();
		}
		String displayMode = HtmlUtil.VIEW;
		logger.debug("JobState >> "+applicationGroup.getJobState());
		logger.debug("fieldConfigId >> "+fieldConfigId);
		if(SystemConstant.lookup("JOBSTATE_APPROVE",applicationGroup.getJobState()))
		{
			displayMode =  HtmlUtil.VIEW;
		}else if(SystemConstant.lookup("JOBSTATE_REJECT",applicationGroup.getJobState())){
			displayMode =  HtmlUtil.EDIT;
		}
		return displayMode;
	}
}
