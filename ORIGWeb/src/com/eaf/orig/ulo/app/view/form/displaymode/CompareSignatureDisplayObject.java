package com.eaf.orig.ulo.app.view.form.displaymode;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayObjectHelper;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormEffects;
import com.eaf.core.ulo.common.util.SessionControl;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.control.FlowControlDataM;

public class CompareSignatureDisplayObject extends FormDisplayObjectHelper {
	private static transient Logger logger = Logger.getLogger(CompareSignatureDisplayObject.class);
	@Override
	public void init(String objectId, String objectType, FormEffects formEffect) {
		// TODO Auto-generated method stub
		super.init(objectId, objectType, formEffect);
	}

	@Override
	public String getObjectFormMode(String objectId, String objectType,Object objectElement) {
		String displayMode = FormEffects.Effects.DISABLED;
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
		String actionType = flowControl.getActionType();
		if(SystemConstant.lookup("ACTION_TYPE_ENQUIRY", actionType)){
			return FormEffects.Effects.HIDE;
		}
		if(MConstant.FLAG.YES.equals(applicationGroup.getExecuteFlag())){
			displayMode =FormEffects.Effects.ENABLE;
		}
		logger.debug("CompareSignatureDisplayObject displayMode >> "+displayMode);
		return displayMode;
	}
	

}
