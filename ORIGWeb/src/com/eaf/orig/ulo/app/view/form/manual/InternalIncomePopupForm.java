package com.eaf.orig.ulo.app.view.form.manual;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.view.util.decisionservice.DecisionApplicationUtil;
import com.eaf.orig.ulo.app.view.util.decisionservice.DecisionServiceResponse;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM.CallerScreen;

public class InternalIncomePopupForm extends FormHelper implements FormAction {
	
	private static transient Logger logger = Logger.getLogger(CBS1215I01PopupForm.class);
	
	@Override
	public Object getObjectForm() {
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)ORIGForm.getObjectForm();
		try {
			UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
			DecisionServiceRequestDataM reqApplication = new DecisionServiceRequestDataM();
			reqApplication.setApplicationGroup(applicationGroup);
			reqApplication.setUserId(userM.getUserName());
			reqApplication.setDecisionPoint(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_INCOME_INQUIRY);
			DecisionServiceResponse responseData = DecisionApplicationUtil.requestEDecisionServiceUncheck(reqApplication);
		} catch(Exception e) {
			logger.fatal("ERROR",e);
		}
		return applicationGroup;
	}
	
	@Override
	public String processForm() {
		return super.processForm();
	}

}
