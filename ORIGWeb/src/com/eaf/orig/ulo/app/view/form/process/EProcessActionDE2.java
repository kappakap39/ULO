package com.eaf.orig.ulo.app.view.form.process;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.im.rest.common.model.ServiceResponse;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

@SuppressWarnings("serial")
public class EProcessActionDE2 extends ProcessActionHelper {

	@Override
	public Object processAction() {
		String transactionId = (String)request.getSession().getAttribute("transactionId");
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		ProcessActionResponse processActionResponse = new ProcessActionResponse();
		processActionResponse.setResultCode(ServiceResponse.Status.SUCCESS);
		
		return processActionResponse;
	}
}
