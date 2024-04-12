package com.eaf.orig.ulo.app.view.util.manual;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.bpm.common.performance.TraceController;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.app.view.util.decisionservice.DecisionApplicationAction;
import com.eaf.orig.ulo.control.util.AuthorizedApplicationAction;

public class FormController implements AjaxInf{
	private static transient Logger logger = Logger.getLogger(FormController.class);
	@Override
	public ResponseData processAction(HttpServletRequest request) {
		String transactionId = (String)request.getSession().getAttribute("transactionId");
		logger.debug("transactionId >> "+transactionId);
		String buttonAction = request.getParameter("buttonAction");
		String functionId = request.getParameter("functionId");
		if(Util.empty(functionId)){
			functionId = SystemConstant.getConstant("DECISION_SERVICE_ACTION");
		}
		logger.debug("buttonAction : "+buttonAction);
		logger.debug("functionId : "+functionId);
		TraceController trace = new TraceController("FormController",transactionId);
		trace.create("Mandatory");
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.FORM_CONTROLLER);
		MandatoryForm mandatory = new MandatoryForm();
		ResponseData responseMandatoryForm = mandatory.processAction(request);
		trace.end("Mandatory");
		logger.debug("responseMandatoryForm : "+responseMandatoryForm.getNextActionFlag());
		if("Y".equals(responseMandatoryForm.getNextActionFlag())){
			if("SUBMIT".equals(buttonAction)){
				trace.create("SensitiveField");
				SensitiveFieldAction senstiveFieldAction = new SensitiveFieldAction();
				ResponseData responseSenstiveFrom = senstiveFieldAction.processAction(request);
				logger.error("responseSenstiveFrom : "+responseSenstiveFrom.getNextActionFlag());
				trace.end("SensitiveField");
				trace.create("Authorized");
				AuthorizedApplicationAction authorizedApplicationAction = new AuthorizedApplicationAction();
				authorizedApplicationAction.processAction(request);
				trace.end("Authorized");
				trace.create("DecisionApplication");
				DecisionApplicationAction decisionApplicationAction = new DecisionApplicationAction();
				ResponseData decisionApplicationResponse = decisionApplicationAction.processAction(request);
				trace.end("DecisionApplication");
				trace.trace();
				return decisionApplicationResponse;
			}
		}else{
			trace.trace();
			return responseMandatoryForm;
		}
		trace.trace();
		return responseData.success();
	}

}
