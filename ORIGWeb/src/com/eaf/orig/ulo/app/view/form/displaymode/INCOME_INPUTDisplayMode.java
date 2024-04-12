package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.tools.ant.taskdefs.Javadoc.Html;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.SessionControl;
import com.eaf.orig.ulo.model.control.FlowControlDataM;

public class INCOME_INPUTDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(INCOME_INPUTDisplayMode.class);
	private static String ROLE_DE1_2 = SystemConstant.getConstant("ROLE_DE1_2");
	private static String ROLE_DV = SystemConstant.getConstant("ROLE_DV");
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
		String displayMode = HtmlUtil.VIEW;	
		String roleId = flowControl.getRole();
		if(ROLE_DE1_2.equals(roleId) || ROLE_DV.equals(roleId)){
			displayMode = HtmlUtil.EDIT;
		}
		logger.debug("INCOME_INPUTDisplayMode.displayMode >>"+displayMode);
		return displayMode;
	}
}
