package com.eaf.orig.ulo.app.view.form.displaymode;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayObjectHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormEffects;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.control.FlowControlDataM;

public class PopupAddressButtonDisplayObject extends FormDisplayObjectHelper{
	private static transient Logger logger = Logger.getLogger(PopupAddressButtonDisplayObject.class);
	@Override
	public String getObjectFormMode(String objectId, String objectType,	Object objectElement) {
		String formMode = FormEffects.Effects.SHOW;
		FlowControlDataM flowControl = (FlowControlDataM)request.getSession().getAttribute("FlowControl");
		String actionType = flowControl.getActionType();
		String roleId = flowControl.getRole();
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		if(Util.empty(applicationGroup)){
			applicationGroup = new ApplicationGroupDataM();
		}
		String source = applicationGroup.getSource();
		logger.debug("DE2CurrentAddressFormDisplayMode source >> "+source);
		logger.debug("flowControl >> "+actionType);
		if(SystemConstant.lookup("ACTION_TYPE_ENQUIRY",actionType)|| SystemConstant.lookup("ROLE_EXCEPTION_DISPLAY_MODE", roleId)){
			formMode = FormEffects.Effects.DISABLED;
		}
		if(ApplicationUtil.eApp(source)){
			formMode = FormEffects.Effects.DISABLED;
		}
		return formMode;
	}
}
