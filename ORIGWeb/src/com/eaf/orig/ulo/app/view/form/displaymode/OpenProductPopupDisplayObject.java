package com.eaf.orig.ulo.app.view.form.displaymode;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayObjectHelper;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormEffects;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class OpenProductPopupDisplayObject extends FormDisplayObjectHelper{
	private static transient Logger logger = Logger.getLogger(OpenProductPopupDisplayObject.class);
	String DECISION_FINAL_DECISION_APPROVE = SystemConstant.getConstant("DECISION_FINAL_DECISION_APPROVE");
	String DECISION_FINAL_DECISION_REJECT = SystemConstant.getConstant("DECISION_FINAL_DECISION_REJECT");
	String DECISION_FINAL_DECISION_CANCEL = SystemConstant.getConstant("DECISION_FINAL_DECISION_CANCEL");
	
	@Override
	public String getObjectFormMode(String objectId, String objectType, Object objectElement) {
		logger.debug("OpenProductPopupDisplayObject.getObjectFormMode");
		String DISPLAY_MODE = FormEffects.Effects.ENABLE;
		ApplicationDataM application = (ApplicationDataM)objectElement;
		if(Util.empty(application)){
			application = new ApplicationDataM();
		}
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		// ENABLE MODE 
//		if(applicationGroup.isVeto() || DECISION_FINAL_DECISION_APPROVE.equals(application.getFinalAppDecision())
//				|| DECISION_FINAL_DECISION_REJECT.equals(application.getFinalAppDecision())
//				|| DECISION_FINAL_DECISION_CANCEL.equals(application.getFinalAppDecision())){
//			DISPLAY_MODE = FormEffects.Effects.HIDE;
//		}
		return DISPLAY_MODE;
	}
}
