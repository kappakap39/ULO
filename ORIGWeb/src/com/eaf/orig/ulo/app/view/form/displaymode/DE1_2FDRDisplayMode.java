package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;

public class DE1_2FDRDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(DE1_2FDRDisplayMode.class);
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
//		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
//		String jobState = applicationGroup.getJobState();
//		logger.debug("jobState >> "+jobState);
		
		String ROLE_FU = SystemConstant.getConstant("ROLE_FU");
		String roleId = FormControl.getFormRoleId(request);
		String displayMode = HtmlUtil.VIEW;
		if(!ROLE_FU.equals(roleId)) {
			displayMode =  HtmlUtil.EDIT;
		}
		logger.debug("DE1_2FDRDisplayMode displayMode "+displayMode);
		return displayMode;
	}
}
