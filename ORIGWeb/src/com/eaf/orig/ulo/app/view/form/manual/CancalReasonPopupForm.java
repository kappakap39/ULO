package com.eaf.orig.ulo.app.view.form.manual;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ReasonDataM;
//import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
//import com.eaf.orig.ulo.model.app.ApplicationDataM;
//import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
//import com.orig.bpm.workflow.handle.WorkflowAction;

public class CancalReasonPopupForm extends FormHelper implements FormAction{
	private static transient Logger logger = Logger.getLogger(CancalReasonPopupForm.class);
	@Override
	public Object getObjectForm() {	
//		#rawi comment change logic cancel
//		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
//		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();	
//		return applicationGroup.getApplications();
		return new ReasonDataM();
	}
	@Override
	public String processForm() {
//		logger.debug("<<< CancalReasonPopupForm >>> ");
//		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
//		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();		
//		ArrayList<ApplicationDataM> applicationInfos = (ArrayList<ApplicationDataM>)objectForm;
//		applicationGroup.setFormAction(WorkflowAction.CANCEL_APPLICATION);
//		applicationGroup.setApplications(applicationInfos);
		
		ReasonDataM reason = (ReasonDataM)objectForm;
		logger.debug("reason : "+reason);
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");		
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		applicationGroup.setReason(reason);
		return "";
	}
}
