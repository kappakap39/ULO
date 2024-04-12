package com.eaf.orig.ulo.app.view.form.displaymode;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayObjectHelper;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormEffects;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class DeleteCardInfoDisplayObject extends FormDisplayObjectHelper{
	private static transient Logger logger = Logger.getLogger(DeleteCardInfoDisplayObject.class);
	String DECISION_FINAL_DECISION_APPROVE = SystemConstant.getConstant("DECISION_FINAL_DECISION_APPROVE");
	String DECISION_FINAL_DECISION_REJECT = SystemConstant.getConstant("DECISION_FINAL_DECISION_REJECT");
	String DECISION_FINAL_DECISION_CANCEL = SystemConstant.getConstant("DECISION_FINAL_DECISION_CANCEL");
	String APPLICATION_TYPE_INCREASE = SystemConstant.getConstant("APPLICATION_TYPE_INCREASE");
	String FLIP_TYPE_INC = SystemConstant.getConstant("FLIP_TYPE_INC");
	String ROLE_DE1_2 = SystemConstant.getConstant("ROLE_DE1_2");
	String ROLE_DE1_1 = SystemConstant.getConstant("ROLE_DE1_1");
	String ROLE_DE2 = SystemConstant.getConstant("ROLE_DE2");
	String ROLE_CA = SystemConstant.getConstant("ROLE_CA");
	String ROLE_VT = SystemConstant.getConstant("ROLE_VT");
	String ROLE_CAMGR = SystemConstant.getConstant("ROLE_CAMGR");
	String ROLE_CAVP = SystemConstant.getConstant("ROLE_CAVP");
	String ADD_CARD_NO_APPLICATION_ROLE = SystemConstant.getConstant("ADD_CARD_NO_APPLICATION_ROLE");
//	String COVERPAGE_TYPE_VETO = SystemConstant.getConstant("COVERPAGE_TYPE_VETO");
	
	@Override
	public String getObjectFormMode(String objectId, String objectType, Object objectElement) {
		logger.debug("DeleteCardInfoDisplayObject.getObjectFormMode");
		String DISPLAY_MODE = FormEffects.Effects.ENABLE;
		ApplicationDataM application = (ApplicationDataM)objectElement;
		if(Util.empty(application)){
			application = new ApplicationDataM();
		}
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		if(applicationGroup.isVeto() 
				|| DECISION_FINAL_DECISION_APPROVE.equalsIgnoreCase(application.getFinalAppDecision())
				|| DECISION_FINAL_DECISION_REJECT.equalsIgnoreCase(application.getFinalAppDecision())
				|| DECISION_FINAL_DECISION_CANCEL.equalsIgnoreCase(application.getFinalAppDecision())){
			DISPLAY_MODE = FormEffects.Effects.HIDE;
		}
		if(APPLICATION_TYPE_INCREASE.equals(applicationGroup.getApplicationType()) 
			&& (ROLE_DE1_2.equals(formRoleId) 
				|| ROLE_DE2.equals(formRoleId) 
				|| ROLE_CA.equals(formRoleId) 
				|| ROLE_VT.equals(formRoleId)
				|| ROLE_CAMGR.equals(formRoleId) 
				|| ROLE_CAVP.equals(formRoleId)
				|| ROLE_DE1_1.equals(formRoleId))  && FLIP_TYPE_INC.equals(applicationGroup.getFlipType())
			&& !Util.empty(applicationGroup.getProducts())){
			DISPLAY_MODE = FormEffects.Effects.HIDE;
		}
	
		if(ADD_CARD_NO_APPLICATION_ROLE.equals(formRoleId)){
			if(!Util.empty(applicationGroup.getUsingPersonalInfo())){
				for(PersonalInfoDataM personalInfo : applicationGroup.getUsingPersonalInfo()){
					if(!Util.empty(personalInfo) && !Util.empty(personalInfo.getVerificationResult()) && !Util.empty(personalInfo.getVerificationResult().getPolicyRules())){
						DISPLAY_MODE = FormEffects.Effects.ENABLE;
						break;
					}else{
						DISPLAY_MODE = FormEffects.Effects.HIDE;
					}
				}
			}
		}
		
//		logger.debug("IS_VETO : "+applicationGroup.isVeto());
//		logger.debug("CoverpageType "+applicationGroup.getCoverpageType());
//		if(applicationGroup.isVeto() || COVERPAGE_TYPE_VETO.equals(applicationGroup.getCoverpageType())){
//			DISPLAY_MODE =FormEffects.Effects.DISABLED;
//		}
		return DISPLAY_MODE;
	}
}
