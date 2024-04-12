package com.eaf.orig.ulo.app.view.util.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class SetPropertyCardRequestSection implements AjaxInf {
private static transient Logger logger = Logger.getLogger(SetPropertyCardRequestSection.class);
	@Override
	public ResponseData processAction(HttpServletRequest request) {
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.SETPROPERTY_CARD_REQUEST);
		String[] subformIds = request.getParameterValues("subformId");
		for(String subformId : subformIds){
			logger.debug("subformId >>"+subformId);
			ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
			ORIGSubForm subform = FormControl.getSubformObject(subformId);
			if(null != subform){
				subform.setProperties(request, applicationGroup);
			}
		}
		return responseData.success();
	}

}
