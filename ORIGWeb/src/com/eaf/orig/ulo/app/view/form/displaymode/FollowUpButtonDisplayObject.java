package com.eaf.orig.ulo.app.view.form.displaymode;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayObjectHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.SessionControl;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.verification.VerifyUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.control.FlowControlDataM;

public class FollowUpButtonDisplayObject extends FormDisplayObjectHelper {
	private static transient Logger logger = Logger.getLogger(FollowUpButtonDisplayObject.class);
	@Override
	public String getObjectFormMode(String objectId, String objectType,Object objectElement) {
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
		String ACTION_TYPE = flowControl.getActionType();
		PersonalInfoDataM personalInfo=  PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		String displayMode=HtmlUtil.VIEW;
		if(!Util.empty(personalInfo) && !SystemConstant.lookup("ACTION_TYPE_ENQUIRY",ACTION_TYPE)){
			VerificationResultDataM verificationResult= personalInfo.getVerificationResult();
			if(VerifyUtil.isFollowUpVerCustomer(verificationResult, applicationGroup)){
				displayMode=HtmlUtil.EDIT;
			}
		}		
		logger.debug("FollowUpButtonDisplayObject displayMode >>"+displayMode);
		return displayMode;
	}
	
}
