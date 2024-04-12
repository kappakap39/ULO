package com.eaf.orig.ulo.app.view.form.displaymode;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayObjectHelper;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormEffects;
import com.eaf.orig.ulo.model.control.FlowControlDataM;

public class PopupButtonDisplayObject extends FormDisplayObjectHelper{
	private static transient Logger logger = Logger.getLogger(PopupButtonDisplayObject.class);
	@Override
	public String getObjectFormMode(String objectId, String objectType,	Object objectElement) {
		String formMode = FormEffects.Effects.SHOW;
		FlowControlDataM flowControl = (FlowControlDataM)request.getSession().getAttribute("FlowControl");
		String actionType = flowControl.getActionType();
		String roleId = flowControl.getRole();
		logger.debug("flowControl >> "+actionType);
		if(SystemConstant.lookup("ACTION_TYPE_ENQUIRY",actionType)|| SystemConstant.lookup("ROLE_EXCEPTION_DISPLAY_MODE", roleId)){
			formMode = FormEffects.Effects.DISABLED;
		}
		return formMode;
	}
}
