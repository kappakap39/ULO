package com.eaf.orig.ulo.app.view.util.fico;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.fico.FicoApplication;

public class FicoSample extends ProcessActionHelper{
	@Override
	public Object processAction() {
		FicoApplication ficoApplication = new FicoApplication();
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		FicoRequest ficoRequest = new FicoRequest();
		ficoRequest.setApplicationGroup(applicationGroup);
		ficoRequest.setFicoFunction("");
		FicoResponse ficoResponse = FicoApplicationUtil.requestFico(ficoRequest);
		return ficoApplication;
	}
}
