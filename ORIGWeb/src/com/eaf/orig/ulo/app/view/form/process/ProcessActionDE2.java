package com.eaf.orig.ulo.app.view.form.process;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.display.ProcessActionInf;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

@SuppressWarnings("serial")
public class ProcessActionDE2 extends ProcessActionHelper {
	private static transient Logger logger = Logger.getLogger(ProcessActionDE2.class);
	
	@Override
	public Object processAction() {
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		String source = applicationGroup.getSource();
		logger.debug("DecisionDV2 source: "+source);
		ProcessActionInf processAction = ProcessActionFactory.getProcessActionDE2(source);
		processAction.init(request);
		return processAction.processAction();
	}
}
