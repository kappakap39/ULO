package com.eaf.orig.ulo.app.view.form.displaymode;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class PercenlimitDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(PercenlimitDisplayMode.class);
	private String PERCENT_LIMIT_MAINCARD_MANUAL = SystemConstant.getConstant("PERCENT_LIMIT_MAINCARD_MANUAL");
	String DECISION_FINAL_DECISION_CANCEL = SystemConstant.getConstant("DECISION_FINAL_DECISION_CANCEL");
	String DECISION_FINAL_DECISION_REJECT = SystemConstant.getConstant("DECISION_FINAL_DECISION_REJECT");
	String ADD_CARD_NO_APPLICATION_ROLE = SystemConstant.getConstant("ADD_CARD_NO_APPLICATION_ROLE");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	
	public String displayMode(Object objectElement, HttpServletRequest request) {
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		if(Util.empty(applicationGroup)){
			applicationGroup = new ApplicationGroupDataM();
		}
		String displayMode = HtmlUtil.VIEW;
		logger.debug("JobState >> "+applicationGroup.getJobState());
		
		Object  objectData = this.objectElement;	
		ApplicationDataM  applicationItem = null;
		logger.debug("PercenlimitDisplayMode objectData >>"+objectData);
		if(objectData instanceof ApplicationDataM){
			applicationItem = (ApplicationDataM)objectData;
		}else if(objectData instanceof HashMap){
			HashMap<String,Object> hObjectModule = (HashMap<String,Object>)objectData;
			applicationItem = (ApplicationDataM)hObjectModule.get("APPLICATION");
		}
		logger.debug("PercenlimitDisplayMode applicationItem >>"+applicationItem);
		CardDataM card = null;
		String finalDecision = null;
		if(!Util.empty(applicationItem)){
			finalDecision = applicationItem.getFinalAppDecision();
			logger.debug("PercenlimitDisplayMode finalDecision >>"+finalDecision);
			card = applicationItem.getCard();
			if(Util.empty(card)){
				card = new CardDataM();
			}
			logger.debug("card.getPercentLimitMaincard() >>"+card.getPercentLimitMaincard());
			if((!(SystemConstant.lookup("JOBSTATE_APPROVE",applicationGroup.getJobState())) && PERCENT_LIMIT_MAINCARD_MANUAL.equals(card.getPercentLimitMaincard()))){ 
				if(ADD_CARD_NO_APPLICATION_ROLE.equals(roleId)){
					PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
					if(!Util.empty(personalInfo)){
						if(!Util.empty(personalInfo) && !Util.empty(personalInfo.getVerificationResult()) && !Util.empty(personalInfo.getVerificationResult().getPolicyRules())){
							displayMode =  HtmlUtil.EDIT;
						}
					}
				}
				else if(!DECISION_FINAL_DECISION_CANCEL.equals(finalDecision) && !DECISION_FINAL_DECISION_REJECT.equals(finalDecision)){
					displayMode =  HtmlUtil.EDIT;
				}
			}
		}
		return displayMode;
	}
}
