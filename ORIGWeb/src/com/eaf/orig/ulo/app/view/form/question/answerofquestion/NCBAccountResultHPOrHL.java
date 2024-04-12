package com.eaf.orig.ulo.app.view.form.question.answerofquestion;

import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class NCBAccountResultHPOrHL extends FormHelper implements FormAction {

	@Override
	public String processForm() {
		// TODO Auto-generated method stub
		return super.processForm();
	}

	@Override
	public Object getObjectForm() {
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		return ORIGForm.getObjectForm();
	}

	


}