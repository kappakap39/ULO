package com.eaf.orig.ulo.app.view.form.displaymode;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayObjectHelper;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormEffects;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.control.FlowControlDataM;

public class ActionTypeDisplayObjectMode extends FormDisplayObjectHelper{
	private static final Logger logger = Logger.getLogger(ActionTypeDisplayObjectMode.class);
	@Override
	public String getObjectFormMode(String objectId, String objectType,Object objectElement) {
		FlowControlDataM flowControl = (FlowControlDataM)request.getSession().getAttribute("FlowControl");
		String actionType = flowControl.getActionType();
		if(SystemConstant.lookup("ACTION_TYPE_ENQUIRY",actionType)){
			return  FormEffects.Effects.HIDE;
		}else if(!Util.empty(conditions) &&conditions.contains(objectRoleId)){
			return  FormEffects.Effects.SHOW;
		}
		return FormEffects.Effects.HIDE;
	}
}
