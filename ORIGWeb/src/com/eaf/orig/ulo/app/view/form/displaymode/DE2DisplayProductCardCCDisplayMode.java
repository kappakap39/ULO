package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.BundleSMEDataM;

public class DE2DisplayProductCardCCDisplayMode extends FormDisplayModeHelper {
private static transient Logger logger = Logger.getLogger(DE2DisplayProductCardCCDisplayMode.class);	
@Override
public String displayMode(Object objectElement, HttpServletRequest request) {
	ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
	if(Util.empty(applicationGroup)){
		applicationGroup = new ApplicationGroupDataM();
	}
	ModuleFormHandler moduleHandler = (ModuleFormHandler) request.getSession().getAttribute("ModuleForm");
	ApplicationDataM application = (ApplicationDataM) moduleHandler.getObjectForm();
	if(Util.empty(application)){
		application = new ApplicationDataM();
	}
	String displayMode = HtmlUtil.VIEW;
	logger.debug("JobState >> "+applicationGroup.getJobState());
	logger.debug("fieldConfigId >> "+fieldConfigId);
	if(SystemConstant.lookup("JOBSTATE_APPROVE",applicationGroup.getJobState())){
		setObjectElement(application);
		if(!isHasDataPreviousRole(applicationGroup)){
			displayMode =  HtmlUtil.EDIT;
		}else{
			displayMode =  HtmlUtil.VIEW;
		}
	}else if(SystemConstant.lookup("JOBSTATE_REJECT",applicationGroup.getJobState())){
		displayMode =  HtmlUtil.EDIT;
	}
	return displayMode;
}

public boolean isHasDataPreviousRole(ApplicationGroupDataM applicationGroup){	
	
	return CompareSensitiveFieldUtil.isPreviousCompareData(applicationGroup,fieldConfigId,objectElement);
}
}
