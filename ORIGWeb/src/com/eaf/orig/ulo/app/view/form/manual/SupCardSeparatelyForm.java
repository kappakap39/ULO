package com.eaf.orig.ulo.app.view.form.manual;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class SupCardSeparatelyForm extends OrigApplicationForm{
	private static transient Logger logger = Logger.getLogger(SupCardSeparatelyForm.class);
	@Override
	public Object getObjectForm() throws Exception {
		String applicationGroupId = getRequestData("APPLICATION_GROUP_ID");
		String taskId = getRequestData("TASK_ID");
		logger.debug("applicationGroupId >> "+applicationGroupId);
		logger.debug("taskId >> "+taskId);
		String transactionId = (String)request.getSession().getAttribute("transactionId");
		ApplicationGroupDataM applicationGroup = getApplicationGroup(applicationGroupId,taskId,transactionId);
//		ApplicationDataM application = applicationGroup.getApplication(0);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_INFO);
		if(!Util.empty(personalInfo)){
				applicationGroup.setMainCardHolderName(personalInfo.getMainCardHolderName());
				applicationGroup.setMainCardNo(personalInfo.getMainCardNo());
		}
		return applicationGroup;
	}
}
