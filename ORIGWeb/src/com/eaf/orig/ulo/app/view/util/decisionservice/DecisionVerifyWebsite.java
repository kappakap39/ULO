package com.eaf.orig.ulo.app.view.util.decisionservice;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.display.ProcessActionInf;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.decison.DecisionApplication;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM.CallerScreen;

public class DecisionVerifyWebsite extends ProcessActionHelper {
	private static transient Logger logger = Logger.getLogger(DecisionVerifyWebsite.class);
	String VALIDATION_STATUS_COMPLETED = SystemConstant.getConstant("VALIDATION_STATUS_COMPLETED");
	String FINAL_APP_DECISION_REJECT = SystemConstant.getConstant("FINAL_APP_DECISION_REJECT");
	String DECISION_SERVICE_POINT_DV1 =SystemConstant.getConstant("DECISION_SERVICE_POINT_DV1");	
	
	public Object processAction() {
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		String source = applicationGroup.getSource();
		logger.debug("DecisionVerifyWebsite source: "+source);
		ProcessActionInf processAction = DecisionFactory.getDecisionVerifyWebsite(source);
		processAction.init(request);
		return processAction.processAction();
	}	

}
