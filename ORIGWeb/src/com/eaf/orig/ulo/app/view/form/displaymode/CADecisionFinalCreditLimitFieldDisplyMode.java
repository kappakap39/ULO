package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.control.FlowControlDataM;

public class CADecisionFinalCreditLimitFieldDisplyMode extends FormDisplayModeHelper {
	private static transient Logger logger = Logger.getLogger(CADecisionFinalCreditLimitFieldDisplyMode.class);
	String REC_RESULT_REFER = SystemConstant.getConstant("REC_RESULT_REFER");
	String DECISION_FINAL_DECISION_CANCEL = SystemConstant.getConstant("DECISION_FINAL_DECISION_CANCEL");
	String ROLE_CAMGR = SystemConstant.getConstant("ROLE_CAMGR");
	@Override
	public String displayMode(Object objectValue, HttpServletRequest request) {
		String displayMode = HtmlUtil.VIEW;
		ApplicationDataM applicationInfo =  (ApplicationDataM)objectElement;
		 try {
			 	FlowControlDataM flowControl = (FlowControlDataM)request.getSession().getAttribute("FlowControl");
				String actionType = flowControl.getActionType();
				logger.debug("ActionType >> "+actionType);
				logger.debug("elementId>>"+elementId);
				logger.debug("fieldConfigId>>"+fieldConfigId);
				if(SystemConstant.lookup("ACTION_TYPE_ENQUIRY",actionType)){
					displayMode = HtmlUtil.DISABLED;
				}else{			
					if(!Util.empty(applicationInfo)){
						int lifeCycle = applicationInfo.getLifeCycle();
						logger.debug("lifeCycle>>"+lifeCycle);
						
						if(lifeCycle > 1){
							displayMode = HtmlUtil.EDIT;
						}else{
							String finalAppDicision = FormatUtil.display(applicationInfo.getFinalAppDecision());
							String recommendDecision=applicationInfo.getRecommendDecision();
							logger.debug("recommendDecision>>"+recommendDecision);
							logger.debug("finalAppDicision>>"+finalAppDicision);
							if(REC_RESULT_REFER.equals(recommendDecision)
							&& !DECISION_FINAL_DECISION_CANCEL.toUpperCase().equals(finalAppDicision.toUpperCase())){
								displayMode = HtmlUtil.EDIT;
							}else{
								displayMode = HtmlUtil.VIEW;
							}
						}
					}
				 }
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		
		logger.debug("displayMode : "+displayMode);
		return displayMode;
	}
}

