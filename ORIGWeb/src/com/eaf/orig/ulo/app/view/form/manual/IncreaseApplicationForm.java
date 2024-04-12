package com.eaf.orig.ulo.app.view.form.manual;

import org.apache.log4j.Logger;

import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class IncreaseApplicationForm extends OrigApplicationForm{
	private static transient Logger logger = Logger.getLogger(IncreaseApplicationForm.class);
	@Override
	public Object getObjectForm() throws Exception {
		String applicationGroupId = getRequestData("APPLICATION_GROUP_ID");
		String taskId = getRequestData("TASK_ID");
		logger.debug("applicationGroupId >> "+applicationGroupId);
		logger.debug("taskId >> "+taskId);
		String transactionId = (String)request.getSession().getAttribute("transactionId");
		ApplicationGroupDataM applicationGroup = getApplicationGroup(applicationGroupId,taskId,transactionId);
		int PERSONAL_SEQ = getPersonalSeq(PERSONAL_TYPE_APPLICANT, applicationGroup);
		initPersonalInfo(PERSONAL_TYPE_APPLICANT,PERSONAL_SEQ, applicationGroup);
		return applicationGroup;
	}

}
