package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.app.view.util.decisionservice.DecisionEDV1;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class DE2HomeAddressFormDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(DE2HomeAddressFormDisplayMode.class);
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		if(Util.empty(applicationGroup)){
			applicationGroup = new ApplicationGroupDataM();
		}
		String source = applicationGroup.getSource();
		String displayMode = HtmlUtil.VIEW;
		logger.debug("DE2CurrentAddressFormDisplayMode source >> "+source);
		logger.debug("JobState >> "+applicationGroup.getJobState());
		logger.debug("fieldConfigId >> "+fieldConfigId);
		if(SystemConstant.lookup("JOBSTATE_APPROVE",applicationGroup.getJobState())){
			if(!isHasDataPreviousRole(applicationGroup)){
				displayMode =  HtmlUtil.EDIT;
			}else{
				displayMode =  HtmlUtil.VIEW;
			}
		}else if(SystemConstant.lookup("JOBSTATE_REJECT",applicationGroup.getJobState())){
			displayMode =  HtmlUtil.EDIT;
		}
		if(ApplicationUtil.eApp(source)){
			displayMode =  HtmlUtil.VIEW;
		}
		return displayMode;
	}
	
	public boolean isHasDataPreviousRole(ApplicationGroupDataM applicationGroup){	
		 
		
	
		return CompareSensitiveFieldUtil.isPreviousCompareData(applicationGroup,fieldConfigId,objectElement);
	}
}
