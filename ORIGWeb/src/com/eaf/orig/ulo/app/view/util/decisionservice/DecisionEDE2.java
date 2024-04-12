package com.eaf.orig.ulo.app.view.util.decisionservice;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.orig.ulo.model.decison.DecisionApplication;

public class DecisionEDE2 extends ProcessActionHelper  {
	private static transient Logger logger = Logger.getLogger(DecisionEDE2.class);

	public Object processAction() {
		logger.debug("decision EDE2.processAction()..");
		DecisionApplication decisionApplication = new DecisionApplication();
		try{
			decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.SYSTEM_EXCEPTION);
			decisionApplication.setResultDesc(e.getLocalizedMessage());
		}
		return decisionApplication;
	}

}
