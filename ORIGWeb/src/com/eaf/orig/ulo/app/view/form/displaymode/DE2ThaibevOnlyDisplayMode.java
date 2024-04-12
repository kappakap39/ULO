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

public class DE2ThaibevOnlyDisplayMode extends FormDisplayModeHelper {
	private static transient Logger logger = Logger.getLogger(DE2ThaibevOnlyDisplayMode.class);
	String APPLICATION_TEMPLATE_THAIBEV = SystemConstant.getConstant("APPLICATION_TEMPLATE_THAIBEV");
	
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		logger.debug("DE2ThaibevOnlyDisplayMode.displayMode");
		String displayMode = HtmlUtil.VIEW;
		
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		if(Util.empty(applicationGroup)) 
		{
			applicationGroup = new ApplicationGroupDataM();
		}
		
		if(APPLICATION_TEMPLATE_THAIBEV.equals(applicationGroup.getApplicationTemplate()))
		{
			logger.debug("JobState >> " + applicationGroup.getJobState());
			logger.debug("fieldConfigId >> " + fieldConfigId);
			if(SystemConstant.lookup("JOBSTATE_APPROVE", applicationGroup.getJobState()))
			{
				if(!isHasDataPreviousRole(applicationGroup)) 
				{
					displayMode = HtmlUtil.EDIT;
				} 
				else 
				{
					displayMode = HtmlUtil.VIEW;
				}
			} 
			else if(SystemConstant.lookup("JOBSTATE_REJECT", applicationGroup.getJobState()))
			{
				displayMode = HtmlUtil.EDIT;
			}
		}
		
		return displayMode;
	}

	public boolean isHasDataPreviousRole(ApplicationGroupDataM applicationGroup)
	{
		return CompareSensitiveFieldUtil.isPreviousCompareData(applicationGroup,fieldConfigId,objectElement);
	}
}
