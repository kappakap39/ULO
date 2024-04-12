package com.eaf.orig.ulo.app.view.form.displaymode;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.CardDataM;

public class PercentlimitDE2 extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(PercentlimitDE2.class);
	private String PERCENT_LIMIT_MAINCARD_MANUAL = SystemConstant.getConstant("PERCENT_LIMIT_MAINCARD_MANUAL");
	String DECISION_FINAL_DECISION_CANCEL = SystemConstant.getConstant("DECISION_FINAL_DECISION_CANCEL");
	String DECISION_FINAL_DECISION_REJECT = SystemConstant.getConstant("DECISION_FINAL_DECISION_REJECT");
	
	public String displayMode(Object objectElement, HttpServletRequest request) {
		Object  objectData = this.objectElement;	
		ApplicationDataM  applicationItem = null;
		CardDataM card = null;
		String finalDecision = null;
		String DisplayMode = HtmlUtil.VIEW;
		if(objectData instanceof ApplicationDataM){
			applicationItem = (ApplicationDataM)objectData;
		}else if(objectData instanceof HashMap){
			HashMap<String,Object> hObjectModule = (HashMap<String,Object>)objectData;
			applicationItem = (ApplicationDataM)hObjectModule.get("APPLICATION");
		}

		if(!Util.empty(applicationItem)){
			finalDecision = applicationItem.getFinalAppDecision();
			logger.debug("PercentlimitDE2 finalDecision >>"+finalDecision);
			card = applicationItem.getCard();
			if(Util.empty(card)){
				card = new CardDataM();
			}
			logger.debug("card.getPercentLimitMaincard() >>"+card.getPercentLimitMaincard() );
			if(PERCENT_LIMIT_MAINCARD_MANUAL.equals(card.getPercentLimitMaincard()) && (!DECISION_FINAL_DECISION_CANCEL.equals(finalDecision) && !DECISION_FINAL_DECISION_REJECT.equals(finalDecision))){
				DisplayMode = HtmlUtil.EDIT;
			}
		}
		return DisplayMode;
	}
}
