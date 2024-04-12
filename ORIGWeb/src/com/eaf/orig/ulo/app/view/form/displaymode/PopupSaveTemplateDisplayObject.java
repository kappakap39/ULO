package com.eaf.orig.ulo.app.view.form.displaymode;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayObjectHelper;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormEffects;
import com.eaf.orig.ulo.model.control.FlowControlDataM;

public class PopupSaveTemplateDisplayObject extends FormDisplayObjectHelper{
	private static final Logger logger = Logger.getLogger(PopupSaveTemplateDisplayObject.class);
	
	@Override
	public String getObjectFormMode(String objectId, String objectType,	Object objectElement) {
		String formMode = FormEffects.Effects.SHOW;
		FlowControlDataM flowControl = (FlowControlDataM)request.getSession().getAttribute("FlowControl");
		String actionType = flowControl.getActionType();
		logger.debug("flowControl >> "+actionType);
		if(SystemConstant.lookup("ACTION_TYPE_ENQUIRY",actionType)){
			formMode = FormEffects.Effects.HIDE;
		}
		return formMode;
	}

}
