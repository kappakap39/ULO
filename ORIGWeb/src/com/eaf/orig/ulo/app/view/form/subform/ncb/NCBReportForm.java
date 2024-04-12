package com.eaf.orig.ulo.app.view.form.subform.ncb;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class NCBReportForm extends FormHelper implements FormAction{
	private static transient Logger logger = Logger.getLogger(NCBReportForm.class);
	@Override
	public String processForm() {
		return super.processForm();
	}
	@Override
	public Object getObjectForm() {
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)ORIGForm.getObjectForm();
		return applicationGroup;
	}
}
