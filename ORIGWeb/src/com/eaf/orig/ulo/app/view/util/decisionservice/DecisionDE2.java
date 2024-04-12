package com.eaf.orig.ulo.app.view.util.decisionservice;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.display.ProcessActionInf;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.view.util.kpl.KPLUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.decison.DecisionApplication;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM;

public class DecisionDE2 extends ProcessActionHelper  {
	private static transient Logger logger = Logger.getLogger(DecisionDE2.class);
	String DECISION_SERVICE_POINT_DE2 =SystemConstant.getConstant("DECISION_SERVICE_POINT_DE2");
	String DECISION_SERVICE_POINT_POST_APPROVAL =SystemConstant.getConstant("DECISION_SERVICE_POINT_POST_APPROVAL");
	String[] JOBSTATE_REJECT = SystemConstant.getArrayConstant("JOBSTATE_REJECT");

	public Object processAction() {
		logger.debug("decision DE2.processAction()..");
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		String source = applicationGroup.getSource();
		logger.debug("DecisionDE2 source: "+source);
		ProcessActionInf processAction = DecisionFactory.getDecisionDE2(source);
		processAction.init(request);
		return processAction.processAction();
	}
}
