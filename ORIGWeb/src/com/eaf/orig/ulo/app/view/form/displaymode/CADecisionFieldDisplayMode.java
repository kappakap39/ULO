package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.model.control.FlowControlDataM;

public class CADecisionFieldDisplayMode extends FormDisplayModeHelper {
	private static transient Logger logger = Logger.getLogger(CADecisionFieldDisplayMode.class);
	String REC_RESULT_REFER = SystemConstant.getConstant("REC_RESULT_REFER");
	String DECISION_FINAL_DECISION_CANCEL = SystemConstant.getConstant("DECISION_FINAL_DECISION_CANCEL");
	String ROLE_CAMGR = SystemConstant.getConstant("ROLE_CAMGR");
	@Override
	public String displayMode(Object objectValue, HttpServletRequest request) {
		String displayMode = HtmlUtil.VIEW;
		try {
			FlowControlDataM flowControl = (FlowControlDataM)request.getSession().getAttribute("FlowControl");
			String actionType = flowControl.getActionType();
			logger.debug("ActionType >> "+actionType);
			logger.debug("elementId>>"+elementId);
			logger.debug("fieldConfigId>>"+fieldConfigId);
			if(SystemConstant.lookup("ACTION_TYPE_ENQUIRY",actionType)){
				displayMode = HtmlUtil.DISABLED;
			}else{			
				if("CA_REMARK".equals(fieldConfigId)){
					displayMode = HtmlUtil.EDIT;
				}
			 }
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		
		logger.debug("displayMode : "+displayMode);
		return displayMode;
	}
}
