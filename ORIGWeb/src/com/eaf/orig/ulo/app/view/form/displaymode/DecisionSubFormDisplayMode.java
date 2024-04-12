package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;

public class DecisionSubFormDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(DecisionSubFormDisplayMode.class);
	private static final String ROLE_CAMGR = SystemConstant.getConstant("ROLE_CAMGR");
	
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		String displayMode = HtmlUtil.EDIT;
		logger.debug("roleId >> "+roleId);
		if(ROLE_CAMGR.equals(roleId)){
			displayMode = HtmlUtil.DISABLED;
		}
		return displayMode;
	}
}
