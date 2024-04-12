package com.eaf.orig.ulo.app.view.form.displaymode;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayObjectHelper;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormEffects;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.control.FlowControlDataM;

public class PopupSaveTemplateCreditCardDisplayObject extends FormDisplayObjectHelper{
	private static final Logger logger = Logger.getLogger(PopupSaveTemplateCreditCardDisplayObject.class);
	String DECISION_FINAL_DECISION_CANCEL = SystemConstant.getConstant("DECISION_FINAL_DECISION_CANCEL");
	String DECISION_FINAL_DECISION_REJECT = SystemConstant.getConstant("DECISION_FINAL_DECISION_REJECT");
	String ADD_CARD_NO_APPLICATION_ROLE = SystemConstant.getConstant("ADD_CARD_NO_APPLICATION_ROLE");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	@Override
	public String getObjectFormMode(String objectId, String objectType,	Object objectElement) {
		String formMode = FormEffects.Effects.SHOW;
		FlowControlDataM flowControl = (FlowControlDataM)request.getSession().getAttribute("FlowControl");
		String actionType = flowControl.getActionType();
		String roleId = flowControl.getRole();
		logger.debug("flowControl >> "+actionType);
		logger.debug("roleId >> "+roleId);
		
		ModuleFormHandler moduleHandler = (ModuleFormHandler) request.getSession().getAttribute("ModuleForm");
		ApplicationDataM  applicationItem = null;
		String finalDecision = null;
		if(Util.empty(moduleHandler)){
			moduleHandler = new ModuleFormHandler();
		}
		Object  objectData = moduleHandler.getObjectForm();	
		if(!Util.empty(objectData)){
			if(objectData instanceof ApplicationDataM){
				applicationItem = (ApplicationDataM)objectData;
			}else if(objectData instanceof HashMap){
				HashMap<String,Object> hObjectModule = (HashMap<String,Object>)objectData;
				applicationItem = (ApplicationDataM)hObjectModule.get("APPLICATION");
			}
		}
		if(!Util.empty(applicationItem)){
			finalDecision = applicationItem.getFinalAppDecision();
			logger.debug("PopupSaveTemplateCreditCardDisplayObject finalDecision >>"+finalDecision);
		}
		if((SystemConstant.lookup("ACTION_TYPE_ENQUIRY",actionType) || SystemConstant.lookup("ROLE_EXCEPTION_DISPLAY_MODE", roleId)) || (DECISION_FINAL_DECISION_CANCEL.equals(finalDecision) || DECISION_FINAL_DECISION_REJECT.equals(finalDecision))){
			formMode = FormEffects.Effects.HIDE;
			ApplicationGroupDataM applicationGroup =  FormControl.getOrigObjectForm(request);
			if(ADD_CARD_NO_APPLICATION_ROLE.equals(roleId)){
				PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
				if(!Util.empty(personalInfo)){
					if(!Util.empty(personalInfo) && !Util.empty(personalInfo.getVerificationResult()) && !Util.empty(personalInfo.getVerificationResult().getPolicyRules())){
						formMode = FormEffects.Effects.SHOW;
					}
				}
			}
		}
		
		return formMode;
	}

}
