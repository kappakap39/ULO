package com.eaf.orig.ulo.app.view.util.decisionservice;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.display.ProcessActionInf;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class DecisionVerifyCustomer extends ProcessActionHelper {
	private static transient Logger logger = Logger.getLogger(DecisionVerifyCustomer.class);

	public Object processAction() {
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		String source = applicationGroup.getSource();
		logger.debug("DecisionVerifyCustomer source: "+source);
		ProcessActionInf processAction = DecisionFactory.getDecisionVerifyCustomer(source);
		processAction.init(request);
		return processAction.processAction();
	}

}
