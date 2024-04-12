package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.model.control.FlowControlDataM;

public class ActionTypeDisplayMode extends FormDisplayModeHelper{
	private static final Logger logger = Logger.getLogger(ActionTypeDisplayMode.class);
	
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		String displayMode = HtmlUtil.EDIT;
		FlowControlDataM flowControl = (FlowControlDataM)request.getSession().getAttribute("FlowControl");
		String actionType = flowControl.getActionType();
		logger.debug("ActionType >> "+actionType);
		if(SystemConstant.lookup("ACTION_TYPE_ENQUIRY",actionType)){
			displayMode = HtmlUtil.VIEW;
		}
		return displayMode;
	}
}
